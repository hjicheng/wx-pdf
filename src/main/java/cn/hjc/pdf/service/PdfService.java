package cn.hjc.pdf.service;

import cn.hjc.pdf.entity.Pdf;
import cn.hjc.pdf.entity.UserLike;
import cn.hjc.pdf.mapper.PdfMapper;
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
public class PdfService {

    @Autowired
    private PdfMapper pdfMapper;

    @Autowired
    private UserLikeMapper userLikeMapper;

    public PagedResult getList(Integer page, Integer pageSize,String id){
        PageHelper.startPage(page,pageSize);
        List<Pdf> list = pdfMapper.selectAll();
        List<UserLike> likes = userLikeMapper.selectById(id);
        PageInfo<Pdf> pageInfoList = new PageInfo<Pdf>(list);
        PagedResult pagedResult = new PagedResult();
        pagedResult.setPage(page);
        pagedResult.setTotal(pageInfoList.getPages());
        pagedResult.setRows(format(list,likes));
        pagedResult.setRecords(pageInfoList.getTotal());
        return pagedResult;
    }

    public List<Pdf> format(List<Pdf> list,List<UserLike> likes) {
        for (UserLike userLike : likes) {
            for(Pdf pdf : list) {
                if (userLike.getLikePdf().equals(pdf.getId())) {
                    pdf.setIsLike("1");
                    break;
                }
            }
        }
        return list;
    }
    public List<Pdf> getOneList(String num,String userId){
        Map<String,Object> map = new HashMap<>();
        map.put("num",num);
        map.put("userId",userId);
        List<Pdf> list = pdfMapper.selectOneList(map);
        return list;
    }
    public Pdf getMessage(String id){
        return pdfMapper.selectByPrimaryKey(id);
    }
}
