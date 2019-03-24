package cn.hjc.pdf.service;

import cn.hjc.pdf.entity.User;
import cn.hjc.pdf.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public int insert(User user){
        return userMapper.insert(user);
    }

    public User getByOpenId(String openid) {
        return userMapper.getByOpenId(openid);
    }

    public int updateUser(User user){
        return userMapper.updateByPrimaryKeySelective(user);
    }
}
