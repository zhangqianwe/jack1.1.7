package com.jack.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jack.jackOnline.Department;
import com.jack.mapper.DepartmentMapper;
import com.jack.pojo.DepPojo;
import com.jack.service.DepartmentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Auther: zhangqianwen
 * @Date: 2020/6/23 09:58
 * getParentNode 获取父节点
 * @Description:
 */
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements DepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;

    @Override
    public List<DepPojo> getAllDep(String dep) {
        QueryWrapper<Department> departmentQueryWrapper = new QueryWrapper<>();
        departmentQueryWrapper.eq("status", 1);
//        if(StringUtils.isNotBlank(dep)){
//            departmentQueryWrapper.eq("dept_Id",Integer.parseInt(dep));
//        }
        //拿到所有部门
        List<Department> allDep = departmentMapper.selectList(departmentQueryWrapper);
        //1先拿到所有的父节点
        List<DepPojo> parentlist = getParentNode(allDep, 1,dep);
        //父节点进行排序
        Collections.sort(parentlist);
        //2拿到子节点信息
//        List<Department> childlist = allDep.stream().filter(Department -> Department.getDepType() == 0).collect(Collectors.toList());
        QueryWrapper<Department> vQueryWrapper = new QueryWrapper<>();
        departmentQueryWrapper.eq("status", 1);
        departmentQueryWrapper.eq("dep_Type", 0);

        //拿到所有部门
//        List<Department> childlist = departmentMapper.selectList(vQueryWrapper);
//        QueryWrapper<Department> departmentQuery = new QueryWrapper<>();
//        departmentQuery.eq("status", 1);
//        List<Department> childlistDep = departmentMapper.selectList(departmentQueryWrapper);
        List<DepPojo> childlist = getParentNode(allDep, 0,"");
        System.out.println("父节点信息" + parentlist.toString());
        System.out.println("子节点信息" + childlist.toString());
        getChildNode(parentlist, childlist);

        return parentlist;
    }

    private void getChildNode(List<DepPojo> parentlist, List<DepPojo> childlist) {
        parentlist.forEach(parent ->{
            if(childlist.size()==0){return;}
            List<DepPojo> collect = childlist.stream().filter(Department ->
                    Department.getPid().longValue() == parent.getId().longValue()
            ).collect(Collectors.toList());
            if(collect.size()>0){
                List<DepPojo> list = new ArrayList<>();
                collect.stream().forEach(e->{
                    DepPojo depPojo = new DepPojo(e.getId(),e.getId(),e.getName(),e.getPid(),e.getName(),e.getLevel());
                    list.add(depPojo);
                });
                Collections.sort(list);
                parent.setChildren(list);
                getChildNode(parent.getChildren(), childlist);
            }

        });

    }

    /**
     * 获取父节点
     * @param allDep  所有部门
     * @param depType 1：父节点；0：叶子节点'
     * @param dep
     * @return
     */
    private List<DepPojo> getParentNode(List<Department> allDep, int depType, String dep) {
        List<DepPojo> menuList = new ArrayList<>();
        if(StringUtils.isEmpty(dep)){

            List<Department> collect = allDep.stream().filter(Department -> Department.getDepType() == depType ).collect(Collectors.toList());
            collect.forEach(e->{
                DepPojo depPojo = new DepPojo(e.getDeptId(),e.getDeptId(),e.getName(),e.getPid(),e.getName(),e.getLevel());
                menuList.add(depPojo);
            });
        }else{
            List<Department> collect = allDep.stream().filter(Department -> Department.getDeptId() == Integer.parseInt(dep)).collect(Collectors.toList());
            collect.forEach(e->{
                DepPojo depPojo = new DepPojo(e.getDeptId(),e.getDeptId(),e.getName(),e.getPid(),e.getName(),e.getLevel());
                menuList.add(depPojo);
            });
        }

        return menuList;
    }
}
