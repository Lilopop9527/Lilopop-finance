package com.auth.pojo.base;

import jakarta.persistence.*;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import java.sql.Timestamp;
import java.util.List;
@Entity(name = "Role")
@Table(
        name = "role"
)
public class Role {
    @Id
    @SequenceGenerator(
            name = "role_sequence",
            sequenceName = "role_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.IDENTITY,
            generator = "role_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;
    @Column(
            name = "role_name",
            length = 16
    )
    private String roleName;
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
            columnDefinition = "TINYINT DEFAULT 0"
    )
    private Integer deleated;
    @OneToMany(
            cascade = {CascadeType.PERSIST,CascadeType.REMOVE},
            mappedBy = "role"
    )
    private List<UserToRole> users;
    @OneToMany(
            cascade = {CascadeType.PERSIST,CascadeType.REMOVE},
            mappedBy = "role"
    )
    private List<RoleToRoutes> routes;

    public Role() {
    }

    public Role(String roleName) {
        this.roleName = roleName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
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

    public List<UserToRole> getUsers() {
        return users;
    }

    public void addUser(UserToRole user){
        if (!users.contains(user))
            users.add(user);
    }

    public void removeUser(UserToRole user){
        users.remove(user);
    }

    public List<RoleToRoutes> getRoutes() {
        return routes;
    }

    public void addRoutes(RoleToRoutes route){
        if(!routes.contains(route))
            routes.add(route);
    }

    public void removeRoutes(RoleToRoutes route){
        routes.remove(route);
    }
    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", roleName='" + roleName + '\'' +
                ", cteateTime=" + cteateTime +
                ", deleated=" + deleated +
                '}';
    }
}
