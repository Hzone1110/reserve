package com.reserve.pojo;

import lombok.Builder;
import lombok.Data;

import java.sql.Date;

@Data
@Builder
public class Info {
    private Integer id;
    private int position;
    private Date date;
    private String model;
    private Integer status;
}
