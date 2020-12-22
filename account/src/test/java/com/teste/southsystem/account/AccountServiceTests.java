package com.teste.southsystem.account;

import com.teste.southsystem.account.model.DTO.PersonDTO;
import com.teste.southsystem.account.model.Entity.AccountEntity;
import com.teste.southsystem.account.model.Entity.LimitEntity;
import com.teste.southsystem.account.repository.AccountRepository;
import com.teste.southsystem.account.repository.LimitRepository;
import com.teste.southsystem.account.repository.PersonRepository;
import com.teste.southsystem.account.service.AccountService;
import com.teste.southsystem.account.service.AccountServiceImpl;
import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.when;

@SpringBootTest
public class AccountServiceTests {

	@Mock
	private AccountService accountService;

	@Mock
	private PersonRepository personRepository;

	@Mock
	private AccountRepository accountRepository;

	@Mock
	private LimitRepository limitRepository;

	@Before
	public void setup(){
		MockitoAnnotations.openMocks(this);
		this.accountService = new AccountServiceImpl(accountRepository, limitRepository, personRepository);
	}

	@Test
	@DisplayName("Validate account creation successful")
	public void successAccountCreation() {
		PersonDTO person = createValidPerson();

		when(accountRepository.save(Mockito.any())).thenReturn(new AccountEntity());

		when(limitRepository.save(Mockito.any())).thenReturn(new LimitEntity());

		accountService.createAccount(person);
	}

	private PersonDTO createValidPerson(){
		return new PersonDTO(1L,"Test Person", "PF", "01610461002", 4);
	}

}
