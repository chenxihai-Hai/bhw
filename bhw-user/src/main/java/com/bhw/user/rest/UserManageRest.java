package com.bhw.user.rest;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bhw.common.base.Page;
import com.bhw.common.base.Response;
import com.bhw.common.multirequestbody.MultiRequestBody;
import com.bhw.user.dto.EditUserReq;
import com.bhw.user.dto.UserListReq;
import com.bhw.user.mapper.BhwUserMapper;
import com.bhw.user.mapper.CompanyCardMapper;
import com.bhw.user.vo.AuditUserListVo;
import com.bhw.user.vo.UserListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RefreshScope
@RequestMapping("/userManage")
public class UserManageRest {
    @Autowired
    private BhwUserMapper bhwUserMapper;

    @Autowired
    private CompanyCardMapper companyCardMapper;
    /**
     * 用户列表
     * @return
     */
    @RequestMapping(value = "/getUserList", method = RequestMethod.GET)
    public Response<Object> getUserList(UserListReq req, Page page){

        Response<Object> response = new Response<>();
        IPage<UserListVo> iPage=bhwUserMapper.selectUserList(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<UserListVo>(page.getCurPage(),page.getPageSize()),req);
        List<UserListVo> list = iPage.getRecords();
        response.setData(list);
        page.setCounts((int)iPage.getTotal(),page.getPageSize());
        page.setCurPage((int)iPage.getCurrent());
        response.setPage(page);
        return response;
    }

    /**
     *
     * @param idList "两个id之间逗号隔开"
     * @param type 1个人，2企业
     * @return
     */
    @RequestMapping(value = "/deleteUserList", method = RequestMethod.POST)
    public Response<Object> deleteUserList(String idList,String type){
        Response<Object> response = new Response<>();
        List<Integer> useridList=new ArrayList<>();
        if(idList.contains(",")){
            String[] idListArray=idList.split(",");
            for(String item:idListArray){
                useridList.add(Integer.parseInt(item));
            }
        }else{
            useridList.add(Integer.parseInt(idList));
        }
        boolean flag=bhwUserMapper.deleteById(useridList);
        if("2".equals(type)){//如果为企业，清除三证合一相关证件
            flag=companyCardMapper.deleteByUserId(useridList);
        }
        response.setData(flag);
        return response;
    }

    @RequestMapping(value = "/editUser", method = RequestMethod.POST)
    public Response<Object> editUser(EditUserReq req){
        Response<Object> response = new Response<>();
        response.setData(bhwUserMapper.updateUserList(req));
        return response;
    }

    /**
     *
     * @param type 1个人，2企业
     * @return
     */
    @RequestMapping(value = "/getAuditUserList", method = RequestMethod.GET)
    public Response<Object> getAuditUserList(String type,Page page){
        Response<Object> response = new Response<>();
        IPage<AuditUserListVo> iPage=bhwUserMapper.selectAuditUserList(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<UserListVo>(page.getCurPage(),page.getPageSize()),type);
        List<AuditUserListVo> list = iPage.getRecords();
        response.setData(list);
        page.setCounts((int)iPage.getTotal(),page.getPageSize());
        page.setCurPage((int)iPage.getCurrent());
        response.setPage(page);
        return response;
    }

    /**
     *
     * @param type 1通过，2驳回
     * @param idList  "两个id之间逗号隔开"
     * @return
     */
    @RequestMapping(value = "/infoAudit",method = RequestMethod.POST)
    @ResponseBody
    public Response<Object> infoAudit( Integer type, String idList){
        Response<Object> response = new Response<>();
        List<Integer> useridList=new ArrayList<>();
        if(idList.contains(",")){
            String[] idListArray=idList.split(",");
            for(String item:idListArray){
                useridList.add(Integer.parseInt(item));
            }
        }else{
            useridList.add(Integer.parseInt(idList));
        }
        response.setData(bhwUserMapper.updateAuditStstus(type,useridList));
        return response;
    }
}
