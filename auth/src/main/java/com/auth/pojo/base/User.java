package com.auth.pojo.base;

import jakarta.persistence.*;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import java.sql.Timestamp;
import java.util.List;

@Entity(name = "User")
@Table(
        name = "user",
        indexes = {
                @Index(name = "user_unique_username",columnList = "username",unique = true),
                @Index(name = "user_unique_email",columnList = "email",unique = true)
        }
)
public class User {
    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.IDENTITY,
            generator = "user_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;
    @Column(
            name = "username",
            length = 32
    )
    private String username;
    @Column(
            name = "password",
            nullable = false,
            length = 32
    )
    private String password;
    @Column(
            name = "phone",
            length = 16
    )
    private String phone;
    @Column(
            name = "email",
            length = 32
    )
    private String email;
    @Column(
            name = "img",
            length = 255
    )
    private String img;
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

    @OneToOne(
            mappedBy = "user",
            orphanRemoval = true,
            cascade = {CascadeType.PERSIST,CascadeType.REMOVE},
            fetch = FetchType.EAGER
    )

    private UserDetail userDetail;

    @OneToMany(
            cascade = {CascadeType.PERSIST,CascadeType.REMOVE},
            mappedBy = "user",
            fetch = FetchType.EAGER
    )
    private List<UserToRole> roles;
    @OneToMany(
            cascade = {CascadeType.PERSIST,CascadeType.REMOVE},
            mappedBy = "user",
            fetch = FetchType.EAGER
    )
    private List<UserToDept> depts;
    @OneToMany(
            cascade = {CascadeType.PERSIST,CascadeType.REMOVE},
            mappedBy = "user",
            fetch = FetchType.EAGER
    )
    private List<UserToStation> stations;




    public User() {
    }

    public User( String username, String password, String phone, String email, String img) {
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.img = img;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
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

    public UserDetail getUserDetail() {
        return userDetail;
    }

    public void setUserDetail(UserDetail userDetail) {
        this.userDetail = userDetail;
    }

    public List<UserToRole> getRoles() {
        return roles;
    }

    public void addRole(UserToRole role){
        if(!roles.contains(role)){
            roles.add(role);
        }
    }

    public void removeRole(UserToRole role){
        roles.remove(role);
    }

    public List<UserToDept> getDepts() {
        return depts;
    }

    public void addDept(UserToDept dept){
        if (!depts.contains(dept))
            depts.add(dept);
    }

    public void removeDept(UserToDept dept){
        depts.remove(dept);
    }

    public List<UserToStation> getStations() {
        return stations;
    }

    public void addStation(UserToStation station){
        if (!stations.contains(station))
            stations.add(station);
    }

    public void removeStation(UserToStation station){
        stations.remove(station);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", img='" + img + '\'' +
                '}';
    }
}
