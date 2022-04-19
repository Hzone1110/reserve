package com.reserve.controller;

import com.reserve.mapper.InfoMapper;
import com.reserve.pojo.Info;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class InfoController {
    private final InfoMapper infoMapper;

    public InfoController(InfoMapper infoMapper) {
        this.infoMapper = infoMapper;
    }

    @PostMapping("/api/rsv")
    public ResponseEntity<Map<String, String>> addInfo(@RequestBody Map<String, String> map) {
        int sesID = Integer.parseInt(map.get("sesID"));
        int position_type = Integer.parseInt(map.get("position_type"));
        int userID = Integer.parseInt(map.get("userID "));
        Map<String, String> m = new HashMap<>();
        String model = map.get("model");
        Info info = Info.builder().build();
        info.setPosition_type(position_type);
        info.setModel(model);
        info.setDate(Date.valueOf(LocalDate.now()));
        infoMapper.addInfo(info);
        m.put("msg", "success");
        return new ResponseEntity<>(m, HttpStatus.OK);
    }

    @DeleteMapping("/api/rsv")
    public ResponseEntity<Map<String, String>> delInfo(@RequestBody Map<String, String> map) {
        Map<String, String> m = new HashMap<>();
        int rsvID = Integer.parseInt(map.get("rsvID"));
        infoMapper.deleteInfo(rsvID);
        m.put("msg", "success");
        return new ResponseEntity<>(m, HttpStatus.OK);
    }

    @GetMapping("/api/rsv")
    public ResponseEntity<List<Info>> getInfo(@RequestBody Map<String, String> map) {
        int sesID = Integer.parseInt(map.get("sesID"));
        String email = map.get("email");
        List<Info> infoList = new ArrayList<>();
        if (sesID != 0) {
            infoList.addAll(infoMapper.getInfoBySes(sesID));
        }
        if (email != null) {
            infoList.addAll(infoMapper.getInfoByEmail(email));
        }
        return new ResponseEntity<>(infoList, HttpStatus.OK);
    }
}
