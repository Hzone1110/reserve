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
public class InfoController {
    private final InfoMapper infoMapper;

    public InfoController(InfoMapper infoMapper) {
        this.infoMapper = infoMapper;
    }

    @PostMapping("/api/rsv")
    public ResponseEntity<Map<String, String>> addInfo(@RequestBody Map<String, String> map) {
        int sesID = Integer.parseInt(map.get("sesID"));
        int position = Integer.parseInt(map.get("position"));
        int userID = Integer.parseInt(map.get("userID"));
        String question = map.get("question");
        Map<String, String> m = new HashMap<>();
        String model = map.get("model");
        Info info = Info.builder().build();
        info.setPosition(position);
        info.setModel(model);
        info.setDate(Date.valueOf(LocalDate.now()));
        info.setSesID(sesID);
        info.setUserID(userID);
        info.setQuestion(question);
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
    public ResponseEntity<List<Info>> getInfo(@RequestParam(value = "sesID", required = false) String sesID,
                                              @RequestParam(value = "email", required = false) String email) {
        List<Info> infoList = new ArrayList<>();
        if (sesID != null) {
            infoList.addAll(infoMapper.getInfoBySes(Integer.parseInt(sesID)));
        }
        if (email != null) {
            infoList.addAll(infoMapper.getInfoByEmail(email));
        }
        return new ResponseEntity<>(infoList, HttpStatus.OK);
    }
}
