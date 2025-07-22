package com.example.invest.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class MINITE {

    private String market;
    private int high_price;
    private int low_price;
    private int trade_price;
    private int opening_price;
    private double candle_acc_trade_volume;
    @Id
    private LocalDateTime candle_date_time_kst;
    private LocalDateTime candle_date_time_utc;
}
