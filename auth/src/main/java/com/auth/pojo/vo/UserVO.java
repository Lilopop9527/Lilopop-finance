package com.auth.pojo.vo;

public class UserVO {
    private Long id;
    private String username;
    private String email;
    private String phone;
    private String img;
    private String token;

    public UserVO() {
    }

    public UserVO(Long id, String username, String email, String phone, String img, String token) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.img = img;
        this.token = token;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "UserVO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", img='" + img + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
