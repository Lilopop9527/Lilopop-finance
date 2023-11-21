package com.auth.pojo.dto;

/**
 * @author: Lilopop
 * @description:
 */
public class RoleDTO {
    private Long id;
    private String roleName;
    private Integer weight;

    public RoleDTO() {
    }

    public RoleDTO(Long id, String roleName, Integer weight) {
        this.id = id;
        this.roleName = roleName;
        this.weight = weight;
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
}
