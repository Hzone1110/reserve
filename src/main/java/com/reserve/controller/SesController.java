package com.reserve.controller;

import com.reserve.mapper.SesMapper;
import com.reserve.pojo.Ses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.util.Map;

@RestController
@CrossOrigin
public class SesController {
    private final SesMapper sesMapper;

    public SesController(SesMapper sesMapper) {
        this.sesMapper = sesMapper;
    }

    @PostMapping("/api/ses")
    public ResponseEntity<String> addSes(@RequestBody Map<String, String> map) {
        Ses ses = Ses.builder().build();
        ses.setDate(Date.valueOf(map.get("date")));
        ses.setStartTime(map.get("startTime"));
        ses.setEndTime(map.get("endTime"));
        ses.setPosition_type(Integer.valueOf(map.get("position")));
        ses.setLimit(Integer.valueOf(map.get("limit")));
        sesMapper.addSes(ses);
        return new ResponseEntity<>("新增成功", HttpStatus.OK);
    }
}
