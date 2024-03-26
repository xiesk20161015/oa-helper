package cn.dazd.oa.sync.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Timestamp;

@Setter
@Getter
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "CronTask")
public class CronTaskEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "taskName")
    private String taskName;
    @Basic
    @Column(name = "taskClassWithMethodName")
    private String taskClassWithMethodName;
    @Basic
    @Column(name = "taskStatus")
    private int taskStatus;
    @Basic
    @Column(name = "schedulingPattern")
    private String schedulingPattern;
    @Basic
    @Column(name = "createTime")
    private Timestamp createTime;
    @Basic
    @Column(name = "updateTime")
    private Timestamp updateTime;

}
