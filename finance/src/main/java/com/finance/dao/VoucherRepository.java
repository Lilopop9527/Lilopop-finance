package com.finance.dao;

import com.finance.pojo.base.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author: Lilopop
 * @description:
 */
public interface VoucherRepository extends JpaRepository<Voucher,Long> {
}
