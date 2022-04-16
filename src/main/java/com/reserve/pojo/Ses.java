package com.reserve.pojo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Ses {
    private Integer id;
    private String date;
    private String startTime;
    private String endTime;
    private Integer position_type;
    private Integer limit;
    private Integer num;
}
