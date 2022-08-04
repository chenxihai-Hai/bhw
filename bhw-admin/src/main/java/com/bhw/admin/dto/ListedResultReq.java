package com.bhw.admin.dto;

import lombok.Data;

@Data
public class ListedResultReq {
    private Integer status;//挂牌状态（0正在挂牌，1已结束）
    private Integer  closeStatus;//成交状态（0成功，1未成交）
}
