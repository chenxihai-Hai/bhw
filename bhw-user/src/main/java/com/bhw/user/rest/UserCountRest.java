package com.bhw.user.rest;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bhw.common.base.Response;
import com.bhw.common.util.DateUtil;
import com.bhw.user.mapper.BhwUserMapper;
import com.bhw.user.mapper.UserMemberMapper;
import com.bhw.user.pojo.UserMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RefreshScope
@RequestMapping("/userCount")
public class UserCountRest {
    @Resource
    private BhwUserMapper bhwUserMapper;

    @Resource
    UserMemberMapper userMemberMapper;

    /**
     *
     * @return
     */
    @RequestMapping(value = "/getCount", method = RequestMethod.GET)
    public Response<Object> getCount(){
        Response<Object> response = new Response<>();
        Map<String,Object> result=new HashMap<>();
        result.put("MerchantUser",bhwUserMapper.selectCountByMember(0));//商家用户数
        result.put("all",bhwUserMapper.selectCountByMember(1));//所有用户数
        UserMember userMember = new UserMember();
        userMember.setStatus(1);
        userMember.setType(1);
        Wrapper<UserMember> query = new QueryWrapper<>(userMember);
        Integer integer = userMemberMapper.selectCount(query);
        userMember.setType(2);
        query = new QueryWrapper<>(userMember);
        Integer integer2 = userMemberMapper.selectCount(query);
        result.put("standardMembet",integer);//标准会员用户数
        result.put("superMember",integer2);//超级会员用户数
        response.setData(result);
        return response;
    }

    /**
     *
     * @param member 会员类型（2标准会员，3超级会员）
     * @param merchantFlag 1商家用户，2个人用户
     * @return
     */
    @RequestMapping(value = "/getAddUser", method = RequestMethod.GET)
    public Response<Object> getAddUser(Integer member,Integer merchantFlag){
        Response<Object> response = new Response<>();
        String startDate=DateUtil.getDateAfter(-6);
        String endDate = DateUtil.getToday("yyyy-MM-dd");
        if(null != member){
            UserMember userMember = new UserMember();
            userMember.setStatus(1);
            userMember.setType(member);
            Wrapper<UserMember> query = new QueryWrapper<>(userMember);
            ((QueryWrapper<UserMember>) query).between("updateTime",startDate,endDate);
            List<UserMember> userMembers = userMemberMapper.selectList(query);
            Map<String,Integer> objectMap=new HashMap<>();
            userMembers.forEach(um->{
                String default_date_format = DateUtil.formatDate(um.getUpdateTime(), "MM-dd");
                Integer integer = objectMap.get(default_date_format);
                if(null == integer){
                    objectMap.put(default_date_format,1);
                }else{
                    objectMap.put(default_date_format,integer++);
                }
            });
            response.setData(objectMap);
            return response;
        }
        List<Map> maps = bhwUserMapper.selectWeekAdd(startDate,endDate,member,merchantFlag);
        Map<Object,Object> objectMap=new HashMap<>();
        for(Map<String,Object> map:maps){
            Object mapkey=null;
            Object value=null;
            for(String key : map.keySet()){
                if("date".equals(key)){
                    mapkey=map.get(key);
                }else if("count".equals(key)){
                    value=map.get(key);
                }
                if(mapkey!=null && value!=null ){
                    objectMap.put(mapkey,value);
                }
            }
        }
        response.setData(objectMap);
        return response;
    }
}
