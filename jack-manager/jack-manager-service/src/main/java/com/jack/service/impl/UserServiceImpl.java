package com.jack.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jack.api.PageParam;
import com.jack.api.ResponseCode;
import com.jack.api.ResponseMessage;
import com.jack.common.jwt.JwtManageTool;
import com.jack.customPojo.Parameter;
import com.jack.customPojo.UserPojo;
import com.jack.jackOnline.Department;
import com.jack.jackOnline.SysUser;
import com.jack.jackOnline.SysUserRole;
import com.jack.mapper.UserMapper;
import com.jack.pojo.DepPojo;
import com.jack.pojo.User;
import com.jack.service.DepartmentService;
import com.jack.service.SysUserRoleService;
import com.jack.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Auther: zhangqianwen
 * @Date: 2020/6/15 15:13
 * @Description:
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, SysUser> implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private SysUserRoleService sysUserRoleService;


    @Override
    public User getById2(int i) {
//        IPage<User> page = new Page<>(1, 20);
//        userMapper.lambdaQuery()
//                // todo 填充 wrapper 的全部条件
//                .orderByDesc(OrderProductBo::getId)
//                .page(page);
        return new User();
    }

    @Override
    public IPage<Map<String, Object>> userService(Parameter pageParam) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();


        if (StringUtils.isNotBlank(pageParam.getStatus())) {
            queryWrapper.eq("status", pageParam.getStatus());
        }

        if (StringUtils.isNotBlank(pageParam.getDeptId())) {
            //拿到此部门一下所有部门id
            List<DepPojo> allDep = departmentService.getAllDep(pageParam.getDeptId());
            List<Integer> deptzId = new ArrayList<>();
            List<Integer> listID = getListID(allDep, deptzId);
            System.out.println("查询： "+listID.toString());

            queryWrapper.in("dept_Id", getListID(allDep,deptzId));
        }
        if(StringUtils.isNotBlank(pageParam.getRealname())){
            queryWrapper.like("name",pageParam.getRealname());
        }
        IPage<Map<String, Object>> mapIPage = userMapper.selectMapsPage(new Page<>(pageParam.getPageNum(), pageParam.getPageSize()), queryWrapper);
        List<Map<String, Object>> records = mapIPage.getRecords();
        records.forEach(e -> {
            QueryWrapper<Department> objectQueryWrapper = new QueryWrapper<>();
            objectQueryWrapper.eq("dept_id", e.get("dept_id"));

            Department dep = departmentService.getOne(objectQueryWrapper,
                    true);
            e.put("deptName", dep.getName());

            QueryWrapper<SysUserRole> sysUserRoleWrapper = new QueryWrapper<>();
            sysUserRoleWrapper.eq("status",1);
            sysUserRoleWrapper.eq("user_id",e.get("ID"));
            List<SysUserRole> list = sysUserRoleService.list(sysUserRoleWrapper);
            List<String> roleList = new ArrayList<>();
            roleList.add("系统管理员,");
            roleList.add("业务人员,");
            roleList.add("业务人员1");
            e.put("roleName", roleList);
        });
        return mapIPage;
    }

    private DepPojo getChild(List<DepPojo> allDep, String deptId) {
        DepPojo depPojo1 = new DepPojo();
        for (DepPojo depPojo : allDep) {
            if (depPojo.getId() == Integer.parseInt(deptId)) {
                //找到选中的部门树
//                List<Integer> integers = new ArrayList<>();
//                integers = getListID(depPojo,integers);
                depPojo1= depPojo;
                break;
            } else {
                if (depPojo.getChildren().size() > 0) {
                     getChild(depPojo.getChildren(), deptId);
                }
                continue;
            }
        }

        return depPojo1;
    }

    private List<Integer> getListID(List<DepPojo> depPojo, List<Integer> integers) {
        for (DepPojo pojo : depPojo) {
            integers.add(pojo.getId());
            if (pojo.getChildren().size()>0){
                getListID(pojo.getChildren(),integers);
//                for (DepPojo child : pojo.getChildren()) {
//                    integers.add(child.getId());
//
//                }
            }
        }
        return integers;
    }


    @Override
    public IPage selectUserPage(Page page, String normal) {
        IPage iPage = userMapper.selectPage(page, new QueryWrapper<>());
        return iPage;
    }

    @Override
    public ResponseMessage selectByUserNameAndPassWord(String userName, String passWord) throws Exception {
        HashMap<String, Object> hashMap = new HashMap<>();
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("login_name", userName)
                .eq("password", passWord);
//                .eq("status", 1);
        SysUser user = userMapper.selectOne(queryWrapper);
        if (null == user) {
            return new ResponseMessage(ResponseCode.PASSWORD_AND_USERNAME_FAIL);
        }
        if(user.getStatus()==2){
            return new ResponseMessage(ResponseCode.USER_NOT_ACTIVE);
        }

        String token = JwtManageTool.createToken(user.getId().longValue(), user.getRealname(), user.getTelphone());
        UserPojo userPojo = new UserPojo();
        userPojo.setName(user.getRealname());
        userPojo.setMobile(user.getTelphone());
        userPojo.setOrgCode(user.getRoleCode());
        hashMap.put("token", token);
        hashMap.put("userInfor", userPojo);
        return new ResponseMessage(ResponseCode.SUCCESS,hashMap);
    }

    @Override
    public List<SysUser> selectList(Parameter pageParam) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper();
        queryWrapper.eq("status",1);
        return userMapper.selectList(queryWrapper);
    }

    public static String getMD5String(String str) {
        try {
            // 生成一个MD5加密计算摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 计算md5函数
            md.update(str.getBytes());
            // digest()最后确定返回md5 hash值，返回值为8位字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
            // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
            //一个byte是八位二进制，也就是2位十六进制字符（2的8次方等于16的2次方）
            return new BigInteger(1, md.digest()).toString(16);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        String md5String = getMD5String("111111");
        System.out.println(md5String);
//        54b53072540eeeb8f8e9343e71f28176
    }



}
