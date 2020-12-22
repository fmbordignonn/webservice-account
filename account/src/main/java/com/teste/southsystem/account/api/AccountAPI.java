package com.teste.southsystem.account.api;

import com.teste.southsystem.account.service.AccountService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import static org.slf4j.LoggerFactory.getLogger;

@RestController
@RequestMapping("/account")
public class AccountAPI {

    private static Logger LOG = getLogger(AccountAPI.class);

    private AccountService accountService;

    @Autowired
    public AccountAPI(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/findAll")
    public ResponseEntity<?> getAllAccounts() {
        try {
            LOG.info("Getting all accounts information");
            return ResponseEntity.ok(accountService.findAll());
        } catch (ResponseStatusException response) {
            LOG.error("An error occurred while trying to get all accounts information");
            LOG.error("Error [{}]", response.getMessage());
            return ResponseEntity.status(response.getStatus()).body(response.getMessage());

        }
    }
}
