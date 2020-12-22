package com.teste.southsystem.account.model.DTO;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;


@Data
public class AccountDTO {
    private int accountNumber;

    @Value("${bank.default.agency}")
    private int agency;

    private char accountType;

    private double specialCheckLimit;

    private double creditCardLimit;

    private Long idPerson;
}
