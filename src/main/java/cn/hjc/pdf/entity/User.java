package cn.hjc.pdf.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
public class User {
    @Id
    private String id;

    @Column(name = "nick_name")
    private String nickName;

    @Column(name = "avatar_url")
    private String avatarUrl;

    @Column(name = "open_id")
    private String openId;

    private Integer money;

    @Column(name = "create_time")
    private Date createTime;

}