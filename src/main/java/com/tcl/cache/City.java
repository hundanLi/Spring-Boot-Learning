package com.tcl.cache;

import lombok.Data;

import java.io.Serializable;

/**
 * @author li
 * @version 1.0
 * @date 2020/8/6 17:47
 */
@Data
public class City implements Serializable {
    private Integer id;
    private String cityName;
    private String population;
    private String province;
}
