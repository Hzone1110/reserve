package com.reserve.pojo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Admin {
    private String account;
    private String password;
}
