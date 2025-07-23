package com.example.invest.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BTCDTO {
    private int highPrice;
    private int lowPrice;
    private int tradePrice;
    private int openingPrice;
    private double candleAccTradeVolume;
    private String candleDateTimeUtc;
    private String candleDateTimeKst;
}
