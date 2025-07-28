package com.example.invest.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.invest.dto.AccountDTO;
import com.example.invest.dto.CoinDTO;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.codec.binary.Hex;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UpbitService {

    private static final String ACCESS_KEY = "?";
    private static final String SECRET_KEY = "?";

    private final RestTemplate restTemplate = new RestTemplate();
    private final Gson gson = new GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .create();
    public List<AccountDTO> getAccounts() {
        try {
            // 1. JWT 생성용 payload
            String nonce = UUID.randomUUID().toString();

            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            String jwtToken = JWT.create()
                    .withClaim("access_key", ACCESS_KEY)
                    .withClaim("nonce", nonce)
                    .sign(algorithm);

            // 2. Authorization 헤더 설정
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + jwtToken);
            headers.set("Content-Type", "application/json");

            HttpEntity<String> entity = new HttpEntity<>(headers);

            // 3. 요청
            String url = "https://api.upbit.com/v1/accounts";
            ResponseEntity<String> response = restTemplate.exchange(
                    url, HttpMethod.GET, entity, String.class
            );

            Type coinListType = new TypeToken<List<AccountDTO>>(){}.getType();
            List<AccountDTO> accountDTOS = gson.fromJson(response.getBody(), coinListType);
            System.out.println("자산 조회 결과:");
            System.out.println(accountDTOS);
            return accountDTOS;


        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void placeOrder() {
        try {
            // 1. 주문 파라미터
            Map<String, String> params = new TreeMap<>();
            params.put("market", "KRW-BTC");
            params.put("side", "bid"); // 매수
            params.put("volume", "20");
            params.put("price", "300"); // 5천만 원에 매수
            params.put("ord_type", "limit");

            // 2. query string 생성
            String queryString = params.entrySet().stream()
                .map(e -> e.getKey() + "=" + URLEncoder.encode(e.getValue(), StandardCharsets.UTF_8))
                .collect(Collectors.joining("&"));

            // 3. SHA512 해시로 query_hash 생성
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] hash = md.digest(queryString.getBytes(StandardCharsets.UTF_8));
            String queryHash = Hex.encodeHexString(hash);

            // 4. JWT 생성
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            String jwtToken = JWT.create()
                    .withClaim("access_key", ACCESS_KEY)
                    .withClaim("nonce", UUID.randomUUID().toString())
                    .withClaim("query_hash", queryHash)
                    .withClaim("query_hash_alg", "SHA512")
                    .sign(algorithm);

            // 5. 헤더 구성
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");
            headers.set("Authorization", "Bearer " + jwtToken);

            // 6. 바디 구성
            HttpEntity<Map<String, String>> entity = new HttpEntity<>(params, headers);

            // 7. 요청 전송
            ResponseEntity<String> response = restTemplate.postForEntity(
                    "https://api.upbit.com/v1/orders", entity, String.class);

            System.out.println("주문 결과:");
            System.out.println(response.getBody());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}