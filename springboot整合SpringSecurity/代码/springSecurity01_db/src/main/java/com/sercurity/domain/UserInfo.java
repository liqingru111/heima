package com.sercurity.domain;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**用户实体类
 */
@Data
@Entity
@Table(name = "user")
public class UserInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id ;
    @Column(name = "username")
    private String userName;

    private String password;

    private String roles;
}