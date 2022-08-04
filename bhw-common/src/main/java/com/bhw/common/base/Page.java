package com.bhw.common.base;

import org.apache.commons.lang.StringUtils;

import java.io.Serializable;

public class Page implements Serializable {


    // 记录总数
    private int counts = -1;

    // 每页显示记录数
    private int pageSize = 10;

    // 总页数
    private int totalPage = 1;

    // 当前页
    private int curPage = 1;

    // 页面显示开始记录数
    private int firstResult = 1;

    // 页面显示最后记录数
    private int lastResult = 1;

    /*
     * 排序名称
     */
    private String orderName;

    /*
     * 排序类型
     */
    private String orderType;

    private String orderBy;

    public Page(int counts, int pageSize) {
        // 计算所有的页面数
        this.counts = counts;
        if (counts % pageSize == 0) {
            this.totalPage = this.counts / this.pageSize;
        } else {
            this.totalPage = this.counts / this.pageSize + 1;
        }
    }

    public Page() {
    }


    public int getCounts() {
        return counts;
    }


    public void setCounts(int counts, int pageSize) {
        this.pageSize = pageSize;
        // 计算所有的页面数
        this.counts = counts;
        if (counts % this.pageSize == 0) {
            this.totalPage = this.counts / this.pageSize;
        } else {
            this.totalPage = this.counts / this.pageSize + 1;
        }
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }


    public int getTotalPage() {
        if (totalPage < 1) {
            return 1;
        }
        return totalPage;
    }


    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }


    public int getFirstResult() {
        int temp = this.curPage - 1;
        if (temp <= 0) {
            return 0;
        }
        return this.firstResult = (this.curPage - 1) * this.pageSize;
    }


    public void setFirstResult(int firstResult) {
        this.firstResult = firstResult;
    }

    public int getLastResult() {
        this.lastResult = this.firstResult + this.pageSize;
        return lastResult;
    }

    public void setLastResult(int lastResult) {
        this.lastResult = lastResult;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderBy() {
        if (StringUtils.isNotBlank(orderBy)) {
            return orderBy;
        }
        if (this.getOrderName() == null || this.getOrderName().equals("")
                || this.getOrderType() == null
                || this.getOrderType().equals("")) {
            return "";
        }
        orderBy = " order by " + this.getOrderName() + " "
                + this.getOrderType();
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public void setCounts(int counts) {
        this.counts = counts;
    }

}
