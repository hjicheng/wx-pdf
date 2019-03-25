package cn.hjc.pdf.service;

import cn.hjc.pdf.entity.Pdf;
import cn.hjc.pdf.entity.PdfUser;
import cn.hjc.pdf.entity.UserLike;
import cn.hjc.pdf.mapper.PdfMapper;
import cn.hjc.pdf.mapper.PdfUserMapper;
import cn.hjc.pdf.mapper.UserLikeMapper;
import cn.hjc.pdf.util.PagedResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: hjc
 * @Date: 2019/3/24 14:57
 * @Version 1.0
 */
@Service
public class PdfUserService {

    @Autowired
    private PdfUserMapper pdfUserMapper;

    public int insert(PdfUser pdfUser){
        return pdfUserMapper.insert(pdfUser);
    }

    public void delete(String pdfId,String userId,Integer num) {
        Map<String,Object> map = new HashMap<>();
        map.put("pdfId",pdfId);
        map.put("userId",userId);
        map.put("num",num);
        pdfUserMapper.deleteById(map);
    }
}
