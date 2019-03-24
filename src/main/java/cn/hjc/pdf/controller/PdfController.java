package cn.hjc.pdf.controller;

import cn.hjc.pdf.entity.Pdf;
import cn.hjc.pdf.entity.UserLike;
import cn.hjc.pdf.mapper.UserLikeMapper;
import cn.hjc.pdf.service.PdfService;
import cn.hjc.pdf.service.UserLikeService;
import cn.hjc.pdf.util.PagedResult;
import cn.hjc.pdf.util.PdfJSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.jws.Oneway;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @Author: hjc
 * @Date: 2019/3/24 14:56
 * @Version 1.0
 */
@RestController
@RequestMapping("/pdf")
public class PdfController {

    @Autowired
    private PdfService pdfService;

    @Autowired
    private UserLikeService userLikeService;

    @RequestMapping("/list")
    @ResponseBody
    public PdfJSONResult list(@RequestParam Integer page,@RequestParam String id){

        if (page == null){
            page = 1;
        }
        PagedResult result = pdfService.getList(page,200000,id);
        return PdfJSONResult.ok(result);
    }

    @RequestMapping("/getMessage")
    @ResponseBody
    public PdfJSONResult getMessage(@RequestParam String id){
        if (id == null && id.length() == 0) {
            return PdfJSONResult.errorMsg("系统出错");
        }
        Pdf pdf = pdfService.getMessage(id);
        if (pdf.getDescrice() == null) {
            pdf.setDescrice("暂无");
        }
        return PdfJSONResult.ok(pdf);
    }

    @RequestMapping("/likeOrNoLike")
    @ResponseBody
    public Map<String,Object> likePdf(@RequestParam String userId,@RequestParam String pdfId){
        Map<String,Object> map = new HashMap<>();
        map.put("status",1);
        if (userId == null && userId.length() == 0) {
            map.put("status",0);
            map.put("msg","请登录");
            return map;
        }
        if (pdfId == null && pdfId.length() == 0) {
            map.put("status",0);
            map.put("msg","请登录");
            return map;
        }
        UserLike userLike = new UserLike();
        userLike.setLikePdf(pdfId);
        userLike.setUserId(userId);
        if (userLikeService.getById(userLike) != null) {
            // 删除点赞
            userLikeService.delete(userLike);
            map.put("msg","收藏取消");
        } else {
            // 插入点赞数据
            userLike.setId(UUID.randomUUID().toString());
            userLikeService.insert(userLike);
            map.put("msg","收藏成功");
        }
        return map;
    }
}
