package com.auth.pojo.vo;

import java.util.List;

public class RouteVO {
    private Long id;
    private String path;
    private String title;
    private List<RouteVO> children;

    public RouteVO() {
    }

    public RouteVO(Long id, String path, String title) {
        this.id = id;
        this.path = path;
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<RouteVO> getChildren() {
        return children;
    }

    public void setChildren(List<RouteVO> children) {
        this.children = children;
    }
}
