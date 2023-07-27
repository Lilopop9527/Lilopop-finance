package com.auth.pojo.base;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserStationId implements Serializable {
    @Column(
            name = "user_id"
    )
    private Long userId;

    @Column(
            name = "station_id"
    )
    private Long stationId;

    public UserStationId() {
    }

    public UserStationId(Long userId, Long stationId) {
        this.userId = userId;
        this.stationId = stationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserStationId that = (UserStationId) o;
        return Objects.equals(userId, that.userId) && Objects.equals(stationId, that.stationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, stationId);
    }

    @Override
    public String toString() {
        return "UserStationId{" +
                "userId=" + userId +
                ", stationId=" + stationId +
                '}';
    }
}
