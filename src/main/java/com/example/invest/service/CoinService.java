package com.example.invest.service;

import com.example.invest.dto.CoinDTO;
import com.example.invest.entity.MINITE;
import com.example.invest.repository.CandleRepository;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class CoinService {

    private final CandleRepository candleRepository;

    private final SimpMessagingTemplate messagingTemplate;
    private final Gson gson = new Gson();
    private final OkHttpClient client = new OkHttpClient();
    @Scheduled(fixedRate = 1000)
    public void updateCoinPrices() throws IOException {
        Request request = new Request.Builder()
          .url("https://api.bithumb.com/v1/ticker?markets=KRW-BTC")
          .get()
          .addHeader("accept", "application/json")
          .build();
        Response response = client.newCall(request).execute();
        Type coinListType = new TypeToken<List<CoinDTO>>(){}.getType();
        List<CoinDTO> data = gson.fromJson(response.body().string(), coinListType);
        messagingTemplate.convertAndSend("/topic/BTC", data.get(0).getTrade_price());
        log.info(data);
    }

    @Scheduled(fixedRate = 1000)
    public void updateXRPPrices() throws IOException {
        Request request = new Request.Builder()
          .url("https://api.bithumb.com/v1/ticker?markets=KRW-XRP")
          .get()
          .addHeader("accept", "application/json")
          .build();
        Response response = client.newCall(request).execute();
        Type coinListType = new TypeToken<List<CoinDTO>>(){}.getType();
        List<CoinDTO> data = gson.fromJson(response.body().string(), coinListType);
        messagingTemplate.convertAndSend("/topic/XRP", data.get(0).getTrade_price());
        log.info(data);
    }


    @Scheduled(fixedRate = 1000)
    public void updateXRPCandle() throws IOException {
        Request request = new Request.Builder()
          .url("https://api.bithumb.com/v1/candles/minutes/1?market=KRW-XRP&count=2")
          .get()
          .addHeader("accept", "application/json")
          .build();

        Response response = client.newCall(request).execute();
        Type coinListType = new TypeToken<List<CoinDTO>>(){}.getType();
        List<CoinDTO> data = gson.fromJson(response.body().string(), coinListType);
        messagingTemplate.convertAndSend("/topic/Chart/XRP", data.get(0));

        MINITE entity = EntityMapper.dtoToEntity(data.get(1));
        try{
            candleRepository.save(entity);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public List<CoinDTO> selectCoinCandle(){
        Pageable pageable = PageRequest.of(0, 100, Sort.by(Sort.Direction.DESC, "candle_date_time_kst"));
        List<MINITE> result = candleRepository.selectCandle(pageable);
        result.sort(Comparator.comparing(MINITE::getCandle_date_time_kst));
        return result.stream().map(EntityMapper::entityToDto).collect(Collectors.toList());
    }
}
