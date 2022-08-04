package com.bhw.admin.rest;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bhw.admin.mapper.BackMenuMapper;
import com.bhw.admin.mapper.BackRoleMenuMapper;
import com.bhw.admin.mapper.BackUserMapper;
import com.bhw.admin.pojo.BackMenu;
import com.bhw.admin.pojo.BackRole;
import com.bhw.admin.pojo.BackRoleMenu;
import com.bhw.admin.pojo.BackUser;
import com.bhw.admin.vo.PermissionMenus;
import com.bhw.common.base.Page;
import com.bhw.common.base.Response;
import com.bhw.common.multirequestbody.MultiRequestBody;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.*;

@RestController
@RequestMapping("/backUser")
public class BackUserRest {

    @Resource
    private BackUserMapper backUserMapper;
    
    @Resource
    private BackRoleMenuMapper backRoleMenuMapper;

    @Resource
    private BackMenuMapper backMenuMapper;


    @RequestMapping("/qryBackUserList")
    @ResponseBody
    public Response<Object> qryBackUserList(BackUser backUser, Page page){
        Response<Object> response = new Response<>();
        IPage<BackUser> iPage = new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(page.getCurPage(),page.getPageSize());
        IPage<BackUser> selectPage = backUserMapper.selectPage(iPage, new QueryWrapper<>(backUser));
        List<BackUser> records = selectPage.getRecords();
        page.setCounts((int)selectPage.getTotal(),page.getPageSize());
        page.setCurPage((int)selectPage.getCurrent());
        response.setData(records);
        response.setPage(page);
        return response;
    }

    @RequestMapping("/saveBackUser")
    @ResponseBody
    public Response<Object> saveBackUser(BackUser backUser){
        Response<Object> response = new Response<>();
        backUserMapper.insert(backUser);
        return response;
    }

    @RequestMapping("/delBackUser")
    @ResponseBody
    public Response<Object> delBackUser(String idList){
        Response<Object> response = new Response<>();
        backUserMapper.deleteBatchIds(Arrays.asList(idList.split(",")));
        return response;
    }

    @RequestMapping("/upBackUser")
    @ResponseBody
    public Response<Object> upBackUser(BackUser backUser){
        Response<Object> response = new Response<>();
        backUserMapper.updateById(backUser);
        return response;
    }


    @RequestMapping("/backUserLogin")
    @ResponseBody
    public Response<Object> backUserLogin(String account,String password){
        Response<Object> response = new Response<>();
        if(StringUtils.isEmpty(account) || StringUtils.isEmpty(password)){
            response.setRetCode(-1);
            return response;
        }
        BackUser backUser = new BackUser();
        backUser.setAccountName(account);
        backUser.setPassWord(password);
        BackUser backUser1 = backUserMapper.selectOne(new QueryWrapper<BackUser>(backUser));
        if(null == backUser1 || null == backUser1.getId()){
            response.setRetCode(-1);
            return response;
        }
        List<BackRoleMenu> roleMenuByRole = backRoleMenuMapper.getRoleMenuByRole(backUser1.getRoleId());
        Map<String,Object> data = new HashMap<>();
        data.put("userInfo",backUser1);
        data.put("menus",getPermissionMenus(roleMenuByRole));
        response.setData(data);
        return response;
    }

