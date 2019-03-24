package cn.hjc.pdf.mapper;

import cn.hjc.pdf.entity.Pdf;
import cn.hjc.pdf.entity.UserLike;
import cn.hjc.pdf.util.MyMapper;

import java.util.List;


public interface UserLikeMapper extends MyMapper<UserLike> {

    List<UserLike> selectById(String id);

}