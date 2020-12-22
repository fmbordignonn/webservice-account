package com.teste.southsystem.account.model.Entity;

import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;

@Data
@Table(name = "ACCOUNTS")
@Entity
@ToString
public class AccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ACCOUNT_ID")
    private Long accountId;

    @Column(name = "ACCOUNT_NUMBER")
    private int accountNumber;

    @Column(name = "AGENCY")
    private int agency;

    @Column(name = "TYPE")
    private char accountType;

    @Column(name = "PERSON_ID")
    private Long personId;
}