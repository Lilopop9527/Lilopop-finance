package com.finance.pojo.dto;

/**
 * @author: Lilopop
 * @description:
 */
public class VoucherDTO {
    private Long id;
    private String createDate;
    private Long record;
    private String recordName;
    private Long principal;
    private String principalName;
    private Long review;
    private String reviewName;
    private Long cashier;
    private String cashierName;
    private Long maker;
    private String makerName;
    private String singleId;
    private Long companyId;
    private Long company;
    private String originalEvidence;
    public VoucherDTO() {
    }

    public VoucherDTO(Long id, String createDate, Long record, String recordName, Long principal, String principalName,
                      Long review, String reviewName, Long cashier, String cashierName, Long maker, String makerName,
                      String singleId, Long companyId, Long company, String originalEvidence) {
        this.id = id;
        this.createDate = createDate;
        this.record = record;
        this.recordName = recordName;
        this.principal = principal;
        this.principalName = principalName;
        this.review = review;
        this.reviewName = reviewName;
        this.cashier = cashier;
        this.cashierName = cashierName;
        this.maker = maker;
        this.makerName = makerName;
        this.singleId = singleId;
        this.companyId = companyId;
        this.company = company;
        this.originalEvidence = originalEvidence;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getRecordName() {
        return recordName;
    }

    public void setRecordName(String recordName) {
        this.recordName = recordName;
    }

    public Long getPrincipal() {
        return principal;
    }

    public void setPrincipal(Long principal) {
        this.principal = principal;
    }

    public String getPrincipalName() {
        return principalName;
    }

    public void setPrincipalName(String principalName) {
        this.principalName = principalName;
    }

    public Long getReview() {
        return review;
    }

    public void setReview(Long review) {
        this.review = review;
    }

    public String getReviewName() {
        return reviewName;
    }

    public void setReviewName(String reviewName) {
        this.reviewName = reviewName;
    }

    public Long getCashier() {
        return cashier;
    }

    public void setCashier(Long cashier) {
        this.cashier = cashier;
    }

    public String getCashierName() {
        return cashierName;
    }

    public void setCashierName(String cashierName) {
        this.cashierName = cashierName;
    }

    public Long getMaker() {
        return maker;
    }

    public void setMaker(Long maker) {
        this.maker = maker;
    }

    public String getMakerName() {
        return makerName;
    }

    public void setMakerName(String makerName) {
        this.makerName = makerName;
    }

    public String getSingleId() {
        return singleId;
    }

    public void setSingleId(String singleId) {
        this.singleId = singleId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
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
    public String toString() {
        return "VoucherDTO{" +
                "id=" + id +
                ", createDate='" + createDate + '\'' +
                ", record=" + record +
                ", recordName='" + recordName + '\'' +
                ", principal=" + principal +
                ", principalName='" + principalName + '\'' +
                ", review=" + review +
                ", reviewName='" + reviewName + '\'' +
                ", cashier=" + cashier +
                ", cashierName='" + cashierName + '\'' +
                ", maker=" + maker +
                ", makerName='" + makerName + '\'' +
                ", singleId='" + singleId + '\'' +
                ", companyId=" + companyId +
                ", company=" + company +
                ", originalEvidence='" + originalEvidence + '\'' +
                '}';
    }
}
