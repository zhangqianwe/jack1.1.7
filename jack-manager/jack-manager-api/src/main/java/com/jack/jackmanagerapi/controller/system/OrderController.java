package com.jack.jackmanagerapi.controller.system;

import com.jack.api.ResponseCode;
import com.jack.api.ResponseMessage;
import com.jack.pojo.User;
import com.jack.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

/**
 * @Auther: zhangqianwen
 * @Date: 2020/6/22 13:03
 * @Description:
 */
@Controller
@Slf4j
@RequestMapping("/Order")
public class OrderController {
    @Autowired
    private UserService userService;

    /**
     * 登录
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("CreateOrder")
    @ResponseBody
    public ResponseMessage login() throws Exception {
        log.info("登录来了");

//        long start = System.currentTimeMillis();
//        IPage<User> userMyPage = new Page<>(pageParam.getPageNum(),pageParam.getPageSize());
//        userService.lambdaQuery().orderByAsc(User::getId).page(userMyPage);
////        IPage<Map<String, Object>> userMyPage = userService.userService(pageParam);
//        Page page=new Page(pageParam.getPageNum(),pageParam.getPageSize());          //1表示当前页，而10表示每页的显示显示的条目数
//        IPage iPage = userService.selectUserPage(page, "NORMAL");
//        long end = System.currentTimeMillis();
//        System.out.println("总条数 ------> " + userMyPage.getRecords().size());
//        System.out.println("当前页数 ------> " + userMyPage.getCurrent());
//        System.out.println("当前每页显示数 ------> " + userMyPage.getSize());

        HashMap<String, Object> hashMap = new HashMap<>();


        return new ResponseMessage(ResponseCode.SUCCESS);
    }
}
