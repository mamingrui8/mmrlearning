package com.jpa.pojo;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;


@Data
@Entity //申明当前类是一个实体类
@Table(name = "tb_users")
public class User implements Serializable {
    private static final long serialVersionUID = 1208995053064500582L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //STRATEGY=GenerationType.IDENTITY 自增长，相当于auto-increment,从自然数1开始依次递增
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "username")
    private String username;

    @Column(name = "user_age")
    private Integer userAge;
}
