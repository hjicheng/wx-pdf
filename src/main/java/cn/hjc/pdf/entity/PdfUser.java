package cn.hjc.pdf.entity;

import lombok.Data;

import java.util.Date;
import javax.persistence.*;

@Data
@Table(name = "pdf_user")
public class PdfUser {
    @Id
    private String id;

    @Column(name = "pdf_id")
    private String pdfId;

    @Column(name = "user_id")
    private String userId;

    /**
     * 1:浏览文件，2:收藏文件，3:下载文件，4:上传文件
     */
    private Integer type;

    @Column(name = "create_time")
    private Date createTime;
}