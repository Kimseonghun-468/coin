package com.example.invest.service;

import com.example.invest.dto.CoinDTO;
import com.example.invest.entity.MINITE;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class EntityMapper {

    public static MINITE dtoToEntity(CoinDTO dto) {
        return MINITE.builder()
                .market(dto.getMarket())
                .high_price(dto.getHigh_price())
                .low_price(dto.getLow_price())
                .trade_price(dto.getTrade_price())
                .opening_price(dto.getOpening_price())
                .candle_acc_trade_volume(dto.getCandle_acc_trade_volume())
                .candle_date_time_utc(LocalDateTime.parse(dto.getCandle_date_time_utc(), DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .candle_date_time_kst(LocalDateTime.parse(dto.getCandle_date_time_kst(), DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .build();
    }

    public static CoinDTO entityToDto(MINITE entity) {
        return CoinDTO.builder()
                .market(entity.getMarket())
                .high_price(entity.getHigh_price())
                .low_price(entity.getLow_price())
                .trade_price(entity.getTrade_price())
                .opening_price(entity.getOpening_price())
                .candle_acc_trade_volume(entity.getCandle_acc_trade_volume())
                .candle_date_time_kst(entity.getCandle_date_time_kst().toString())
                .candle_date_time_utc(entity.getCandle_date_time_utc().toString())
                .build();
    }
}
