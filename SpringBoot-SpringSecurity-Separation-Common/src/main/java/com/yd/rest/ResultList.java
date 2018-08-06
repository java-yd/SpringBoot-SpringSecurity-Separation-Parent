package com.yd.rest;

import java.util.List;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.yd.constant.StatusCodeEnum;

/**
 * Created by fred on 2017/12/5.
 */
public class ResultList<T> extends AbstractResult {
    private List<T> dataList; //列表数据
    private Integer totalPage; //总页数
    private Integer totalNumber;//总条数
    private Integer pageIndex; //当前页
    private Integer pageSize;  //当前页条数

    public static <T> ResultList<T> error() {
        return new ResultList(StatusCodeEnum.SYS_ERROR);
    }

    @SuppressWarnings(value = "unchecked")
    public static <T> ResultList<T> error(StatusCodeEnum statusCode) {
        return new ResultList(statusCode);
    }
    
    @SuppressWarnings(value = "unchecked")
    public static <T> ResultList<T> list(PageInfo<T> datas) {
    	ResultList<T> res =  new ResultList(StatusCodeEnum.SUCCESS);
        res.dataList = datas.getList();
        res.totalPage = datas.getPages();
        res.totalNumber = (int)datas.getTotal();
        res.pageIndex = datas.getPageNum();
        res.pageSize = datas.getSize();
        return res;
    }

    public ResultList(StatusCodeEnum statusCode) {
        super(statusCode);
    }

    public ResultList(String code, String message) {
        super(code, message);
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Integer getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(Integer totalNumber) {
        this.totalNumber = totalNumber;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
