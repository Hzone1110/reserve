package com.reserve.controller;

import com.reserve.mapper.SesMapper;
import com.reserve.pojo.Ses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class SesController {
    private final SesMapper sesMapper;


    public SesController(SesMapper sesMapper) {
        this.sesMapper = sesMapper;
    }

    @PostMapping("/api/ses")
    public ResponseEntity<Map<String, String>> addSes(@RequestBody Map<String, String> map) {
        Map<String, String> m = new HashMap<>();
        Ses ses = Ses.builder().build();
        ses.setDate(Date.valueOf(map.get("date")));
        ses.setStartTime(Time.valueOf(map.get("startTime")));
        ses.setEndTime(Time.valueOf(map.get("endTime")));
        ses.setPosition(Integer.valueOf(map.get("position")));
        ses.setLimit(Integer.valueOf(map.get("limit")));
        sesMapper.addSes(ses);
        m.put("msg", "success");
        return new ResponseEntity<>(m, HttpStatus.OK);
    }

    @DeleteMapping("/api/ses")
    public ResponseEntity<Map<String, String>> delSes(@RequestBody Map<String, String> map) {
        Map<String, String> m = new HashMap<>();
        sesMapper.deleteInfo(Integer.parseInt(map.get("sesID")));
        sesMapper.deleteSes(Integer.parseInt(map.get("sesID")));
        m.put("msg", "success");
        return new ResponseEntity<>(m, HttpStatus.OK);
    }

    @GetMapping("/api/ses")
    public ResponseEntity<List<Ses>> getSes(@RequestParam(value = "sesID", required = false) String sesID,
                                            @RequestParam(value = "fromTime", required = false) String fromTime) {
        List<Ses> sesList = new ArrayList<>();
        if (sesID == null && fromTime == null) {
            sesList.addAll(sesMapper.getAll());
            return new ResponseEntity<>(sesList, HttpStatus.OK);
        }
        if (sesID != null) {
            sesList.add(sesMapper.getSes(Integer.parseInt(sesID)));
            return new ResponseEntity<>(sesList, HttpStatus.OK);
        }
        sesList.addAll(sesMapper.getSome(Date.valueOf(fromTime)));
        return new ResponseEntity<>(sesList, HttpStatus.OK);
    }
}
