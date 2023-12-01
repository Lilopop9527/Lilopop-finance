package com.auth.pojo.vo;

/**
 * @author: Lilopop
 * @description:
 */
public class StationVO {
    private Long id;
    private String name;
    private Integer deleated;

    public StationVO() {
    }

    public StationVO(Long id, String name,Integer deleated) {
        this.id = id;
        this.name = name;
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

    public Integer getDeleated() {
        return deleated;
    }

    public void setDeleated(Integer deleated) {
        this.deleated = deleated;
    }

    @Override
    public String toString() {
        return "StationVO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
