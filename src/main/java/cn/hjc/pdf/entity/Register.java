package cn.hjc.pdf.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Data
public class Register {
    @Id
    private String id;

    @Column(name = "is_register")
    private Integer isRegister;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "create_time")
    private Date createTime;

}