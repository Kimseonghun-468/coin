package com.example.invest.controller;


import com.example.invest.dto.AccountDTO;
import com.example.invest.dto.BTCDTO;
import com.example.invest.dto.CoinDTO;
import com.example.invest.dto.ETHDTO;
import com.example.invest.request.CandleRequest;
import com.example.invest.service.CoinService;
import com.example.invest.service.UpbitService;
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
    private final UpbitService upbitService;

    @PostMapping("/selectCandle")
    @CrossOrigin(origins = "http://192.168.0.117:5137")
    public ResponseEntity selectCandle(@RequestBody CandleRequest candleRequest) {
        List<CoinDTO> result = coinService.selectCoinCandle();
        return ResponseEntity.ok(result);
    }

    @PostMapping("/selectBTCCandle")
    @CrossOrigin(origins = "http://192.168.0.117:5137")
    public ResponseEntity selectBTCCandle(@RequestBody CandleRequest candleRequest) {
        List<BTCDTO> result = coinService.selectBTCCandle();
        return ResponseEntity.ok(result);
    }

    @PostMapping("/selectETHCandle")
    @CrossOrigin(origins = "http://192.168.0.117:5137")
    public ResponseEntity selectETHCandle(@RequestBody CandleRequest candleRequest) {
        List<ETHDTO> result = coinService.selectETHCandle();
        return ResponseEntity.ok(result);
    }

    @PostMapping("/selectAccount")
    @CrossOrigin(origins = "http://192.168.0.117:5137")
    public ResponseEntity selectAccount() {
        List<AccountDTO> result = upbitService.getAccounts();
        System.out.println(result);
        if(result == null){
            return ResponseEntity.badRequest().body("요청이 잘못되었습니다.");
        }
        return ResponseEntity.ok(result);
    }

}
