package com.auth.pojo.base;

import jakarta.persistence.*;

@Entity(name = "role_to_routes")
@Table(name = "role_to_routes")
public class RoleToRoutes {

    @EmbeddedId
    private RoleRoutesId id;
    @ManyToOne
    @MapsId("roleId")
    @JoinColumn(
            name = "role_id",
            foreignKey = @ForeignKey(
                    name = "role_routes_role_id"
            )
    )
    private Role role;
    @ManyToOne
    @MapsId("routesId")
    @JoinColumn(
            name = "routes_id",
            foreignKey = @ForeignKey(
                    name = "role_routes_routes_id"
            )
    )
    private Routes routes;

    public RoleToRoutes() {
    }

    public RoleToRoutes(RoleRoutesId id, Role role, Routes routes) {
        this.id = id;
        this.role = role;
        this.routes = routes;
    }

    public RoleRoutesId getId() {
        return id;
    }

    public void setId(RoleRoutesId id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Routes getRoutes() {
        return routes;
    }

    public void setRoutes(Routes routes) {
        this.routes = routes;
    }
}
