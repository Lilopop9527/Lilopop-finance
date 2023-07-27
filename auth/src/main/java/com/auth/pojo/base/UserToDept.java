package com.auth.pojo.base;

import jakarta.persistence.*;

@Entity(name = "UserToDepartment")
@Table(name = "user_to_department")
public class UserToDept {
    @EmbeddedId
    private UserDeptId id;
    @ManyToOne
    @MapsId("userId")
    @JoinColumn(
            name = "user_id",
            foreignKey = @ForeignKey(
                    name = "user_department_user_id"
            )
    )
    private User user;
    @ManyToOne
    @MapsId("departmentId")
    @JoinColumn(
            name = "department_id",
            foreignKey = @ForeignKey(
                    name = "user_department_department_id"
            )
    )
    private Department department;

    public UserToDept(UserDeptId id, User user, Department department) {
        this.id = id;
        this.user = user;
        this.department = department;
    }

    public UserToDept() {
    }

    public UserDeptId getId() {
        return id;
    }

    public void setId(UserDeptId id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
