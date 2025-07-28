package com.example.invest.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class AccountDTO {
    private String currency;
    private BigDecimal balance;
    private BigDecimal locked;
    private BigDecimal avgBuyPrice;
}
