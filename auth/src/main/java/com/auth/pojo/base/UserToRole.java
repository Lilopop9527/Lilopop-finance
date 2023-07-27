package com.auth.pojo.base;

import jakarta.persistence.*;

@Entity(name = "UserToRole")
@Table(name = "user_to_role")
public class UserToRole {
    @EmbeddedId
    private UserRoleId id;
    @ManyToOne
    @MapsId("userId")
    @JoinColumn(
            name = "user_id",
            foreignKey = @ForeignKey(
                    name = "user_role_user_id"
            )
    )
    private User user;
    @ManyToOne
    @MapsId("roleId")
    @JoinColumn(
            name = "role_id",
            foreignKey = @ForeignKey(
                    name = "user_role_role_id"
            )
    )
    private Role role;

    public UserToRole() {
    }

    public UserToRole(User user, Role role) {
        this.user = user;
        this.role = role;
    }

    public UserToRole(UserRoleId id, User user, Role role) {
        this.id = id;
        this.user = user;
        this.role = role;
    }

    public UserRoleId getId() {
        return id;
    }

    public void setId(UserRoleId id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
