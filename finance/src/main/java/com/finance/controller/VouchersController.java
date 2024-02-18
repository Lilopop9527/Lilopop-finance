package com.finance.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.common.core.pojo.CommonData;
import com.common.interceptor.annotation.CheckRole;
import com.common.interceptor.config.ThreadInfo;
import com.finance.pojo.dto.VoucherDTO;
import com.finance.pojo.dto.VoucherMsgDTO;
import com.finance.pojo.vo.VoucherVO;
import com.finance.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    @CheckRole(2000)
    public CommonData<List<Integer>> saveVoucher(String dto,String vouchers){
        String[] arr = vouchers.split("-");
        List<VoucherMsgDTO> list = new ArrayList<>();
        for (String s:arr) {
            list.add(JSONUtil.toBean(s,VoucherMsgDTO.class));
        }
        VoucherDTO vd = JSONUtil.toBean(dto,VoucherDTO.class);
        List<Integer> error = voucherService.saveVouchers(vd,list);
        if (!error.isEmpty())
            return new CommonData<>(202,"部分信息失败",error);
        else
            return new CommonData<>(200,"success",null);
    }

    @PostMapping("/submit")
    @CheckRole(2000)
    public CommonData<String> submit(String singleId,Long userId,String username){
        String s = voucherService.submitMsg(singleId, userId, username);
        return new CommonData<>(200,"success",s);
    }

    @GetMapping("/g")
    @CheckRole(2000)
    public CommonData<Map<String, List<VoucherVO>>> getVoucherWithUserId(Long id){
        Map<String, Object> msg = ThreadInfo.get();
        Long t = (Long) msg.get("id");
        if (ObjectUtil.notEqual(id,t))
            return new CommonData<>(201,"只能操作当前用户信息",null);
        Map<String, List<VoucherVO>> map = voucherService.getVouchersWithUserId(id);
        return new CommonData<>(200,"success",map);
    }

    @PutMapping("/check")
    @CheckRole(2500)
    public CommonData checkVoucher(Long id,String singleId,String name){
        Map<String, Object> msg = ThreadInfo.get();
        Long t = (Long) msg.get("id");
        if (ObjectUtil.notEqual(id,t))
            return new CommonData<>(201,"只能操作当前用户信息",null);
        voucherService.checkedVoucher(id, singleId, name);
        return new CommonData<>(200,"success",null);
    }
}
