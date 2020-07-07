package com.jack.jackmanagerapi.controller.system;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jack.api.ResponseCode;
import com.jack.api.ResponseMessage;
import com.jack.common.jwt.JwtTool;
import com.jack.customPojo.Parameter;
import com.jack.customPojo.RolePojo;
import com.jack.jackOnline.Department;
import com.jack.jackOnline.SysRole;
import com.jack.jackOnline.SysUser;
import com.jack.pojo.*;

import javax.servlet.http.HttpServletRequest;

import com.jack.service.DepartmentService;
import com.jack.service.SysMenuService;
import com.jack.service.SysRoleService;
import com.jack.service.UserService;
import com.jack.common.jwt.JwtManageTool;
import com.jack.utils.MD5;
import com.jack.utils.TimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

//        System.out.println(ZonedDateTime.now(ZoneId.of("Asia/Shanghai")));
//        System.out.println(LocalDateTime.now(ZoneId.of("Asia/Shanghai")));

/**
 * @Auther: zhangqianwen
 * @Date: 2020/6/11 14:31
 * @Description:
 */


@Controller
@Slf4j
@RequestMapping("/System")
@ComponentScan(basePackages = {"com.jack.service", "com.jack.mapper", "com.jack.domain"})
public class LoginController {
    @Autowired
    private UserService userService;
    @Autowired
    private SysMenuService sysMenuService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private SysRoleService sysRoleService;

