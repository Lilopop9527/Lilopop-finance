package com.auth.pojo.vo;

/**
 * @author: Lilopop
 * @description:
 */
public class StationVO {
    private Long id;
    private String name;

    public StationVO() {
    }

    public StationVO(Long id, String name) {
        this.id = id;
        this.name = name;
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

    @Override
    public String toString() {
        return "StationVO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
