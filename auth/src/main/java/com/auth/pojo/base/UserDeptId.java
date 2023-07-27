package com.auth.pojo.base;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserDeptId implements Serializable {
    @Column(
            name = "user_id"
    )
    private Long userId;
    @Column(
            name = "department_id"
    )
    private Long deptId;

    public UserDeptId() {
    }

    public UserDeptId(Long userId, Long deptId) {
        this.userId = userId;
        this.deptId = deptId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDeptId that = (UserDeptId) o;
        return Objects.equals(userId, that.userId) && Objects.equals(deptId, that.deptId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, deptId);
    }

    @Override
    public String toString() {
        return "UserDeptId{" +
                "userId=" + userId +
                ", deptId=" + deptId +
                '}';
    }
}
