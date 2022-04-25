package com.reserve.controller;

import com.reserve.mapper.InfoMapper;
import com.reserve.mapper.SesMapper;
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
    private final SesMapper sesMapper;

    public InfoController(InfoMapper infoMapper, SesMapper sesMapper) {
        this.infoMapper = infoMapper;
        this.sesMapper = sesMapper;
    }

    @PostMapping("/api/rsv")
    public ResponseEntity<Map<String, String>> addInfo(@RequestBody Map<String, String> map) {
        int sesID = Integer.parseInt(map.get("sesID"));
        int position = Integer.parseInt(map.get("position"));
        int userID = Integer.parseInt(map.get("userID "));
        Map<String, String> m = new HashMap<>();
        String model = map.get("model");
        Info info = Info.builder().build();
        info.setPosition(position);
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
    public ResponseEntity<List<Info>> getInfo(@RequestBody(required = false) Map<String, String> map) {
        List<Info> infoList = new ArrayList<>();
        if (map == null) {
            infoList.addAll(infoMapper.getAll());
            return new ResponseEntity<>(infoList, HttpStatus.OK);
        }
        int sesID = Integer.parseInt(map.get("sesID"));
        String email = map.get("email");
        if (sesID != 0) {
            infoList.addAll(infoMapper.getInfoBySes(sesID));
        }
        if (email != null) {
            infoList.addAll(infoMapper.getInfoByEmail(email));
        }
        return new ResponseEntity<>(infoList, HttpStatus.OK);
    }
}
