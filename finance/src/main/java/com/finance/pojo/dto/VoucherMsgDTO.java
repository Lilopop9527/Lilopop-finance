package com.finance.pojo.dto;

/**
 * @author: Lilopop
 * @description:
 */
public class VoucherMsgDTO {
    private String content;
    private Integer checked;
    private Long first;
    private Long second;
    private String money;

    public VoucherMsgDTO() {
    }

    public VoucherMsgDTO(String content, Integer checked, Long first, Long second, String money) {
        this.content = content;
        this.checked = checked;
        this.first = first;
        this.second = second;
        this.money = money;
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

    @Override
    public String toString() {
        return "VoucherMsgDTO{" +
                "content='" + content + '\'' +
                ", checked=" + checked +
                ", first=" + first +
                ", second=" + second +
                ", money='" + money + '\'' +
                '}';
    }
}
