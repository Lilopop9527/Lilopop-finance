package com.auth.pojo.base;

import jakarta.persistence.*;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import java.sql.Timestamp;
import java.util.List;

@Entity(name = "Department")
@Table(
        name = "department",
        indexes = {
                @Index(name = "department_principalId",columnList = "principalId")
        }
)
public class Department {
    @Id
    @SequenceGenerator(
            name = "department_sequence",
            sequenceName = "department_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.IDENTITY,
            generator = "department_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;
    @Column(
            name = "name",
            length = 16
    )
    private String name;

    @Column(
            name = "principalId"
    )
    private Long principalId;

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
            mappedBy = "department"
    )
    private List<UserToDept> users;

    public Department() {
    }

    public Department(String name, Long principalId) {
        this.name = name;
        this.principalId = principalId;
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

    public Long getPrincipalId() {
        return principalId;
    }

    public void setPrincipalId(Long principalId) {
        this.principalId = principalId;
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

    public List<UserToDept> getUsers() {
        return users;
    }

    public void addUser(UserToDept user){
        if(!users.contains(user))
            users.add(user);
    }

    public void removeUser(UserToDept user){
        users.remove(user);
    }
    @Override
    public String toString() {
        return "Deptartment{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", principalId=" + principalId +
                '}';
    }
}
