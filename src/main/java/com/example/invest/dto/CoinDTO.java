package com.example.invest.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CoinDTO {
    private String market;
    private int high_price;
    private int low_price;
    private int trade_price;
    private int opening_price;
    private double candle_acc_trade_volume;
    private String candle_date_time_utc;
    private String candle_date_time_kst;
}
