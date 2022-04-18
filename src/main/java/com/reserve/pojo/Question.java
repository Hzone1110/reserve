package com.reserve.pojo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Question {
    private Integer id;
    private String description;
}
