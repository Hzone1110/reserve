package com.reserve.pojo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Info {
    private Integer id;
    private int position_type;
    private String date;
    private String model;
    private Integer status;
}
