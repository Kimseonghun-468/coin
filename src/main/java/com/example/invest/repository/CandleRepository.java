package com.example.invest.repository;

import com.example.invest.entity.MINITE;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CandleRepository extends JpaRepository<MINITE, String> {

    @Query("SELECT Candle FROM MINITE Candle ORDER BY Candle.candle_date_time_kst DESC")
    List<MINITE> selectCandle(Pageable pageable);

}
