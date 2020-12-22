package com.teste.southsystem.account.model.Entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Data
@Table(name = "LIMITS")
@Entity
@ToString
public class LimitEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LIMIT_ID")
    private Long idLimit;

    @Column(name = "SPECIAL_CHECK_LIMIT")
    private double specialCheckLimit;

    @Column(name = "CREDIT_CARD_LIMIT")
    private double creditCartLimit;

    @Column(name = "ACCOUNT_ID")
    private Long accountId;
}
