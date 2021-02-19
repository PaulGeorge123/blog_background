package com.ase.springsecurity.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @author 
 * InnoDB free: 10240 kB
 */
@Data
public class User implements Serializable {
    private Integer id;

    private String nickname;

    private String username;

    private String password;

    private String role;

    private Date datetimes;

    private Integer gender;

    private Integer logicRemove;

    private static final long serialVersionUID = 1L;
}