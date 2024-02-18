package com.finance.pojo.vo;

/**
 * @author: Lilopop
 * @description:
 */
public class VoucherVO {
    private Long id;
    private String content;
    private Integer checked;
    private Long first;
    private Long second;
    private String money;
    private String createDate;
    private String record;
    private String principal;
    private String review;
    private String cashier;
    private String maker;
    private String singleId;
    private Long company;
    private String originalEvidence;
    private Integer status;

    public VoucherVO() {
    }

    public VoucherVO(Long id, String content, Integer checked, Long first, Long second, String money, String createDate,
                     String record, String principal, String review, String cashier, String maker,
                     String singleId, Long company, String originalEvidence,Integer status) {
        this.id = id;
        this.content = content;
        this.checked = checked;
        this.first = first;
        this.second = second;
        this.money = money;
        this.createDate = createDate;
        this.record = record;
        this.principal = principal;
        this.review = review;
        this.cashier = cashier;
        this.maker = maker;
        this.singleId = singleId;
        this.company = company;
        this.originalEvidence = originalEvidence;
        this.status = status;
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

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getCashier() {
        return cashier;
    }

    public void setCashier(String cashier) {
        this.cashier = cashier;
    }

    public String getMaker() {
        return maker;
    }

    public void setMaker(String maker) {
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
