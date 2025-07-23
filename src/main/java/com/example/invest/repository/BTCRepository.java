package com.example.invest.repository;

import com.example.invest.entity.BTC;
import com.example.invest.entity.MINITE;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BTCRepository extends JpaRepository<BTC, String> {
    @Query("SELECT Candle FROM BTC Candle ORDER BY Candle.candleDateTimeKst DESC")
        List<BTC> selectCandle(Pageable pageable);
}
