package com.finance.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.common.core.exception.Asserts;
import com.common.core.pojo.CommonData;
import com.finance.dao.VoucherRepository;
import com.finance.feign.UserCheckService;
import com.finance.pojo.base.Voucher;
import com.finance.pojo.dto.VoucherDTO;
import com.finance.pojo.dto.VoucherMsgDTO;
import com.finance.pojo.vo.VoucherVO;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author: Lilopop
 * @description:
 */
@Service
public class VoucherService {
    @Autowired
    private UserCheckService userCheckService;
    @Autowired
    private VoucherRepository voucherRepository;
    @Autowired
    private StringRedisTemplate template;
    @Autowired
    private RedissonClient redissonClient;
    private static final String VOUCHERKEY = "voucher_for_check";
    private static final String VOUCHERLOCK = "voucher_lock";
    /**
     * 保存凭证信息
     * @param dto 凭证信息
     * @return 保存结果
     */
    public List<Integer> saveVouchers(VoucherDTO dto, List<VoucherMsgDTO> l){
        boolean flag = true;
        Integer status = 0;
        if (ObjectUtil.isNotEmpty(dto.getSingleId())){
            List<Voucher> list = voucherRepository.getVoucherBySingleId(dto.getSingleId());
            if (CollectionUtil.isNotEmpty(list))
                Asserts.fail("唯一编号已存在");
        }else{
            Asserts.fail("唯一编号不允许为空");
        }
        if (ObjectUtil.isNotEmpty(dto.getRecord())&&ObjectUtil.isNotEmpty(dto.getRecordName())){
            flag = userCheckService.checkUser(dto.getRecord(), dto.getRecordName()).getData();
            if (flag){
                status = 10;
            }
        }else {
            Asserts.fail("创建者不能为空");
        }
        //TODO 公司信息校验未做
        List<Integer> error = new ArrayList<>();
        if (flag){
            for (int i = 0;i<l.size();i++) {
                Voucher v = new Voucher();
                BeanUtil.copyProperties(dto,v);
                BeanUtil.copyProperties(l.get(i),v);
                v.setStatus(status);
                try {
                    voucherRepository.save(v);
                }catch (Exception e){
                    error.add(i);
                }
            }
        }
        return error;
    }

    /**
     * 提交当前凭证审核
     * @param singleId 凭证唯一id
     * @param userId 当前用户id
     * @param username 当前用户名
     * @return 提交结果
     */
    public String submitMsg(String singleId,Long userId,String username){
        boolean flag = userCheckService.checkUser(userId,username).getData();
        if (!flag)return "该用户不存在！";
        List<Voucher> list = voucherRepository.getVoucherBySingleIdAndRecordAndStatus(singleId,userId,10);
        if (CollectionUtil.isEmpty(list)){
            Asserts.fail("需要提交的数据不存在");
        }
        RLock lock = redissonClient.getFairLock(VOUCHERLOCK);
        try{
            boolean tryLock = lock.tryLock(5000,100, TimeUnit.MILLISECONDS);
            if (tryLock){
                template.opsForHash().put(VOUCHERKEY,String.valueOf(singleId),String.valueOf(userId));
                List<Voucher> vouchers = new ArrayList<>();
                for (Voucher v:list) {
                    v.setStatus(1);
                    vouchers.add(v);
                }
                voucherRepository.saveAll(vouchers);
            }
        }catch (InterruptedException e){
            Asserts.fail("数据获取失败");
        }finally {
            lock.unlock();
        }
        return "提交成功";
    }

    /**
     * 查找当前用户参与的凭证
     * @param userId 用户id
     * @return 凭证信息集合
     */
    public Map<String,List<VoucherVO>> getVouchersWithUserId(Long userId){
        List<Voucher> vouchers = voucherRepository.getVouchersByRecordOrPrincipalOrCashierOrReview(userId,userId,userId,userId);
        List<VoucherVO> vos = createVOs(vouchers);
        Map<String,List<VoucherVO>> map = new HashMap<>();
        for (VoucherVO v:vos) {
            List<VoucherVO> temp = map.getOrDefault(v.getSingleId(),new ArrayList<>());
            temp.add(v);
            map.put(v.getSingleId(),temp);
        }
        return map;
    }

    /**
     * 将凭证状态修改为已审核
     * @param id 审核人id
     * @param singleId 凭证唯一编号
     * @param name 审核人名称
     */
    public void checkedVoucher(Long id,String singleId,String name){
        Boolean data = userCheckService.checkUser(id, name).getData();
        if (!data)
            Asserts.fail("该用户非法");
        List<Voucher> vouchers = voucherRepository.getVoucherBySingleId(singleId);
        for (Voucher v:vouchers) {
            v.setPrincipal(id);
            v.setStatus(2);
        }
        voucherRepository.saveAll(vouchers);
        RLock lock = redissonClient.getFairLock(VOUCHERLOCK);
        try{
            boolean flag = lock.tryLock(5000,100,TimeUnit.MILLISECONDS);
            if (flag&&template.opsForHash().hasKey(VOUCHERKEY,singleId))
                template.opsForHash().delete(VOUCHERKEY,singleId);
        }catch (InterruptedException e){
            Asserts.fail("未找到相关信息");
        }finally {
            lock.unlock();
        }
    }

    /**
     * 获取所有待审核的凭证唯一编号
     * @return 结果集
     */
    public List<String> getAllCheck(){
        Set<Object> keys = template.opsForHash().keys(VOUCHERKEY);
        List<String> list = new ArrayList<>();
        for (Object o:keys) {
            list.add(String.valueOf(o));
        }
        list.sort((a,b)-> Long.parseLong(a)<Long.parseLong(b)?1:-1);
        return list;
    }

    public List<VoucherVO> createVOs(Collection<Voucher> vouchers){
        List<VoucherVO> vos = new ArrayList<>();
        for (Voucher v:vouchers) {
            VoucherVO vo = new VoucherVO();
            BeanUtil.copyProperties(v,vo);
            vos.add(vo);
        }
        return vos;
    }
}
