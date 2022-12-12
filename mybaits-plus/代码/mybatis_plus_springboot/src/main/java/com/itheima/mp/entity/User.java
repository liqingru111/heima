package com.itheima.mp.entity;

import lombok.Data;

/**
 * 实体类的属性名和数据库的字段名 自动映射：
 *  1. 名称一样
 *  2. 数据库字段使用_分割，实体类属性名使用驼峰名称
 */

//@TableName("tb_user")
@Data
public class User {


    //设置id生成策略:AUTO 数据库自增
    //@TableId(type = IdType.AUTO)
    private Long id;
    //@TableField("user_name")
    private String userName;

    private String password;
    private String name;
    private Integer age;
    private String email;

    //不希望该值存入数据库
    //@TableField(exist = false)
    //private String info;

}
