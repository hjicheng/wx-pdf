package cn.hjc.pdf.controller;

import cn.hjc.pdf.entity.Register;
import cn.hjc.pdf.entity.User;
import cn.hjc.pdf.mapper.RegisterMapper;
import cn.hjc.pdf.service.RegisterService;
import cn.hjc.pdf.service.UserService;
import cn.hjc.pdf.util.AesCbcUtil;
import cn.hjc.pdf.util.HttpRequest;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RegisterService registerService;

    @RequestMapping("/isRegister")
    @ResponseBody
    public Map<String,Object> isRegister(@RequestParam String id){
        Map<String, Object> map = new HashMap<>();
        Register register = registerService.getByUserId(id);
        if (register != null){
            map.put("status",1);
        } else {
            map.put("status",0);
        }
        return map;
    }
    // 用户签到
    @ResponseBody
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Map<String, Object> register(String openId) {
        Map<String, Object> map = new HashMap<>();
        if (openId == null && openId.length() == 0) {
            map.put("status", 0);
            map.put("msg", "请授权");
            return map;
        }
        User user = userService.getByOpenId(openId);
        user.setMoney(user.getMoney() + 5);
        userService.updateUser(user);
        // 插入签到信息
        Register register = new Register();
        register.setCreateTime(new Date());
        register.setIsRegister(1);
        register.setUserId(user.getId());
        register.setId(UUID.randomUUID().toString());
        registerService.insert(register);

        map.put("userInfo", user);
        return map;
    }

    // 授权保存数据
    @RequestMapping(value = "/save", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getOpenId(@RequestParam String encryptedData, @RequestParam String iv, @RequestParam String code) {
        Map<String, Object> map = new HashMap<>();
        if (code == null || code.length() == 0) {
            map.put("status", 0);
            map.put("msg", "code 不能为空");
            return map;
        }
        String appId = "wx452a7b8d99fff180";
        String Secret = "028915122c999d70b737459aaffab836";
        //授权（必填）
        String grant_type = "authorization_code";


        //////////////// 1、向微信服务器 使用登录凭证 code 获取 session_key 和 openid ////////////////
        //请求参数
        String params = "appid=" + appId + "&secret=" + Secret + "&js_code=" + code + "&grant_type=" + grant_type;
        //发送请求
        String sr = HttpRequest.sendGet("https://api.weixin.qq.com/sns/jscode2session", params);
        //解析相应内容（转换成json对象）
        JSONObject json = JSONObject.fromObject(sr);
        //获取会话密钥（session_key）
        String session_key = json.get("session_key").toString();
        //用户的唯一标识（openid）
        String openid = (String) json.get("openid");
        // 通过openId获取用户数据，进行解析返回用户数据
        map = getUserInfo(openid, encryptedData, iv, session_key);

        return map;
    }

    // 用户首次登陆解密数据
    public Map<String, Object> getUserInfo(String openid, String encryptedData, String iv, String sessionkey) {
        HashMap<String, Object> map = new HashMap<>();

        try {
            String result = AesCbcUtil.decrypt(encryptedData, sessionkey, iv, "UTF-8");
            if (null != result && result.length() > 0) {
                map.put("status", 1);
                map.put("msg", "解密成功");

                JSONObject userInfoJSON = JSONObject.fromObject(result);

                // 通过openid获取用户信息
                User u = userService.getByOpenId(openid);
                User user = new User();
                if (u == null) {
                    // 存储用户数据
                    user.setAvatarUrl(userInfoJSON.get("avatarUrl").toString());
                    user.setNickName(userInfoJSON.get("nickName").toString());
                    user.setOpenId(openid);
                    user.setMoney(0);
                    user.setCreateTime(new Date());
                    user.setId(UUID.randomUUID().toString());
                    userService.insert(user);
                }
                Map<String, Object> userInfo = new HashMap();
                userInfo.put("nickName", userInfoJSON.get("nickName"));
                userInfo.put("avatarUrl", userInfoJSON.get("avatarUrl"));
                userInfo.put("id",u.getId());
                if (u == null) {
                    userInfo.put("money", 0);
                } else {
                    userInfo.put("money", u.getMoney());
                }
                userInfo.put("openid", openid);
                map.put("userInfo", userInfo);


                return map;
            } else {
                map.put("status", 0);
                map.put("msg", "解密失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
}
