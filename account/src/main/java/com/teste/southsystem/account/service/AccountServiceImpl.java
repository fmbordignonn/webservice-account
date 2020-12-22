package com.teste.southsystem.account.service;

import com.teste.southsystem.account.model.DTO.PersonDTO;
import com.teste.southsystem.account.model.Entity.AccountEntity;
import com.teste.southsystem.account.model.Entity.LimitEntity;
import com.teste.southsystem.account.model.Entity.PersonEntity;
import com.teste.southsystem.account.repository.AccountRepository;
import com.teste.southsystem.account.repository.LimitRepository;
import com.teste.southsystem.account.repository.PersonRepository;
import com.teste.southsystem.account.util.Utils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class AccountServiceImpl implements AccountService {

    private static Logger LOG = getLogger(AccountServiceImpl.class);

    private final AccountRepository accountRepository;
    private final LimitRepository limitRepository;
    private final PersonRepository personRepository;

    @Value("${bank.default.agency}")
    private int DEFAULT_AGENCY;

    @Value("${bank.parameters.score.bronze}")
    private int bronzeScore;

    @Value("${bank.parameters.score.silver}")
    private int silverScore;

    @Value("${bank.parameters.score.gold}")
    private int goldScore;

    @Value("${bank.parameters.score.platinum}")
    private int platinumScore;

    @Value("${bank.parameters.credit.limit.bronze}")
    private double bronzeLimit;

    @Value("${bank.parameters.credit.limit.silver}")
    private double silverLimit;

    @Value("${bank.parameters.credit.limit.gold}")
    private double goldLimit;

    @Value("${bank.parameters.credit.limit.platinum}")
    private double platinumLimit;

    @Value("${bank.parameters.specialCheck.limit.bronze}")
    private double bronzeSpecialCheck;

    @Value("${bank.parameters.specialCheck.limit.silver}")
    private double silverSpecialCheck;

    @Value("${bank.parameters.specialCheck.limit.gold}")
    private double goldSpecialCheck;

    @Value("${bank.parameters.specialCheck.limit.platinum}")
    private double platinumSpecialCheck;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository, LimitRepository limitRepository, PersonRepository personRepository) {
        this.accountRepository = accountRepository;
        this.limitRepository = limitRepository;
        this.personRepository = personRepository;
    }

    @Override
    @Transactional
    public void createAccount(PersonDTO person) {
        LOG.info("Creating new account for person with name={}", person.getName());

        AccountEntity account = buildAccountEntity(person);
        accountRepository.save(account);

        LOG.info("Saved account information: {}", account.toString());

        LimitEntity limit = buildLimitEntity(person.getScore(), account.getAccountId());
        limitRepository.save(limit);

        LOG.info("Saved limit information: {}", limit.toString());

        LOG.info("Person saved successfully");
    }

    @Override
    public List<AccountEntity> findAll(){

        try{
            LOG.info("Getting all accounts information from database");
            return accountRepository.findAll();
        }catch(Exception ex){
            LOG.error("An error occurred while trying to fetch accounts");
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, ex.getMessage());
        }
    }

    public LimitEntity buildLimitEntity(int score, Long accountId) {
        LOG.info("Building limit entity");

        LimitEntity entity = new LimitEntity();

        if (score <= bronzeScore) {
            entity.setCreditCartLimit(bronzeLimit);
            entity.setSpecialCheckLimit(bronzeSpecialCheck);
        } else if (score <= silverScore) {
            entity.setCreditCartLimit(silverLimit);
            entity.setSpecialCheckLimit(silverSpecialCheck);
        } else if (score <= goldScore) {
            entity.setCreditCartLimit(goldLimit);
            entity.setSpecialCheckLimit(goldSpecialCheck);
        } else if (score == platinumScore) {
            entity.setCreditCartLimit(platinumLimit);
            entity.setSpecialCheckLimit(platinumSpecialCheck);
        } else {
            throw new IllegalArgumentException(String.format("Unable to identify special check and credit limits for score %d", score));
        }

        entity.setAccountId(accountId);

        return entity;
    }

    public AccountEntity buildAccountEntity(PersonDTO person) {
        LOG.info("Building account entity");
        AccountEntity entity = new AccountEntity();

        Optional<PersonEntity> personEntity = personRepository.findById(person.getPersonId());

        if (personEntity.isEmpty()) {
            throw new EntityNotFoundException(String.format("Unable to retrieve person with id %s from the database", person.getPersonId()));
        }

        entity.setPersonId(personEntity.get().getPersonId());
        entity.setAccountNumber(Utils.generateAccountNumber());
        entity.setAccountType(Utils.getAccountType(person.getType()));
        entity.setAgency(DEFAULT_AGENCY);
        return entity;
    }

}
