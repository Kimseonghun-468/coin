package com.example.invest.controller;

import com.example.invest.service.UpbitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Log4j2
@RequiredArgsConstructor
public class investController {

    private final UpbitService upbitService;
    @GetMapping(value = "/test")
    public String index() {
        log.info("test");
        return "test";
    }

    @GetMapping("/api/upbit/accounts")
    public String getAccounts() {
        upbitService.getAccounts();
        return "test";
    }

    @GetMapping("/api/upbit/placeOrder")
        public String placeOrder() {
            upbitService.placeOrder();
            return "test";
        }
}
