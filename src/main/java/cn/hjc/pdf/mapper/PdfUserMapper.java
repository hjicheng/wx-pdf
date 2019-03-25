package cn.hjc.pdf.mapper;

import cn.hjc.pdf.entity.PdfUser;
import cn.hjc.pdf.util.MyMapper;

import java.util.Map;

public interface PdfUserMapper extends MyMapper<PdfUser> {
    void deleteById(Map<String,Object> map);
}