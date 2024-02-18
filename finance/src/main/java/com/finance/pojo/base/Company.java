package com.finance.pojo.base;

import jakarta.persistence.*;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import java.sql.Timestamp;

/**
 * @author: Lilopop
 * @description:
 */
@Entity(name = "company")
@Table(name = "company")
public class Company {
    @Id
    @SequenceGenerator(
            name = "company_sequence",
            sequenceName = "company_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.IDENTITY,
            generator = "company_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;
    @Column(
            name = "name",
            length = 32,
            columnDefinition = "",
            nullable = false
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
            name = "deleted",
            columnDefinition = "tinyint default 0"
    )
    private Integer deleted;

    public Company() {
    }

    public Company(Long id, String name, Timestamp createTime, Integer deleted) {
        this.id = id;
        this.name = name;
        this.createTime = createTime;
        this.deleted = deleted;
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

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", createTime=" + createTime +
                ", deleted=" + deleted +
                '}';
    }
}
