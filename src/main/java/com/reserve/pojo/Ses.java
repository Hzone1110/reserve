package com.reserve.pojo;

import lombok.Builder;
import lombok.Data;

import java.sql.Date;
import java.sql.Time;

@Data
@Builder
public class Ses {
    private Integer sesID;
    private Date date;
    private Time startTime;
    private Time endTime;
    private Integer position;
    private Integer limit;
    private Integer num;
}
