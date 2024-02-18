package com.finance.pojo.dto;

/**
 * @author: Lilopop
 * @description:
 */
public class EntryDTO {
    private Long id;
    private String name;
    private Long parent;

    public EntryDTO() {
    }

    public EntryDTO(Long id, String name, Long parent) {
        this.id = id;
        this.name = name;
        this.parent = parent;
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

    public Long getParent() {
        return parent;
    }

    public void setParent(Long parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {
        return "EntryDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", parent=" + parent +
                '}';
    }
}
