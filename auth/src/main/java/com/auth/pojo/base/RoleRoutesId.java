package com.auth.pojo.base;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class RoleRoutesId implements Serializable {
    @Column(
            name = "role_id"
    )
    private Long roleId;
    @Column(
            name = "routes_id"
    )
    private Long routesId;

    public RoleRoutesId() {
    }

    public RoleRoutesId(Long roleId, Long routesId) {
        this.roleId = roleId;
        this.routesId = routesId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoleRoutesId that = (RoleRoutesId) o;
        return Objects.equals(roleId, that.roleId) && Objects.equals(routesId, that.routesId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleId, routesId);
    }

    @Override
    public String toString() {
        return "RoleRoutesId{" +
                "roleId=" + roleId +
                ", routesId=" + routesId +
                '}';
    }
}
