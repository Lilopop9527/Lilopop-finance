package com.auth.pojo.base;

import jakarta.persistence.*;

@Entity(name = "UserToStation")
@Table(name = "user_to_station")
public class UserToStation {
    @EmbeddedId
    private UserStationId id;
    @ManyToOne
    @MapsId("userId")
    @JoinColumn(
            name = "user_id",
            foreignKey = @ForeignKey(
                    name = "user_to_station_user_id"
            )
    )
    private User user;
    @ManyToOne
    @MapsId("stationId")
    @JoinColumn(
            name = "station_id",
            foreignKey = @ForeignKey(
                    name = "user_to_station_station_id"
            )
    )
    private Station station;

    public UserToStation() {
    }

    public UserToStation(UserStationId id, User user, Station station) {
        this.id = id;
        this.user = user;
        this.station = station;
    }

    public UserStationId getId() {
        return id;
    }

    public void setId(UserStationId id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }
}
