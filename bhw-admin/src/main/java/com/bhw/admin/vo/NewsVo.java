package com.bhw.admin.vo;

import lombok.Data;

@Data
public class NewsVo {
    private Integer id;
    private String subject;
    private String createtime;
    private String uploadTime;
    private String img;
}
