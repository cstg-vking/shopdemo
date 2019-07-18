package com.shopproject.controller;

import com.shopproject.controller.viewobject.UserVO;
import com.shopproject.error.BusinessException;
import com.shopproject.error.EmBusinessError;
import com.shopproject.response.CommonReturnType;
import com.shopproject.service.UserService;
import com.shopproject.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Controller("user")
@RequestMapping("/user")
public class UserController extends BaseController{

    @Autowired
    private UserService userService;

    @Autowired
    private HttpServletRequest httpServletRequest;

    //用户获取otp短信接口
    @RequestMapping("/getotp")
    @ResponseBody
    public CommonReturnType getOtp(@RequestParam(name = "telphone")String telphone){
        //按照一定的规则生成otp验证码
        Random random=new Random();
        int randomInt=random.nextInt(99999);  //[0,99999]
        randomInt+=10000;
        String otpCode=String.valueOf(randomInt);
        //将otp验证码同对应的用户的手机号关联,采用httpsession的方式绑定他的otpcode
        httpServletRequest.getSession().setAttribute(telphone,otpCode);

        //将otp验证码通过短信通道发送给用户(无接口 省略)
        System.out.println("telphone"+telphone+"otpcode"+otpCode);
        return CommonReturnType.creat(null);

    }

    @RequestMapping("/get")
    @ResponseBody
    public CommonReturnType getUser(@RequestParam(name="id")Integer id) throws BusinessException {
        //调用service服务获取对应id的用户对象
        UserModel userModel=userService.getUserById(id);

        //若获取的用户信息不存在
        if (userModel==null){
            throw new BusinessException(EmBusinessError.USER_NOT_EXIST);
        }

        //将核心领域模型用户对象转化为可供UI使用的viewobject
        UserVO userVO= convertFromModel(userModel);
        //返回通用对象
        return CommonReturnType.creat(userVO);
    }
    private UserVO convertFromModel(UserModel userModel){
        if (userModel==null){
            return null;
        }
        UserVO userVO=new UserVO();
        BeanUtils.copyProperties(userModel,userVO);
        return userVO;
    }

}
