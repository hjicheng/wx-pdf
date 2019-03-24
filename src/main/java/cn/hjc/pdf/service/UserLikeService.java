package cn.hjc.pdf.service;

import cn.hjc.pdf.entity.Register;
import cn.hjc.pdf.entity.UserLike;
import cn.hjc.pdf.mapper.RegisterMapper;
import cn.hjc.pdf.mapper.UserLikeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author: hjc
 * @Date: 2019/3/23 21:24
 * @Version 1.0
 */
@Service
public class UserLikeService {

    @Autowired
    private UserLikeMapper userLikeMapper;

    public void insert(UserLike userLike){
        userLikeMapper.insert(userLike);
    }

    public UserLike getById(UserLike userLike){
        return userLikeMapper.selectOne(userLike);
    }

    public int delete(UserLike userLike){
        return userLikeMapper.delete(userLike);
    }
}
