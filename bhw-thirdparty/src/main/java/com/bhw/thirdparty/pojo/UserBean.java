package com.bhw.thirdparty.pojo;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "T_USER")
@Data
public class UserBean implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    /**
     * 用户名称
     * */
    private String userName;

    /**
     * 头像
     * */
    private String photo;


    private String phone;

    private Integer type;

    private Integer dutyId;

    /**
     * 职务
     * */
    @Transient
    private String duTyName;

    private Integer professionId;

    /**
     * 所属行业
     * */
    @Transient
    private String professionName;

    private Integer brokerId;

    /**
     * 经纪人所属行业
     * */
    @Transient
    private String brokerName;

    private String remark;

    @CreatedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String createTime;


    @Transient
    private String title;

    @Transient
    private String avatar;

    @Transient
    private String summary;

    public UserBean() {
    }

    public UserBean(String userName, String photo, String duTyName, String professionName, String brokerName) {
        this.userName = userName;
        this.photo = photo;
        this.duTyName = duTyName;
        this.professionName = professionName;
        this.brokerName = brokerName;
    }

    public UserBean(String title,String avatar,String summary){
        this.title = title;
        this.avatar = avatar;
        this.summary = summary;
    }
}
