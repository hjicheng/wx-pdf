package cn.hjc.pdf.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author: hjc
 * @Date: 2019/3/24 20:15
 * @Version 1.0
 */
@Data
@Table(name = "user_like")
public class UserLike {

    @Id
    private String id;

    @Column(name = "like_pdf")
    private String likePdf;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "is_like")
    private String isLike;

}
