package cn.hjc.pdf.mapper;

import cn.hjc.pdf.entity.User;
import cn.hjc.pdf.util.MyMapper;

public interface UserMapper extends MyMapper<User> {

    User getByOpenId(String openid);
}