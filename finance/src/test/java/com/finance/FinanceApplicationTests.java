package com.finance;

import cn.hutool.json.JSONUtil;
import com.finance.dao.EntryRepository;
import com.finance.pojo.base.Entry;
import com.finance.pojo.dto.VoucherDTO;
import com.finance.service.VoucherService;
import org.junit.jupiter.api.Test;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.List;
import java.util.Set;

@SpringBootTest
class FinanceApplicationTests {
    @Autowired
    private StringRedisTemplate template;
    @Autowired
    private EntryRepository entryRepository;
    @Autowired
    private VoucherService voucherService;
    @Autowired
    private RedissonClient redissonClient;
    @Test
    void contextLoads() {
        String s = "[{\"content\":\"出差3\",\"check\":2,\"first\":1,\"second\":2,\"money\":89893.2},{\"check\":1,\"first\":1,\"second\":3,\"money\":6598.21,\"content\":\"出差2\"},{\"check\":1,\"first\":0,\"second\":2,\"money\":986587.36,\"content\":\"出差3\"}]";
        List<VoucherDTO> dtos = JSONUtil.toList(s, VoucherDTO.class);
        System.out.println(dtos);
    }

    @Test
    void entryTest(){
        Entry entry = new Entry();
        entry.setName("出差");
        entryRepository.save(entry);
        Entry entry1 = new Entry();
        entry1.setName("出差");
        entry1.setParent(1L);
        entryRepository.save(entry1);
    }

    @Test
    void voucherTest(){
        //voucherService.checkedVoucher(1L,"5118536421879","周博源");
        Set<Object> set = template.opsForHash().keys("voucher_for_check");
        System.out.println(set);
    }

}
