package com.finance.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.common.core.exception.Asserts;
import com.finance.dao.VoucherRepository;
import com.finance.feign.UserCheckService;
import com.finance.pojo.base.Voucher;
import com.finance.pojo.base.dto.VoucherDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Boolean saveVouchers(VoucherDTO dto){
        boolean flag = true;
        Voucher voucher = new Voucher();
        if (ObjectUtil.isNotEmpty(dto.getRecord())&&ObjectUtil.isNotEmpty(dto.getRecordName())){
            flag = flag & userCheckService.checkUser(dto.getRecord(), dto.getRecordName()).getData();
            if (flag){
                voucher.setRecord(dto.getRecord());
                voucher.setStatus(1);
            }
        }else {
            Asserts.fail("创建者不能为空");
        }
        if (ObjectUtil.isNotEmpty(dto.getPrincipal())&&ObjectUtil.isNotEmpty(dto.getPrincipalName())){
            flag = flag & userCheckService.checkUser(dto.getPrincipal(), dto.getPrincipalName()).getData();
            if (flag){
                voucher.setPrincipal(dto.getPrincipal());
                voucher.setStatus(2);
            }
        }
        if (ObjectUtil.isNotEmpty(dto.getReview())&&ObjectUtil.isNotEmpty(dto.getReviewName())){
            flag = flag & userCheckService.checkUser(dto.getReview(), dto.getRecordName()).getData();
            if (flag){
                voucher.setReview(dto.getReview());
                voucher.setStatus(3);
            }
        }
        if (ObjectUtil.isNotEmpty(dto.getCashier())&&ObjectUtil.isNotEmpty(dto.getCashierName())){
            flag = flag & userCheckService.checkUser(dto.getCashier(), dto.getCashierName()).getData();
            if (flag){
                voucher.setCashier(dto.getCashier());
                voucher.setStatus(4);
            }
        }
        if (ObjectUtil.isNotEmpty(dto.getMaker())&&ObjectUtil.isNotEmpty(dto.getMakerName())){
            flag = flag & userCheckService.checkUser(dto.getMaker(), dto.getMakerName()).getData();
            if (flag)voucher.setMaker(dto.getMaker());
        }
        //TODO 公司信息校验未做
        if (flag){
            BeanUtil.copyProperties(dto,voucher);
            voucherRepository.save(voucher);
        }
        return flag;
    }
}
