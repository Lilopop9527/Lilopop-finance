package com.finance.controller;

import com.common.core.pojo.CommonData;
import com.finance.pojo.base.dto.VoucherDTO;
import com.finance.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: Lilopop
 * @description:
 */
@RestController
@RequestMapping("/voucher")
public class VouchersController {
    @Autowired
    private VoucherService voucherService;

    @PostMapping("/save")
    public CommonData<Boolean> saveVoucher(VoucherDTO dto){
        Boolean flag = voucherService.saveVouchers(dto);
        return new CommonData<>(200,"success",flag);
    }
}
