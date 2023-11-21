package com.auth.pojo.vo;

public class RoleVO {
    private Long id;
    private String roleName;
    private Integer weight;
    private Integer deleated;

    public RoleVO() {
    }

    public RoleVO(Long id, String roleName,Integer weight,Integer deleated) {
        this.id = id;
        this.roleName = roleName;
        this.weight = weight;
        this.deleated = deleated;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getDeleated() {
        return deleated;
    }

    public void setDeleated(Integer deleated) {
        this.deleated = deleated;
    }

    @Override
    public String toString() {
        return "RoleVO{" +
                "id=" + id +
                ", roleName='" + roleName + '\'' +
                ", weight=" + weight +
                ", deleated=" + deleated +
                '}';
    }
}