    public List<PermissionMenus> getPermissionMenus(List<BackRoleMenu> roleMenuByRole){
        List<PermissionMenus> permissionMenusList = new ArrayList<>();
        Map<Integer,PermissionMenus> map = new HashMap<>();
        roleMenuByRole.forEach(roleMenu->{
            PermissionMenus permissionMenus = new PermissionMenus();
            String menuUrl = roleMenu.getMenuUrl();
            permissionMenus.setCode(menuUrl);
            permissionMenus.setPath(menuUrl);
            permissionMenus.setHref(menuUrl);
            permissionMenus.setTitle(roleMenu.getMenuName());
            permissionMenus.setLabel(roleMenu.getMenuName());
            permissionMenus.setIcon(roleMenu.getIcon());
            permissionMenus.setIconClass(roleMenu.getIconClass());
            permissionMenus.setId(roleMenu.getMenuId());
            permissionMenus.setParentId(roleMenu.getParentId()==0?-1:roleMenu.getParentId());
            if(roleMenu.getLevels()==1){
                permissionMenusList.add(permissionMenus);
                map.put(roleMenu.getMenuId(),permissionMenus);
            }
            if(roleMenu.getLevels()==2){
                map.put(roleMenu.getMenuId(),permissionMenus);
            }
            PermissionMenus permissionMenus1 = map.get(roleMenu.getParentId());
            if(null == permissionMenus1 && roleMenu.getParentId()>0){
                BackMenu backMenu = backMenuMapper.selectById(roleMenu.getParentId());
                PermissionMenus permissionMenus2 = new PermissionMenus();
                setPermissionMenus(permissionMenus2,backMenu);
                permissionMenus2.setType("false");
                if(backMenu.getLevels()==2){
                    map.put(backMenu.getMenuId(),permissionMenus2);
                    PermissionMenus permissionMenus3 = map.get(permissionMenus2.getParentId());
                    if(null != permissionMenus3){
                        permissionMenus3.getChildren().add(permissionMenus2);
                    }else {
                        BackMenu backMenu1 = backMenuMapper.selectById(permissionMenus2.getParentId());
                        PermissionMenus permissionMenus4 = new PermissionMenus();
                        setPermissionMenus(permissionMenus4,backMenu1);
                        permissionMenus4.setType("false");
                        permissionMenusList.add(permissionMenus4);
                        map.put(backMenu.getMenuId(),permissionMenus4);
                    }
                }
                if(backMenu.getLevels()==1){
                    permissionMenusList.add(permissionMenus2);
                    map.put(backMenu.getMenuId(),permissionMenus2);
                }
                permissionMenus1 = permissionMenus2;
            }
            if(null != permissionMenus1){
                permissionMenus1.getChildren().add(permissionMenus);
            }
        });
        Collections.sort(permissionMenusList, new Comparator<PermissionMenus>() {
            @Override
            public int compare(PermissionMenus o1, PermissionMenus o2) {
                return o1.getId().compareTo(o2.getId());
             }
        });
        return permissionMenusList;
    }

    public void setPermissionMenus(PermissionMenus permissionMenus,BackMenu roleMenu){
        String menuUrl = roleMenu.getMenuUrl();
        permissionMenus.setCode(menuUrl);
        permissionMenus.setPath(menuUrl);
        permissionMenus.setHref(menuUrl);
        permissionMenus.setTitle(roleMenu.getMenuName());
        permissionMenus.setLabel(roleMenu.getMenuName());
        permissionMenus.setIcon(roleMenu.getIcon());
        permissionMenus.setIconClass(roleMenu.getIconClass());
        permissionMenus.setId(roleMenu.getMenuId());
        permissionMenus.setParentId(roleMenu.getParentId()==0?-1:roleMenu.getParentId());
    }

    @RequestMapping("/getBackUserMenu")
    @ResponseBody
    public Response<Object> getBackUserMenu(Integer roleId){
        Response<Object> response = new Response<>();
        List<BackRoleMenu> roleMenuByRole = backRoleMenuMapper.getRoleMenuByRole(roleId);
        response.setData(getPermissionMenus(roleMenuByRole));
        return response;
    }

    @RequestMapping("/getBackMenu")
    @ResponseBody
    public Response<Object> getBackMenu(){
        Response<Object> response = new Response<>();
        List<BackMenu> backMenus = backMenuMapper.selectList(new QueryWrapper<>());
        List<PermissionMenus> permissionMenusList = new ArrayList<>();
        Map<Integer,PermissionMenus> map = new HashMap<>();
        backMenus.forEach(roleMenu->{
            PermissionMenus permissionMenus = new PermissionMenus();
            String menuUrl = roleMenu.getMenuUrl();
            permissionMenus.setCode(menuUrl);
            permissionMenus.setPath(menuUrl);
            permissionMenus.setHref(menuUrl);
            permissionMenus.setTitle(roleMenu.getMenuName());
            permissionMenus.setLabel(roleMenu.getMenuName());
            permissionMenus.setIcon(roleMenu.getIcon());
            permissionMenus.setIconClass(roleMenu.getIconClass());
            permissionMenus.setId(roleMenu.getMenuId());
            permissionMenus.setParentId(roleMenu.getParentId()==0?-1:roleMenu.getParentId());
            if(roleMenu.getLevels()==1){
                permissionMenusList.add(permissionMenus);
                map.put(roleMenu.getMenuId(),permissionMenus);
            }
            if(roleMenu.getLevels()==2){
                map.put(roleMenu.getMenuId(),permissionMenus);
            }
            PermissionMenus permissionMenus1 = map.get(roleMenu.getParentId());
            if(null != permissionMenus1){
                permissionMenus1.getChildren().add(permissionMenus);
            }
        });
        response.setData(permissionMenusList);
        return response;
    }

}
