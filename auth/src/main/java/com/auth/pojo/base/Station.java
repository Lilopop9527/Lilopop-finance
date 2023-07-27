package com.auth.pojo.base;

import jakarta.persistence.*;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import java.sql.Timestamp;
import java.util.List;

@Entity(name = "Station")
@Table(
        name = "station"
)
public class Station {
    @Id
    @SequenceGenerator(
            name = "station_sequence",
            sequenceName = "station_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.IDENTITY,
            generator = "station_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;
    @Column(
            name = "name",
            length = 32
    )
    private String name;

    @Column(
            name = "create_time",
            nullable = false,
            updatable = false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
    )
    @Generated(GenerationTime.INSERT)
    private Timestamp createTime;
    @Column(
            name = "deleated",
            columnDefinition = "tinyint DEFAULT 0"
    )
    private Integer deleated;
    @OneToMany(
            cascade = {CascadeType.PERSIST,CascadeType.REMOVE},
            mappedBy = "station"
    )
    private List<UserToStation> users;

    public Station() {
    }

    public Station(String name) {
        this.name = name;
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

    public Timestamp getCteateTime() {
        return createTime;
    }

    public void setCteateTime(Timestamp cteateTime) {
        this.createTime = cteateTime;
    }

    public Integer getDeleated() {
        return deleated;
    }

    public void setDeleated(Integer deleated) {
        this.deleated = deleated;
    }

    public List<UserToStation> getUsers() {
        return users;
    }

    public void addUser(UserToStation user){
        if (!users.contains(user))
            users.add(user);
    }

    public void removeUser(UserToStation user){
        users.remove(user);
    }

    @Override
    public String toString() {
        return "Station{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
