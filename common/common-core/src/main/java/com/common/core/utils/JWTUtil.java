package com.common.core.utils;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.*;

public class JWTUtil {
    //过期时间30分钟
    private static final long EXPIRE_TIME = 30*60*1000;
    //私钥
    private static final String secert = "oiuxzflknas";

    /**
     * 创建一个过期时间为30分钟的token
     * @param map 需要在token中存储的信息
     * @return token字符串
     */
    public static String createToken(Map<String,Object> map){
        //设置过期时间
        //Date expDate = new Date(System.currentTimeMillis()+EXPIRE_TIME);
        //设置加密算法
        Algorithm algorithm = Algorithm.HMAC256(secert);
        //设置请求头
        Map<String,Object> header = new HashMap<>();
        header.put("typ","jwt");
        JWTCreator.Builder builder = JWT.create()
                .withHeader(header)
                //当前时间
                .withIssuedAt(new Date())
                //过期时间
                //.withExpiresAt(expDate)
        ;
        //添加负载信息
        map.forEach((k,v)->{
            if(v instanceof Integer){
                builder.withClaim(k,(Integer) v);
            }else if(v instanceof Long){
                builder.withClaim(k,(Long) v);
            }else if(v instanceof Boolean){
                builder.withClaim(k,(Boolean) v);
            }else if(v instanceof String){
                builder.withClaim(k,(String) v);
            }else if(v instanceof Double){
                builder.withClaim(k,(Double) v);
            }else if(v instanceof Date){
                builder.withClaim(k,(Date) v);
            }else if (v instanceof List<?>){
                builder.withClaim(k,(List<?>) v);
            }
        });
        return builder.sign(algorithm);
    }

    /**
     *校验传入的token是否合理
     * @param token 需要校验的token
     * @return 由于在校验阶段出错时校验器会直接抛出JWTVerificationException的子类，捕获到这个异常后再返回false
     */
    public static boolean verifyToken(String token){
        try {
            //设置运算方法
            Algorithm algorithm = Algorithm.HMAC256(secert);
            //创建检验器
            JWTVerifier verifier = JWT.require(algorithm).build();
            verifier.verify(token);
            return true;
        }catch (JWTVerificationException verificationException){
            //TODO 添加日志打印
            return false;
        }
    }

    /**
     * 获取token中的负载信息
     * @param token 需要解析的token
     * @return token中的信息,未找到返回null
     */
    public static Map<String, Claim> getClaims(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secert);
            JWTVerifier verifier = JWT.require(algorithm).build();
            return verifier.verify(token).getClaims();
        }catch (JWTVerificationException verificationException){
            //TODO 添加日志打印
            return null;
        }

    }

    /**
     * 根据特定字段在token中获取指定信息
     * @param token 目标token
     * @param target 需要解析的字段
     * @return 解析的字段，未找到返回空字符串
     */
    public static String getString(String token,String target){
        try {
            DecodedJWT decodedJWT = JWT.decode(token);
            return decodedJWT.getClaim(target).asString();
        }catch (JWTVerificationException decodeException){
            //TODO 日志打印
            return "";
        }
    }

    /**
     * 根据特定字段在token中获取指定数字信息
     * @param token 目标token
     * @param target 需要解析的字段
     * @return 解析的字段，未找到返回null
     */
    public static Integer getInt(String token,String target){
        try {
            DecodedJWT decodedJWT = JWT.decode(token);
            return decodedJWT.getClaim(target).asInt();
        }catch (JWTVerificationException decodeException){
            //TODO 日志打印
            return null;
        }
    }

    /**
     * 获取过期时间
     * @param token 目标token
     * @return 过期时间
     */
    public static Date getExpTime(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secert);
            return JWT.require(algorithm).build().verify(token).getExpiresAt();
        }catch (JWTVerificationException decodeException){
            //TODO 日志打印
            return null;
        }
    }
    /**
     * 获取创建时间
     * @param token 目标token
     * @return 创建时间
     */
    public static Date getCreateTime(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secert);
            return JWT.require(algorithm).build().verify(token).getIssuedAt();
        }catch (JWTVerificationException decodeException){
            //TODO 日志打印
            return null;
        }
    }

    /**
     * 判断当前token是否过期
     * @param token 需要判断的token
     * @return true 未过期  false 已过期
     */
    public static boolean isNotExpire(String token){
        try{
            return Objects.requireNonNull(getExpTime(token)).after(new Date());
        }catch (NullPointerException nullPointerException){
            //TODO 日志打印
            return false;
        }
    }

    /**
     * 利用Base64解析token的头信息
     * @param token
     * @return
     */
    public static String getHeaderByBase64(String token){
        if (token.isEmpty()){
            return null;
        }
        byte[] header_type = Base64.getDecoder().decode(token.split("\\.")[0]);
        return new String(header_type);
    }

    /**
     * 利用Base64解析token的负载信息
     * @param token
     * @return
     */
    public static String getPayloadByBase64(String token){
        if (token.isEmpty()){
            return null;
        }
        byte[] payload_type = Base64.getDecoder().decode(token.split("\\.")[1]);
        return new String(payload_type);
    }
}
