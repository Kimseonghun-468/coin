package com.example.invest.repository;

import com.example.invest.dto.ETHDTO;
import com.example.invest.entity.BTC;
import com.example.invest.entity.ETH;
import com.example.invest.entity.MINITE;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ETHRepository extends JpaRepository<ETH, Long> {
    @Query("SELECT Candle FROM ETH Candle ORDER BY Candle.candleDateTimeKst DESC")
        List<ETH> selectCandle(Pageable pageable);
}
