package com.reserve.controller;

import com.reserve.mapper.SesMapper;
import com.reserve.pojo.Ses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
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
        ses.setStartTime(map.get("startTime"));
        ses.setEndTime(map.get("endTime"));
        ses.setPosition_type(Integer.valueOf(map.get("position_type")));
        ses.setLimit(Integer.valueOf(map.get("limit")));
        sesMapper.addSes(ses);
        m.put("msg", "success");
        return new ResponseEntity<>(m, HttpStatus.OK);
    }

    @DeleteMapping("/api/ses")
    public ResponseEntity<Map<String, String>> delSes(@RequestBody Object sesID) {
        Map<String, String> m = new HashMap<>();
        sesMapper.deleteSes((Integer) sesID);
        m.put("msg", "success");
        return new ResponseEntity<>(m, HttpStatus.OK);
    }

    @GetMapping("/api/ses")
    public ResponseEntity<List<Ses>> getSes(@RequestBody(required = false) Map<String, String> map) {
        List<Ses> sesList = new ArrayList<>();
        if (map==null){
            sesList.addAll(sesMapper.getAll());
            return new ResponseEntity<>(sesList, HttpStatus.OK);
        }
        String sesID = map.get("sesID");
        String fromTime = map.get("fromTime");
        if (sesID != null) {
            sesList.add(sesMapper.getSes(Integer.parseInt(sesID)));
            return new ResponseEntity<>(sesList, HttpStatus.OK);
        }
        if (fromTime != null) {
            sesList.addAll(sesMapper.getSome(Date.valueOf(fromTime)));
            return new ResponseEntity<>(sesList, HttpStatus.OK);
        }
        return new ResponseEntity<>(sesList, HttpStatus.OK);
    }
}
