package com.tms.speeding.service;


import com.tms.speeding.domain.dbo.LoginDbo;
import com.tms.speeding.repository.LoginRepository;
import com.tms.speeding.util.ResponseObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;


@ExtendWith(SpringExtension.class)
@SpringBootTest
class LoginServiceTest {

    @MockBean
    private LoginRepository repo;

    @MockBean
    private EntityManager entityManager;

    @MockBean
    private TypedQuery<LoginDbo> q;

    @MockBean
    private LoginDbo user;

    private LoginService service;

    @BeforeEach
    public void setUp() {
        service = new LoginService(repo, entityManager);
    }

    @Test
    public void assertTrueIfLogInIsSuccessful() {

        String login = "login";
        String password = "password";

        Mockito.when(repo.save(any(LoginDbo.class))).thenReturn(user);

        ResponseObject successfulResponse = new ResponseObject();

        Mockito.when(entityManager.createQuery(LoginService.QUERY_C, LoginDbo.class)).thenReturn(q);
        Mockito.when(q.setParameter(anyString(), any())).thenReturn(q);
        Mockito.when(q.getResultList()).thenReturn(List.of(user));
        Mockito.when(q.getSingleResult()).thenReturn(user);

        ResponseObject logInResponse = service.logIn(login, password);

        boolean isSuccessfulLogIn = (successfulResponse.isSuccess() == logInResponse.isSuccess());

        assertTrue(isSuccessfulLogIn);
    }

    @Test
    public void assertFalseIfLogInIsNotSuccessful() {
        String login = "login";
        String password = "incorrectPassword";

        Mockito.when(repo.save(any(LoginDbo.class))).thenReturn(user);

        ResponseObject successfulResponse = new ResponseObject();

        Mockito.when(entityManager.createQuery(LoginService.QUERY_C, LoginDbo.class)).thenReturn(q);
        Mockito.when(q.setParameter(anyString(), any())).thenReturn(q);
        Mockito.when(q.getResultList()).thenReturn(new ArrayList<>());

        ResponseObject logInResponse = service.logIn(login, password);

        boolean isSuccessfulLogIn = (successfulResponse.isSuccess() == logInResponse.isSuccess());

        assertFalse(isSuccessfulLogIn);
    }


    @Test
    public void assertTrueIfRegInIsSuccessful() {

        String login = "login";
        String password = "password";


        String query = "select p from LoginDbo p where lower(login) = lower(:login)";

        Mockito.when(entityManager.createQuery(query, LoginDbo.class)).thenReturn(q);
        Mockito.when(q.setParameter(anyString(), any())).thenReturn(q);
        Mockito.when(q.getResultList()).thenReturn(List.of(user));

        Mockito.when(repo.save(any(LoginDbo.class))).thenReturn(user);

        ResponseObject regInResponse = service.regIn(login, password);

        boolean isSuccessfulLogIn = (regInResponse.isSuccess());

        assertTrue(isSuccessfulLogIn);
    }

    @Test
    public void assertFalseIfRegInIsNotSuccessful() {

        String login = "login";
        String password = "password";

        String query = "select p from LoginDbo p where lower(login) = lower(:login)";

        Mockito.when(entityManager.createQuery(query, LoginDbo.class)).thenReturn(q);
        Mockito.when(q.setParameter(anyString(), any())).thenReturn(q);
        Mockito.when(q.getResultList()).thenReturn(List.of(user));
        Mockito.when(q.getSingleResult()).thenReturn(user);

        ResponseObject regInResponse = service.regIn(login, password);

        boolean isSuccessfulLogIn = (regInResponse.isSuccess());

        assertFalse(isSuccessfulLogIn);
    }
}
