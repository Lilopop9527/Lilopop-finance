package com.finance.pojo.vo;

import java.util.List;

/**
 * @author: Lilopop
 * @description:
 */
public class EntryVO {
    private Long id;
    private String name;
    private Long parent;
    private Integer deleted;
    private List<EntryVO> child;
    public EntryVO() {
    }

    public EntryVO(Long id, String name, Long parent,List<EntryVO> child) {
        this.id = id;
        this.name = name;
        this.parent = parent;
        this.child = child;
    }

    public EntryVO(Long id, String name, Long parent) {
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

    public List<EntryVO> getChild() {
        return child;
    }

    public void setChild(List<EntryVO> child) {
        this.child = child;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return "EntryVO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", parent=" + parent +
                '}';
    }
}
