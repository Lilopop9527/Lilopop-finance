package com.finance.pojo.base;

import jakarta.persistence.*;

import java.util.Objects;

/**
 * @author: Lilopop
 * @description:
 */
@Entity(name = "voucher")
@Table(
        name = "voucher",
        indexes = {
                @Index(name = "voucher_unique_single_id",columnList = "single_id",unique = true),
                @Index(name = "voucher_normal_company",columnList = "company")
        }
)
public class Voucher {
    @Id
    @SequenceGenerator(
            name = "voucher_sequence",
            sequenceName = "voucher_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.IDENTITY,
            generator = "voucher_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;
    @Column(
            name = "content",
            length = 8,
            columnDefinition = "",
            nullable = false
    )
    private String content;
    @Column(
            name = "checked",
            columnDefinition = "tinyint default 0"
    )
    private Integer checked;
    @Column(
            name = "first",
            columnDefinition = "bigint default 0"
    )
    private Long first;
    @Column(
            name = "second",
            columnDefinition = "bigint default 1"
    )
    private Long second;
    @Column(
            name = "money",
            length = 12
    )
    private String money;
    @Column(
            name = "status",
            columnDefinition = "tinyint default 0"
    )
    private Integer status;
    @Column(
            name = "create_date",
            columnDefinition = ""
    )
    private String createDate;
    @Column(
            name = "record",
            columnDefinition = "bigint default 0"
    )
    private Long record;
    @Column(
            name = "principal",
            columnDefinition = "bigint default 0"
    )
    private Long principal;
    @Column(
            name = "review",
            columnDefinition = "bigint default 0"
    )
    private Long review;
    @Column(
            name = "cashier",
            columnDefinition = "bigint default 0"
    )
    private Long cashier;
    @Column(
            name = "maker",
            columnDefinition = "bigint default 0"
    )
    private Long maker;
    @Column(
            name = "single_id",
            columnDefinition = "",
            length = 64
    )
    private String singleId;
    @Column(
            name = "company",
            columnDefinition = "bigint default 0"
    )
    private Long company;
    @Column(
            name = "original_evidence",
            columnDefinition = ""
    )
    private String originalEvidence;

    public Voucher() {
    }

    public Voucher(Long id, String content, Integer checked, Long first, Long second, String money,
                   Integer status, String createDate, Long record, Long principal, Long review, Long cashier,
                   Long maker, String singleId, Long company, String originalEvidence) {
        this.id = id;
        this.content = content;
        this.checked = checked;
        this.first = first;
        this.second = second;
        this.money = money;
        this.status = status;
        this.createDate = createDate;
        this.record = record;
        this.principal = principal;
        this.review = review;
        this.cashier = cashier;
        this.maker = maker;
        this.singleId = singleId;
        this.company = company;
        this.originalEvidence = originalEvidence;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getChecked() {
        return checked;
    }

    public void setChecked(Integer checked) {
        this.checked = checked;
    }

    public Long getFirst() {
        return first;
    }

    public void setFirst(Long first) {
        this.first = first;
    }

    public Long getSecond() {
        return second;
    }

    public void setSecond(Long second) {
        this.second = second;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public Long getRecord() {
        return record;
    }

    public void setRecord(Long record) {
        this.record = record;
    }

    public Long getPrincipal() {
        return principal;
    }

    public void setPrincipal(Long principal) {
        this.principal = principal;
    }

    public Long getReview() {
        return review;
    }

    public void setReview(Long review) {
        this.review = review;
    }

    public Long getCashier() {
        return cashier;
    }

    public void setCashier(Long cashier) {
        this.cashier = cashier;
    }

    public Long getMaker() {
        return maker;
    }

    public void setMaker(Long maker) {
        this.maker = maker;
    }

    public String getSingleId() {
        return singleId;
    }

    public void setSingleId(String singleId) {
        this.singleId = singleId;
    }

    public Long getCompany() {
        return company;
    }

    public void setCompany(Long company) {
        this.company = company;
    }

    public String getOriginalEvidence() {
        return originalEvidence;
    }

    public void setOriginalEvidence(String originalEvidence) {
        this.originalEvidence = originalEvidence;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Voucher voucher = (Voucher) o;
        return Objects.equals(id, voucher.id) && Objects.equals(content, voucher.content) &&
                Objects.equals(checked, voucher.checked) && Objects.equals(first, voucher.first) &&
                Objects.equals(second, voucher.second) && Objects.equals(money, voucher.money) &&
                Objects.equals(status, voucher.status) && Objects.equals(createDate, voucher.createDate) &&
                Objects.equals(record, voucher.record) && Objects.equals(principal, voucher.principal) &&
                Objects.equals(review, voucher.review) && Objects.equals(cashier, voucher.cashier) &&
                Objects.equals(maker, voucher.maker) && Objects.equals(singleId, voucher.singleId) &&
                Objects.equals(company, voucher.company) && Objects.equals(originalEvidence, voucher.originalEvidence);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, content, checked, first, second, money, status, createDate, record, principal, review,
                cashier, maker, singleId, company, originalEvidence);
    }
}
