package com.example.invest.controller;


import com.example.invest.dto.CoinDTO;
import com.example.invest.repository.CandleRepository;
import com.example.invest.request.CandleRequest;
import com.example.invest.service.CoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api")
@RequiredArgsConstructor
public class investApiController {

    private final CoinService coinService;
    @PostMapping("/selectCandle")
    @CrossOrigin(origins = "http://192.168.0.117:5137")
    public ResponseEntity selectCandle(@RequestBody CandleRequest candleRequest) {
        List<CoinDTO> result = coinService.selectCoinCandle();
        return ResponseEntity.ok(result);
    }
}
