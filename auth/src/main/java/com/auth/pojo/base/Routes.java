package com.auth.pojo.base;

import jakarta.persistence.*;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import java.sql.Timestamp;
import java.util.List;

@Entity(name = "Routes")
@Table(
        name = "routes"
)
public class Routes {
    @Id
    @SequenceGenerator(
            name = "routes_sequence",
            sequenceName = "routes_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.IDENTITY,
            generator = "routes_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;
    @Column(
            name = "name",
            length = 64
    )
    private String name;
    @Column(
            name = "url"
    )
    private String url;
    @Column(
            name = "create_time",
            nullable = false,
            updatable = false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
    )
    @Generated(GenerationTime.INSERT)
    private Timestamp cteateTime;
    @Column(
            name = "deleated",
            columnDefinition = "tinyint default 0"
    )
    private Integer deleated;
    @OneToMany(
            cascade = {CascadeType.PERSIST,CascadeType.REMOVE},
            mappedBy = "routes"
    )
    private List<RoleToRoutes> roles;

    public Routes() {
    }

    public Routes(String name, String url) {
        this.name = name;
        this.url = url;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Timestamp getCteateTime() {
        return cteateTime;
    }

    public void setCteateTime(Timestamp cteateTime) {
        this.cteateTime = cteateTime;
    }

    public Integer getDeleated() {
        return deleated;
    }

    public void setDeleated(Integer deleated) {
        this.deleated = deleated;
    }

    public List<RoleToRoutes> getRoles() {
        return roles;
    }

    public void addRole(RoleToRoutes role){
        if(!roles.contains(role))
            roles.add(role);
    }
    public void removeRole(RoleToRoutes role){
        roles.remove(role);
    }

    @Override
    public String toString() {
        return "Routes{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
