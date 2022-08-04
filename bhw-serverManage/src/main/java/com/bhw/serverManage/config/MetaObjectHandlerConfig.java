package com.bhw.serverManage.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.bhw.common.util.DateUtil;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MetaObjectHandlerConfig implements MetaObjectHandler {
    public void insertFill(MetaObject metaObject) {

        Object createTime = getFieldValByName("createTime", metaObject);
        Object updateTime = getFieldValByName("updateTime", metaObject);
        if (createTime == null)
            setFieldValByName("createTime", new Date(), metaObject);
        if (updateTime == null)
            setFieldValByName("updateTime",new Date(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Object updateTime = getFieldValByName("updateTime", metaObject);
        if (updateTime == null) {
            setFieldValByName("updateTime", new Date(), metaObject);
        }
    }
}