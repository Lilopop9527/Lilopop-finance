package com.finance.dao;

import com.finance.pojo.base.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author: Lilopop
 * @description:
 */
public interface VoucherRepository extends JpaRepository<Voucher,Long> {

    List<Voucher> getVoucherBySingleId(String singleId);
    List<Voucher> getVoucherBySingleIdAndRecordAndStatus(String singleId,Long record,Integer status);
    //@Query("select * from Voucher v where v.record = :id or v.principal = :id or v.review = :id or v.cashier = :id")
    //List<Voucher> getVouchersWithUserId(@Param("id") Long id);

    List<Voucher> getVouchersByRecordOrPrincipalOrCashierOrReview(Long record,Long principal,Long cashier,Long review);
}
