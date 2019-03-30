package cn.hjc.pdf.controller;

import cn.hjc.pdf.entity.Pdf;
import cn.hjc.pdf.entity.PdfUser;
import cn.hjc.pdf.entity.UserLike;
import cn.hjc.pdf.mapper.UserLikeMapper;
import cn.hjc.pdf.service.PdfService;
import cn.hjc.pdf.service.PdfUserService;
import cn.hjc.pdf.service.UserLikeService;
import cn.hjc.pdf.util.FastDFSClient;
import cn.hjc.pdf.util.PagedResult;
import cn.hjc.pdf.util.PdfJSONResult;
import cn.hjc.pdf.util.PdfUserEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.*;

/**
 * @Author: hjc
 * @Date: 2019/3/24 14:56
 * @Version 1.0
 */
@Controller
@RequestMapping("/pdf")
public class PdfController {

    @Autowired
    private PdfService pdfService;

    @Autowired
    private UserLikeService userLikeService;

    @Autowired
    private PdfUserService pdfUserService;

    private static final Map<String,Object> map = new HashMap<>();


    @RequestMapping("/file")
    public String index() {
        return "file";
    }

    /**
     * 文件上传类
     * 文件会自动绑定到MultipartFile中
     *
     * @param request 获取请求信息
     * @param file    上传的文件
     * @return 上传成功或失败结果
     * @throws IllegalStateException
     */
    @RequestMapping(value = "/uploads", method = RequestMethod.POST)
    @ResponseBody
    public String uploads(HttpServletRequest request, MultipartFile[] file) {
        try {
            //上传目录地址
            String uploadDir = request.getSession().getServletContext().getRealPath("/") + "upload/";
            System.err.println(uploadDir);
            //如果目录不存在，自动创建文件夹
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdir();
            }
            //遍历文件数组执行上传
            for (int i = 0; i < file.length; i++) {
                if (file[i] != null) {
                    //调用上传方法
                    executeUpload(uploadDir, file[i]);
                }
            }
            String fileAuthor = request.getParameter("fileAuthor");
            String fileDesc = request.getParameter("fileDesc");
            map.put("fileAuthor",fileAuthor);
            map.put("fileDesc",fileDesc);
            insertPdf(map);
        } catch (Exception e) {
            //打印错误堆栈信息
            e.printStackTrace();
            return "上传失败";
        }
        return "上传成功";
    }

    private void executeUpload(String uploadDir, MultipartFile file) throws Exception {
        //上传文件名
        String filename = file.getOriginalFilename();
        //获取上传到本地后的文件路径
        String localPath = uploadDir + filename;

        //服务器端保存的文件对象
        File serverFile = new File(uploadDir + filename);
        //将上传的文件写入到服务器端文件内
        file.transferTo(serverFile);

        // 将文件上传达fdfs
        FastDFSClient fastDFSClient = new FastDFSClient();
        String fdfsPath = fastDFSClient.testUpload(localPath);

        // 后缀名
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        // 文件名
        if (suffix.equals(".jpg") || suffix.equals(".png")) {
            // 该文件为文件的照片
            map.put("fileImage",fdfsPath);
        }
        if (suffix.equals(".pdf")) {
            Double size = Double.valueOf(file.getSize() / 1024);
            // 获取文件的大小
            if (size > 50 * 1024) {
                map.put("money",10);
            } else if(size > 40 * 1024) {
                map.put("money",8);
            } else if(size > 20 * 1024) {
                map.put("money",6);
            } else if(size > 10 * 1024){
                map.put("money",4);
            } else if(size > 5 * 1024) {
                map.put("money",2);
            } else {
                map.put("money",1);
            }
            map.put("filePath",fdfsPath);
            map.put("fileSize",size);
            map.put("fileName",filename.substring(0,filename.length()-4));
        }

    }

    // 数据存储
    public void insertPdf(Map<String,Object> map) {
        Pdf pdf = new Pdf();
        pdf.setId(UUID.randomUUID().toString());
        pdf.setFileName(map.get("fileName").toString());
        pdf.setFileMoney(Integer.valueOf(map.get("money").toString()));
        pdf.setFilePath(map.get("filePath").toString());
        pdf.setFileSize(Double.valueOf(map.get("fileSize").toString()));
        pdf.setImage(map.get("fileImage").toString());
        pdf.setDescrice(map.get("fileDesc").toString());
        pdf.setFileAuthor(map.get("fileAuthor").toString());
        pdf.setUploadTime(new Date());
        pdf.setLikes(0);
        pdf.setDown(0);
        pdf.setSee(0);
        pdfService.insert(pdf);
    }

    @RequestMapping("/uploadList")
    @ResponseBody
    public  Map<String,Object> list(@RequestParam String userId){

        Map<String,Object> map = new HashMap<>();
        if (userId == null && userId.length() == 0) {
            map.put("status",0);
            map.put("msg","请登录");
            return map;
        }
        List<Pdf> result = pdfService.getUploadList(userId);
        map.put("pdfList",result);
        map.put("num",result.size());
        return map;
    }


    @RequestMapping("/oneList")
    @ResponseBody
    public  Map<String,Object> list(@RequestParam String num,@RequestParam String userId){

        Map<String,Object> map = new HashMap<>();
        map.put("status",1);
        if (userId == null && userId.length() == 0) {
            map.put("status",0);
            map.put("msg","请登录");
            return map;
        }
        if (num == null && num.length() == 0) {
            map.put("status",0);
            map.put("msg","请登录");
            return map;
        }
        List<Pdf> result = pdfService.getOneList(num,userId);
        map.put("pdfList",result);
        return map;
    }


    @RequestMapping("/list")
    @ResponseBody
    public PdfJSONResult list(@RequestParam Integer page,@RequestParam String id){

        if (page == null){
            page = 1;
        }
        PagedResult result = pdfService.getList(page,2,id);
        return PdfJSONResult.ok(result);
    }

    @RequestMapping("/getMessage")
    @ResponseBody
    public PdfJSONResult getMessage(@RequestParam String id,@RequestParam String userId){
        if (id == null && id.length() == 0) {
            return PdfJSONResult.errorMsg("系统出错");
        }
        if (userId != null && !userId.equals("1")) {
            // 插入用户浏览记录
            insertMessage(id,userId, PdfUserEnum.getNum("浏览"));
        } else {
            insertMessage(id,"游客浏览",PdfUserEnum.getNum("浏览"));
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
            pdfUserService.delete(pdfId,userId,PdfUserEnum.getNum("点赞"));
            map.put("msg","收藏取消");
        } else {
            // 插入点赞数据
            userLike.setId(UUID.randomUUID().toString());
            userLikeService.insert(userLike);
            insertMessage(pdfId,userId,PdfUserEnum.getNum("点赞"));
            map.put("msg","收藏成功");
        }
        return map;
    }


    public void insertMessage(String pdfId,String userId,Integer num){

        // 插入数据之前先查看
        PdfUser pdfUser = new PdfUser();
        pdfUser.setCreateTime(new Date());
        pdfUser.setPdfId(pdfId);
        pdfUser.setUserId(userId);
        pdfUser.setType(num);

        if(pdfUserService.getByPdfUser(pdfUser) == null) {
            pdfUser.setId(UUID.randomUUID().toString());
            pdfUserService.insert(pdfUser);
        }
    }
}
