package com.auth.pojo.vo;

/**
 * @author: Lilopop
 * @description:
 */
public class DeptVO {
    private Long id;
    private String name;
    private Long principalId;
    private Integer deleated;

    public DeptVO() {
    }

    public DeptVO(Long id, String name, Long principalId, Integer deleated) {
        this.id = id;
        this.name = name;
        this.principalId = principalId;
        this.deleated = deleated;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPrincipalId() {
        return principalId;
    }

    public void setPrincipalId(Long principalId) {
        this.principalId = principalId;
    }

    public Integer getDeleated() {
        return deleated;
    }

    public void setDeleated(Integer deleated) {
        this.deleated = deleated;
    }

    @Override
    public String toString() {
        return "DeptVO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", principalId=" + principalId +
                ", deleated=" + deleated +
                '}';
    }
}
