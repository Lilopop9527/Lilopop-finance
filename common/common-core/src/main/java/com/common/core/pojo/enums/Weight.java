package com.common.core.pojo.enums;

/**
 * @author: Lilopop
 * @description:
 */
public enum Weight {
    MILLION(1000000);
    private Integer id;
    private Integer weight;
    private Weight(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }


    @Override
    public String toString() {
        return "Weight{" +
                "id=" + id +
                '}';
    }
}
