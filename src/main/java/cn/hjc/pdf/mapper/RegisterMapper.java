package cn.hjc.pdf.mapper;

import cn.hjc.pdf.entity.Register;
import cn.hjc.pdf.util.MyMapper;



public interface RegisterMapper extends MyMapper<Register> {

    Register getByUserId(String id);
}