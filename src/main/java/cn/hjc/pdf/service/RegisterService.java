package cn.hjc.pdf.service;

import cn.hjc.pdf.entity.Register;
import cn.hjc.pdf.mapper.RegisterMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Author: hjc
 * @Date: 2019/3/23 21:24
 * @Version 1.0
 */
@Service
public class RegisterService {

    @Autowired
    private RegisterMapper registerMapper;

    public void insert(Register register){
        registerMapper.insert(register);
    }

    public Register getByUserId(String id) {
        return registerMapper.getByUserId(id);
    }
}
