package com.tms.speeding.domain.dbo;

import com.tms.speeding.repository.LoginRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class LoginDboTest {
    @Autowired
    private LoginRepository repo;

    private LoginDbo testAccount;

    @BeforeEach
    public void setUp() {
        this.testAccount = new LoginDbo("testLogin", "testPassword", new Date(), new Date());
        this.repo.save(this.testAccount);
    }

    @Test
    public void createAccountTest() {

        Optional<LoginDbo> optionalFindingAccount = this.repo.findById(this.testAccount.getId());

        if (optionalFindingAccount.isPresent()) {
            LoginDbo findingAccount = optionalFindingAccount.get();

            assertNotNull(findingAccount);

            assertThat(findingAccount.getLogin()).isEqualTo(this.testAccount.getLogin());
            assertThat(findingAccount.getPassword()).isEqualTo(this.testAccount.getPassword());
            assertThat(findingAccount.getLastVisit()).isEqualTo(this.testAccount.getLastVisit());
        }
    }

    @Test
    public void updateAccountPropertiesTest() {

        Optional<LoginDbo> optionalFindingAccount = this.repo.findById(this.testAccount.getId());
        LoginDbo findingAccount = null;
        if (optionalFindingAccount.isPresent()) {
            findingAccount = optionalFindingAccount.get();

            findingAccount.setLastVisit(new Date());
            findingAccount.setLogin("newLogin");
            findingAccount.setPassword("newPass");
            findingAccount.setRegDate(new Date());

            repo.save(findingAccount);
        }

        Optional<LoginDbo> optionalNewFindingAccount = this.repo.findById(findingAccount.getId());
        if (optionalNewFindingAccount.isPresent()) {
            LoginDbo newFindingAccount = optionalNewFindingAccount.get();

            assertNotNull(newFindingAccount);

            assertThat(newFindingAccount.getId()).isEqualTo(findingAccount.getId());
            assertThat(newFindingAccount.getLogin()).isEqualTo(findingAccount.getLogin());
            assertThat(newFindingAccount.getPassword()).isEqualTo(findingAccount.getPassword());
            assertThat(newFindingAccount.getRegDate()).isEqualTo(findingAccount.getRegDate());
            assertThat(newFindingAccount.getLastVisit()).isEqualTo(findingAccount.getLastVisit());
        }

    }

    @Test
    public void deleteAccountTest() {
        repo.delete(this.testAccount);

        Optional<LoginDbo> findingAccount = repo.findById(this.testAccount.getId());

        findingAccount.ifPresent(loginDbo -> assertThat(loginDbo).isNotEqualTo(this.testAccount));
    }
}