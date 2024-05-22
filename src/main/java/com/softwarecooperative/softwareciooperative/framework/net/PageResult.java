package com.softwarecooperative.softwareciooperative.framework.net;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 封装分页查询结果
 */
@Data
@AllArgsConstructor
public class PageResult<T> implements Serializable {

    private long total; //总记录数

    private List<T> records; //当前页数据集合

}
