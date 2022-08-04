package com.bhw.admin.rest;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bhw.admin.mapper.BackRoleMapper;
import com.bhw.admin.mapper.BackRoleMenuMapper;
import com.bhw.admin.pojo.BackRole;
import com.bhw.admin.pojo.BackRoleMenu;
import com.bhw.admin.service.BackRoleMenuService;
import com.bhw.common.base.Response;
import com.bhw.common.multirequestbody.MultiRequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.*;

@RestController
@RequestMapping("/backRole")
public class BackRoleRest {

    @Resource
    private BackRoleMapper backRoleMapper;

    @Resource
    private BackRoleMenuService backRoleMenuService;

    @Resource
    private BackRoleMenuMapper backRoleMenuMapper;

    @RequestMapping("/qryBackRoleList")
    @ResponseBody
    public Response<Object> qryBackRoleList(BackRole backRole){
        Response<Object> response = new Response<>();
        List<BackRole> backRoles = backRoleMapper.selectList(new QueryWrapper<>(backRole));
        response.setData(backRoles);
        return response;
    }

    @RequestMapping("/saveBackRole")
    @ResponseBody
    public Response<Object> saveBackRole(BackRole backRole){
        Response<Object> response = new Response<>();
        backRoleMapper.insert(backRole);
        return response;
    }

    @RequestMapping("/delBackRole")
    @ResponseBody
    public Response<Object> delBackRole(String idList){
        Response<Object> response = new Response<>();
        backRoleMapper.deleteBatchIds(Arrays.asList(idList.split(",")));
        return response;
    }

    @RequestMapping("/upBackRole")
    @ResponseBody
    public Response<Object> upBackRole(BackRole backRole){
        Response<Object> response = new Response<>();
        backRoleMapper.updateById(backRole);
        return response;
    }

    @RequestMapping("/qryBackRoleParams")      //查询角色参数
    @ResponseBody
    public Response<Object> qryBackRoleParams(BackRole backRole){
        Response<Object> response = new Response<>();
        List<BackRole> backRoles = backRoleMapper.selectList(new QueryWrapper<>(backRole));
        Map<Integer,String> map = new HashMap<>();
        backRoles.forEach(item->{
            map.put(item.getId(),item.getRoleName());
        });
        response.setData(map);
        return response;
    }

    @RequestMapping("/saveRoleMenus")
    @ResponseBody
    public Response<Object> saveRoleMenus(String idList,Integer roleId){
        Response<Object> response = new Response<>();
        BackRoleMenu backRoleMenu = new BackRoleMenu();
        backRoleMenu.setRoleId(roleId);
        backRoleMenuMapper.delete(new QueryWrapper<>(backRoleMenu));
        List<BackRoleMenu> backRoleMenus = new ArrayList<>();
        String[] split = idList.split(",");
        for (String s : split) {
            BackRoleMenu backRoleMenu1 = new BackRoleMenu();
            backRoleMenu1.setRoleId(roleId);
            backRoleMenu1.setMenuId(Integer.valueOf(s));
            backRoleMenus.add(backRoleMenu1);
        }
        backRoleMenuService.saveBatch(backRoleMenus);
        return response;
    }
}
