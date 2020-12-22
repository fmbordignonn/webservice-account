package com.teste.southsystem.account.service;

import com.teste.southsystem.account.model.DTO.PersonDTO;
import com.teste.southsystem.account.model.Entity.AccountEntity;

import java.util.List;

public interface AccountService {
    void createAccount(PersonDTO person);

    List<AccountEntity> findAll();
}
