package com.bhw.thirdparty.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class DataVo implements Serializable {

    private String title;

    private String avatar;

    private String summary;

    private String url;
}