    /**
     * 登录
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("login")
    @ResponseBody
    public ResponseMessage login(@RequestBody User user) throws Exception {
        log.info("登录来了");
        HashMap<String, Object> hashMap = new HashMap<>();
        if (StringUtils.isBlank(user.getUsername())) {
            return new ResponseMessage(ResponseCode.USER_NAME_PASSWORD_FALSE, "用户名或密码不正确!");
        }
        if (StringUtils.isBlank(user.getPassword())) {
            return new ResponseMessage(ResponseCode.USER_NAME_PASSWORD_FALSE, "用户名或密码不正确!");
        }
        return userService.selectByUserNameAndPassWord(user.getUsername(), user.getPassword());
//        if ((Boolean) objectHashMap.get("errMsg")) {
//            return new ResponseMessage(ResponseCode.USER_NOT_ACTIVE,objectHashMap.get("msg"));
//        }
//        hashMap.put("token", objectHashMap.get("token"));
//        hashMap.put("userInfor", objectHashMap.get("userInfor"));


//        return new ResponseMessage(ResponseCode.SUCCESS, objectHashMap);
    }

    /**
     * 获取详细信息
     *
     * @param request
     * @return
     * @throws InterruptedException
     */
    @RequestMapping(value = "getInfor")
    @ResponseBody
    public ResponseMessage getInfor(HttpServletRequest request) throws InterruptedException {
        long start = System.currentTimeMillis();
        Long userIdByAuthorization = JwtManageTool.getUserIdByAuthorization(request);
        log.info("获取详细信息来了" + userIdByAuthorization);

        if (null == userIdByAuthorization) {
            return new ResponseMessage(ResponseCode.TOKEN_ERROR);
        }
        Map<String, Object> sysUserInfoByAuthorization = JwtManageTool.getSysUserInfoByAuthorization(request);
        long end = System.currentTimeMillis();
//
        HashMap<String, Object> hashMap = new HashMap<>();
        HashMap<String, Object> rolesMap = new HashMap<>();
        ArrayList<Object> rolesList = new ArrayList<>();
//        角色[{"role":"admin","authority":[1,2,3]},{"role":"juese","authority":[1,2,3]}]
        rolesMap.put("role","admin");
        rolesMap.put("authority",new int[]{2,3,4,7,8});
        rolesList.add(rolesMap);
        hashMap.put("roles", rolesList);
        hashMap.put("name", "system");
        hashMap.put("introduction", "zqw");
        hashMap.put("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        log.info("返回对象:   " + rolesList);
        return new ResponseMessage(ResponseCode.SUCCESS, hashMap);
    }


    /**
     *
     * @param request
     * @return
     * @throws InterruptedException
     * @throws IOException
     */
    @RequestMapping(value = "role/generateRoutes")
    @ResponseBody
    public ResponseMessage generateRoutes(HttpServletRequest request){
        MenuPojo menuPojo = new MenuPojo();
        menuPojo.setAlwaysShow(true);
        Meta meta = new Meta();
        meta.setIcon("zip");
        meta.setTitle("Zip");
        menuPojo.setMeta(meta);
        menuPojo.setName("Zip");
        menuPojo.setPath("/zip");
        menuPojo.setRedirect("/zip/download");
        return new ResponseMessage(ResponseCode.SUCCESS, menuPojo);
    }
    /**
     * 退出
     *
     * @param request
     * @return
     * @throws InterruptedException
     */
    @RequestMapping(value = "logout")
    @ResponseBody
    public ResponseMessage logout(HttpServletRequest request) throws InterruptedException, IOException {
//        MQUtils mqUtils =new MQUtils();
//        Map<String, Integer> mqCountMap = mqUtils.getMQCountMap("jack");
        return new ResponseMessage(ResponseCode.SUCCESS, "退出成功!");
    }


    /**
     * 增加用户
     *
     * @return
     * @throws InterruptedException
     */
    @RequestMapping("/addUser")
    @ResponseBody
    public ResponseMessage addUser(@RequestBody Parameter parameter) throws InterruptedException {
        SysUser sysUser = new SysUser();
        sysUser.setCreateTime(new Date());
        sysUser.setStatus(2);
        sysUser.setPassword(parameter.getPassword());
        sysUser.setRealname(parameter.getRealname());
        sysUser.setDeptId(Integer.parseInt(parameter.getDeptId()));
        sysUser.setEmail(parameter.getEmail());
        sysUser.setPost(parameter.getPost());
        sysUser.setSex(parameter.getSex());
        sysUser.setLogin_name(parameter.getLogin_name());
        sysUser.setTelphone(parameter.getTelphone());
        System.out.println(sysUser.getLogin_name() + TimeUtils.getSysYear());
        sysUser.setPassword(MD5.getMD5String(sysUser.getLogin_name() + TimeUtils.getSysYear()));
        sysUser.setCreateTime(new Date());
        userService.save(sysUser);

        return new ResponseMessage(ResponseCode.SUCCESS);
    }

    /**
     * 删除用户
     *
     * @param request
     * @return
     * @throws InterruptedException
     */
    @RequestMapping(value = "deleteUser")
    @ResponseBody
    public ResponseMessage deleteUser(HttpServletRequest request) throws InterruptedException {
        long start = System.currentTimeMillis();
        String token = null;
        try {
            token = JwtTool.createToken(111L);
        } catch (Exception e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println("执行时间:" + (end - start));
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("token", token);
        return new ResponseMessage(ResponseCode.SUCCESS, hashMap);
    }

    /**
     * 新建角色
     *
     * @param request
     * @return
     * @throws InterruptedException
     */
    @RequestMapping(value = "role/add")
    @ResponseBody
    public ResponseMessage roleAdd(HttpServletRequest request, @RequestBody Parameter parameter) {
        System.out.println("角色名称" + parameter.getRoleName());
        SysRole sysRole = new SysRole();
        sysRole.setRoleName(parameter.getRoleName());
        sysRole.setStatus(1);

        sysRoleService.save(sysRole);
        return new ResponseMessage(ResponseCode.SUCCESS);
    }

    /**
     * 删除角色
     *
     * @param request
     * @return
     * @throws InterruptedException
     */
    @RequestMapping(value = "deleteRole")
    @ResponseBody
    public ResponseMessage deleteRole(HttpServletRequest request) throws InterruptedException {
        long start = System.currentTimeMillis();
        String token = null;
        try {
            token = JwtTool.createToken(111L);
        } catch (Exception e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println("执行时间:" + (end - start));
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("token", token);
        return new ResponseMessage(ResponseCode.SUCCESS, hashMap);
    }

    /**
     * 获取角色列表
     *
     * @param request
     * @return
     * @throws InterruptedException
     */
    @RequestMapping(value = "role/getRoleByType")
    @ResponseBody
    public ResponseMessage getRoleByType(HttpServletRequest request) {
        List<RolePojo> List = sysRoleService.getRoleList();
        return new ResponseMessage(ResponseCode.SUCCESS, List);
    }

    /**
     * 角色绑定员工
     * @param
     * @return
     */
    @RequestMapping(value = "role/relatedUser")
    @ResponseBody
    public ResponseMessage roleRelatedUser(@RequestBody Parameter parameter) {
        String roleIds = parameter.getRoleIds();
        String[] split = parameter.getUserIds().split(",");
        System.out.println("角色ID"+roleIds);
        System.out.println("用户ID"+split);
        List<RolePojo> List = sysRoleService.getRoleList();
        return new ResponseMessage(ResponseCode.SUCCESS, List);
    }



    /**
     * 获取路由
     *
     * @param request
     * @return
     * @throws InterruptedException
     */
    @RequestMapping(value = "getRoutes")
    @ResponseBody
    public ResponseMessage getRoutes(HttpServletRequest request) throws InterruptedException {
        log.debug("获取路由");
        List<Menu> menuList = sysMenuService.getRoutes("");
        return new ResponseMessage(ResponseCode.SUCCESS, menuList);
    }


    /**
     * 获取员工列表
     * @param pageParam
     * @return
     */
    @RequestMapping(value = "queryUserList")
    @ResponseBody
    public ResponseMessage queryUserList(@RequestBody Parameter pageParam){
        //        long start = System.currentTimeMillis();
        IPage<SysUser> userMyPage = new Page<SysUser>(pageParam.getPageNum(), pageParam.getPageSize());
        userService.lambdaQuery().orderByAsc(SysUser::getId).page(userMyPage);
        IPage<Map<String, Object>> mapIPage = userService.userService(pageParam);
//        Page page=new Page(pageParam.getPageNum(),pageParam.getPageSize());          //1表示当前页，而10表示每页的显示显示的条目数
//        IPage iPage = userService.selectUserPage(page, "NORMAL");
//        long end = System.currentTimeMillis();
//        System.out.println("总条数 ------> " + userMyPage.getRecords().size());
//        System.out.println("当前页数 ------> " + userMyPage.getCurrent());
//        System.out.println("当前每页显示数 ------> " + userMyPage.getSize());
//        List<Object> list = new ArrayList<>();
//        HashMap<String, Object> hashMap = new HashMap<>();
//        hashMap.put("deptName", "测试一123");
//        hashMap.put("img", null);
//        hashMap.put("createTime", "2019-01-29 17:14:47");
//        hashMap.put("roleId", "25");
//        hashMap.put("mobile", "1233");
//        hashMap.put("realname", "张倩文");
//        hashMap.put("parentName", null);
//        hashMap.put("post", "员工");
//        hashMap.put("userId", 2);
//        hashMap.put("parentId", null);
//        hashMap.put("roleName", "测试角色");
//        hashMap.put("deptId", 36);
//        hashMap.put("email", "54556@qq.com");
//        hashMap.put("username", "15589654126");
//        hashMap.put("status", 1);
//        list.add(hashMap);
        return new ResponseMessage(ResponseCode.SUCCESS, mapIPage);
    }


    /**
     * 角色跟员工关联，员工查询
     * @param pageParam
     * @return
     * @throws InterruptedException
     */
    @RequestMapping(value = "userList")
    @ResponseBody
    public ResponseMessage userList(@RequestBody Parameter pageParam) throws InterruptedException {
        List<SysUser> list = userService.selectList(pageParam);
        List<Object> list1 = new ArrayList<>();
//        HashMap<String, Object> hashMap = new HashMap<>();
//        hashMap.put("deptName","测试一123");
//        hashMap.put("img","http://demo9java.5kcrm.net/api//20190826/Logohong1.png");
//        hashMap.put("createTime","2019-01-29 17:14,47");
//        hashMap.put("roleId","25");
//        hashMap.put("sex",2);
//        hashMap.put("mobile","123456");
//        hashMap.put("realname","张倩倩");
//        hashMap.put("parentName",null);
//        hashMap.put("post","员工");
//        hashMap.put("userId",2);
//        hashMap.put("parentId",null);
//        hashMap.put("roleName","测试角色");
//        hashMap.put("deptId",36);
//        hashMap.put("email","54556@qq.com");
//        hashMap.put("username","15589654126");
//        hashMap.put("status",1);
//        list1.add(hashMap);
        return new ResponseMessage(ResponseCode.SUCCESS, list);
    }
    /**
     * 获取路由
     * @param pageParam
     * @return
     */
    @RequestMapping(value = "dept/queryDeptTree")
    @ResponseBody
    public ResponseMessage queryDeptTree(@RequestBody Parameter pageParam ){
        String deptId = "";
        if(StringUtils.isNotBlank(pageParam.getDeptId())){
            deptId = pageParam.getDeptId();
        }
        List<DepPojo> menuList = departmentService.getAllDep(deptId);


//        List<Object> list = new ArrayList<>();
//        List<Object> children = new ArrayList<>();
//        List<Object> childrechildrenhaslist = new ArrayList<>();
//        HashMap<String, Object> hashMap = new HashMap<>();
//        HashMap<String, Object> childrenhashMap = new HashMap<>();
//        HashMap<String, Object> childrechildrenhashMap = new HashMap<>();
//        hashMap.put("level", "1");
//        hashMap.put("children", children);
//        hashMap.put("name", "办公室");
//        hashMap.put("pid", 0);
//        hashMap.put("id", 1);
//        hashMap.put("label", "办公室");
////        list.add(hashMap);
//        childrenhashMap.put("level", "2");
//        childrenhashMap.put("name", "办公室-1-1-1-11-11-");
//        childrenhashMap.put("pid", 0);
//        childrenhashMap.put("id", 16);
//        childrenhashMap.put("label", "办公室-1-");
//        children.add(childrenhashMap);
//        childrechildrenhashMap.put("level", "2");
//        childrechildrenhashMap.put("name", "办公室-1-1");
//        childrechildrenhashMap.put("pid", 0);
//        childrechildrenhashMap.put("id", 17);
//        childrechildrenhashMap.put("label", "办公室-1-1");
//        childrechildrenhaslist.add(childrechildrenhashMap);
//        childrenhashMap.put("children", childrechildrenhaslist);
        return new ResponseMessage(ResponseCode.SUCCESS, menuList);
    }

    /**
     * 增加部门
     *
     * @param pageParam
     * @return
     */
    @RequestMapping(value = "dept/addDept")
    @ResponseBody
    public ResponseMessage addDept(@RequestBody Parameter pageParam, HttpServletRequest request) {
        Department department = new Department();
        Long userIdByAuthorization = JwtManageTool.getUserIdByAuthorization(request);
        if (null == userIdByAuthorization) {
            return new ResponseMessage(ResponseCode.TOKEN_ERROR);
        }
        //为增加根部门
        department.setName(pageParam.getName());
        department.setLevel(pageParam.getLevel());
        department.setCreateUser(userIdByAuthorization.toString());
        department.setCreateTime(new Date());
        department.setDepType(1);
        department.setStatus(1);
        department.setPid(pageParam.getPid());
        if (pageParam.getPid() != 0) {
            department.setPid(pageParam.getPid());
            department.setDepType(0);
        }

        boolean save = departmentService.save(department);
        if (!save) {
            return new ResponseMessage(ResponseCode.FALSE, "保存失败！");
        }
        // {"deptName":"新的部门3","img":null,"createTime":"2019-03-02 14:04:17","roleId":null,"sex":2,"mobile":"15555555555","realname":"王月蔓","parentName":null,"post":"员工","userId":12,"parentId":0,"roleName":null,"deptId":16,"email":"123@4y.ccc","username":"15555555555","status":0},{"deptName":"新的部门3","img":null,"createTime":"2019-08-15 08:56:59","roleId":"12","sex":1,"mobile":"18224568023","realname":"郭海兵","parentName":"张倩倩","post":"员工","userId":34,"parentId":2,"roleName":"销售员角色","deptId":16,"email":null,"username":"18224568023","status":1}
//        {
//            "code":0,
//                "data":[
//            {
//                "level":1,
//                    "children":Array[2],
//                    "name":"办公室",
//                    "pid":0,
//                    "id":1,
//                    "label":"办公室"
//            },
//            {
//                "level":1,
//                    "name":"人力资源部",
//                    "pid":0,
//                    "id":5,
//                    "label":"人力资源部"
//            },
//            Object{...},
//            Object{...},
//            Object{...}
//    ]
//        }
        return new ResponseMessage(ResponseCode.SUCCESS);
    }


    /**
     * 增加部门
     *
     * @param pageParam
     * @return
     */
    @RequestMapping(value = "dept/updateDept")
    @ResponseBody
    public ResponseMessage updateDept(@RequestBody Parameter pageParam, HttpServletRequest request) {
        Department department = new Department();
        department.setDeptId(Integer.parseInt(pageParam.getDeptId()));
        department.setPid(pageParam.getPid());
        department.setName(pageParam.getName());
        department.setLevel(pageParam.getLevel());
        department.setUpdateTime(new Date());
        department.setStatus(1);
        boolean b = departmentService.updateById(department);
        if(!b){
            return new ResponseMessage(ResponseCode.FALSE,"错误");
        }

        return new ResponseMessage(ResponseCode.SUCCESS);
    }

}
