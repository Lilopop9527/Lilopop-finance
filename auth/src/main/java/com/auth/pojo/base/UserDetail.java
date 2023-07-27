package com.auth.pojo.base;

import jakarta.persistence.*;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import java.sql.Timestamp;

@Entity(name = "UserDetail")
@Table(
        name = "user_detail",
        indexes = {
                @Index(name = "user_detail_normal_first_name",columnList = "first_name"),
                @Index(name = "user_detail_normal_last_name",columnList = "last_name"),
                @Index(name = "user_detail_unique_card_number",columnList = "card_number",unique = true)
        }
)
public class UserDetail {
    @Id
    @SequenceGenerator(
            name = "user_detail_sequence",
            sequenceName = "user_detail_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.IDENTITY,
            generator = "user_detail_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;
    @Column(
            name = "first_name"
    )
    private String firstName;
    @Column(
            name = "last_name"
    )
    private String lastName;
    @Column(
            name = "birthday"
    )
    private String birthday;
    @Column(
            name = "card_number"
    )
    private String cardNumber;
    @Column(
            name = "height"
    )
    private Integer height;
    @Column(
            name = "weight"
    )
    private Integer weight;
    @Column(
            name = "blood_type"
    )
    private String bloodType;
    @Column(
            name = "hobby"
    )
    private String hobby;
    @Column(
            name = "introduce"
    )
    private String introduce;
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
            cascade = {CascadeType.PERSIST,CascadeType.REMOVE},
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "user_id_fk"
            )
    )
    private User user;

    public UserDetail() {
    }

    public UserDetail(String firstName, String lastName, String birthday, String cardNumber, Integer height, Integer weight, String bloodType, String hobby, String introduce) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.cardNumber = cardNumber;
        this.height = height;
        this.weight = weight;
        this.bloodType = bloodType;
        this.hobby = hobby;
        this.introduce = introduce;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "UserDetail{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthday='" + birthday + '\'' +
                ", cardNumber='" + cardNumber + '\'' +
                ", height=" + height +
                ", weight=" + weight +
                ", bloodType='" + bloodType + '\'' +
                ", hobby='" + hobby + '\'' +
                ", introduce='" + introduce + '\'' +
                '}';
    }
}
