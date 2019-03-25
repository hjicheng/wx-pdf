package cn.hjc.pdf.mapper;

import cn.hjc.pdf.entity.Pdf;
import cn.hjc.pdf.util.MyMapper;

import java.util.List;
import java.util.Map;


public interface PdfMapper extends MyMapper<Pdf> {

    List<Pdf> selectOneList(Map<String,Object> map);

}