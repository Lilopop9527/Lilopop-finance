package com.finance.pojo.base;

import jakarta.persistence.*;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import java.sql.Timestamp;

/**
 * @author: Lilopop
 * @description:
 */
@Entity(name = "entry")
@Table(
        name = "entry",
        indexes = {
                @Index(name = "entry_normal_parent",columnList = "parent")
        }
)
public class Entry {
    @Id
    @SequenceGenerator(
            name = "entry_sequence",
            sequenceName = "entry_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.IDENTITY,
            generator = "entry_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;
    @Column(
            name = "name",
            length = 16,
            columnDefinition = "",
            nullable = false
    )
    private String name;
    @Column(
            name = "parent",
            columnDefinition = "bigint default 0"
    )
    private Long parent;
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

    public Entry() {
    }

    public Entry(Long id, String name, Long parent, Timestamp createTime, Integer deleted) {
        this.id = id;
        this.name = name;
        this.parent = parent;
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

    public Long getParent() {
        return parent;
    }

    public void setParent(Long parent) {
        this.parent = parent;
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
        return "Entry{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", parent=" + parent +
                ", createTime=" + createTime +
                ", deleted=" + deleted +
                '}';
    }
}
