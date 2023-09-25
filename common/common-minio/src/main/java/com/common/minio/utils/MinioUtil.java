package com.common.minio.utils;

import com.common.minio.proper.MinioProper;
import com.common.minio.proper.ObjectItem;
import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import io.minio.messages.DeleteError;
import io.minio.messages.DeleteObject;
import io.minio.messages.Item;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author: Lilopop
 * @description:
 */
@Component
public class MinioUtil {

    private final MinioClient client;
    private final String bucketName;

    public MinioUtil(MinioProper proper) {
        this.client = MinioClient.builder().endpoint(proper.getUrl())
                .credentials(proper.getUsername(), proper.getPassword()).build();
        this.bucketName = proper.getBucketName();
    }

    /**
     * 判断是否存在名字为bucketName的桶，不存在则创建
     * @param bucketName
     */
    public void existBucket(String bucketName) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        boolean exists = client.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        if (!exists){
            makeBucket(bucketName);
        }
    }

    /**
     * 创建名称为bucketName的桶
     * @param bucketName
     */
    public void makeBucket(String bucketName) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        client.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
    }

    /**
     * 删除名称为bucketName的桶
     * @param bucketName
     * @return
     */
    public boolean removeBucket(String bucketName) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        client.removeBucket(RemoveBucketArgs.builder().bucket(bucketName).build());
        return true;
    }

    /**
     * 上传文件
     * @param multipartFile
     * @return
     */
    public List<String> upload(MultipartFile[] multipartFile) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        List<String> names = new ArrayList<>(multipartFile.length);
        for (MultipartFile file:multipartFile) {
            String fileName = file.getOriginalFilename();
            assert fileName != null;
            String[] split = fileName.split("\\.");
            if (split.length > 1){
                fileName = split[0] + "_" + System.currentTimeMillis() + "." + split[1];
            }else {
                fileName = fileName + System.currentTimeMillis();
            }
            InputStream in = null;
            in = file.getInputStream();
            client.putObject(PutObjectArgs.builder().bucket(bucketName).object(fileName)
                    .stream(in,in.available(),-1)
                    .contentType(file.getContentType()).build());
            in.close();
            names.add(fileName);
        }
        return names;
    }

    /**
     * 下载文件
     * @return
     */
    public ResponseEntity<byte[]> download(String fileName) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        ResponseEntity<byte[]> responseEntity = null;
        InputStream in = null;
        ByteArrayOutputStream out = null;
        in = client.getObject(GetObjectArgs.builder().bucket(bucketName).object(fileName).build());
        out = new ByteArrayOutputStream();
        IOUtils.copy(in,out);
        //封装返回值
        byte[] bytes = out.toByteArray();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition","attachment;fileName=" +
                URLEncoder.encode(fileName, StandardCharsets.UTF_8));
        headers.setContentLength(bytes.length);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setAccessControlExposeHeaders(List.of("*"));
        responseEntity = new ResponseEntity<byte[]>(bytes,headers, HttpStatus.OK);
        in.close();
        out.close();
        return responseEntity;
    }

    /**
     * 查看文件对象
     * @param bucketName 存储bucket名称
     * @return 存储bucket内文件对象信息
     */
    public List<ObjectItem> listObjects(String bucketName) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        Iterable<Result<Item>> results = client.listObjects(ListObjectsArgs.
                builder().bucket(bucketName).build());
        List<ObjectItem> objectItems = new ArrayList<>();
        for (Result<Item> result:results) {
            Item item = result.get();
            ObjectItem objectItem = new ObjectItem();
            objectItem.setObjectName(item.objectName());
            objectItem.setSize(item.size());
            objectItems.add(objectItem);
        }
        return objectItems;
    }

    /**
     * 批量删除文件
     * @param bucketName 存储bucket名称
     * @param objects 对象名称集合
     * @return
     */
    public Iterable<Result<DeleteError>> removeObjects(String bucketName, List<String> objects){
        List<DeleteObject> dos = objects.stream().map(DeleteObject::new)
                .collect(Collectors.toList());
        return client.removeObjects(RemoveObjectsArgs.builder()
                .bucket(bucketName).objects(dos).build());
    }

    /**
     * 生成图片的缩略图
     * @param file
     * @return
     * @throws Exception
     */
    public String getThumb(MultipartFile file) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        String thumbName;
        List<String> list;
        String suffix = Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf(".") + 1);
        String fileName = "thumb_" + file.getOriginalFilename();
        if (isPicture(suffix) && (1024*1024*0.1) <= file.getSize()){
            File newFile = new File(//ClassUtils.getDefaultClassLoader().getResource("upload").getPath()
                        "E:\\upload\\" + fileName);
            if ((1024*1024*0.1) <= file.getSize() && file.getSize() <= (1024*1024)){//小于1M的
                Thumbnails.of(file.getInputStream()).scale(1f,1f).toFile(newFile);
            }else if ((1024*1024) < file.getSize() && file.getSize() <= (1024*1024*2)){//1-2M的
                Thumbnails.of(file.getInputStream()).scale(0.2,0.2).toFile(newFile);
            }else if ((1024*1024*2) < file.getSize()){//大于2M的
                Thumbnails.of(file.getInputStream()).scale(0.1,0.1).toFile(newFile);
            }
            FileInputStream input = new FileInputStream(newFile);
            MultipartFile multipartFile = new MockMultipartFile("file",newFile.getName(),"image/png",input);
            list = upload(new MultipartFile[]{multipartFile});
            thumbName = list.get(0);
            Files.delete(newFile.toPath());
            }else {//图片过小，直接上传
                thumbName = file.getOriginalFilename();
            }
        return thumbName;
    }

    /**
     * 判断文件是否为图片
     */
    public boolean isPicture(String imgName) {
        boolean flag = false;
        if (imgName.isEmpty()) {
            return false;
        }
        String[] arr = {"bmp", "dib", "gif", "jfif", "jpe", "jpeg", "jpg", "png", "tif", "tiff", "ico"};
        for (String item : arr) {
            if (item.equals(imgName)) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    public String getObjectUrl(String name,String bucket) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        Map<String,String> map = new HashMap<>();
        map.put("response-content-type","application/json");
        GetPresignedObjectUrlArgs args = GetPresignedObjectUrlArgs.builder()
                .method(Method.GET)
                .bucket(bucket)
                .object(name)
                .expiry(6000)
                .extraQueryParams(map)
                .build();
        return client.getPresignedObjectUrl(args);
    }
}
