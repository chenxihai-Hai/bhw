package com.bhw.admin.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PermissionMenus {

    private String code;

    private String path;

    private String href;

    private String icon;

    private String iconClass;

    private Integer id;

    private Integer parentId;

    private String title;

    private String label;

    private String type="dirt";

    private List<PermissionMenus> children = new ArrayList<>();
}
