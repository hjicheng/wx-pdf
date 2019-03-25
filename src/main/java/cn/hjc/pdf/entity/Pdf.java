package cn.hjc.pdf.entity;

import lombok.Data;

import javax.persistence.*;

@Data
public class Pdf {
    @Id
    private String id;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_size")
    private Double fileSize;

    @Column(name = "file_path")
    private String filePath;

    private Integer likes;

    private Integer see;

    private Integer down;

    @Column(name = "file_money")
    private Integer fileMoney;

    @Column(name = "upload_user")
    private String uploadUser;

    private String categories;

    @Column(name = "file_author")
    private String fileAuthor;

    @Column(name = "upload_time")
    private String uploadTime;

    private String descrice;

    @Column(name = "is_like")
    private String isLike;
}