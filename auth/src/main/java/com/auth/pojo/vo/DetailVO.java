package com.auth.pojo.vo;

public class DetailVO {
    private Long id;
    private String firstName;
    private String lastName;
    private String birthday;
    private String cardNumber;
    private Integer height;
    private Integer weight;
    private String bloodType;
    private String hobby;
    private String introduce;
    private Long userId;

    public DetailVO() {
    }

    public DetailVO(Long id, String firstName, String lastName, String birthday, String cardNumber, Integer height, Integer weight, String bloodType, String hobby, String introduce, Long userId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.cardNumber = cardNumber;
        this.height = height;
        this.weight = weight;
        this.bloodType = bloodType;
        this.hobby = hobby;
        this.introduce = introduce;
        this.userId = userId;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "DetailVO{" +
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
                ", userId=" + userId +
                '}';
    }
}
