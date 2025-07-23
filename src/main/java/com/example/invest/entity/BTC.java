package com.example.invest.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class BTC {
    private int highPrice;
    private int lowPrice;
    private int tradePrice;
    private int openingPrice;
    private double candleAccTradeVolume;
    @Id
    private LocalDateTime candleDateTimeKst;
    private LocalDateTime candleDateTimeUtc;
}
