package com.tms.speeding.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.xml.bind.DatatypeConverter;

import com.tms.speeding.domain.dbo.LoginDbo;
import com.tms.speeding.repository.LoginRepository;
import com.tms.speeding.exception.CustomException;
import com.tms.speeding.util.ResponseObject;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private final EntityManager entityManager;
    private final LoginRepository repository;

    public LoginService (LoginRepository repository, EntityManager entityManager) {
        this.repository = repository;
        this.entityManager = entityManager;
    }

    private void setUser (String login, String password) {
        List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("USER"));
        UserDetails loggedIn = new User(login, password, authorities);
        Authentication auth = new UsernamePasswordAuthenticationToken(loggedIn, null, loggedIn.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    private String getHash(String input) {
        try {
            var md = MessageDigest.getInstance("MD5");
            md.update(input.getBytes());
            byte[] digest = md.digest();
            return DatatypeConverter.printHexBinary(digest).toUpperCase();
        } catch (NoSuchAlgorithmException ex) {
            throw new CustomException("Something went wrong");
        }
    }

    static final String QUERY_C = "select p from LoginDbo p where lower(p.login) = lower(:login)"
                + " and lower(p.password) = lower(:password)";

    public LoginDbo checkLogin(String login, String password) {
        TypedQuery<LoginDbo> query = entityManager.createQuery(QUERY_C, LoginDbo.class);
        query.setParameter("login", login).setParameter("password", password).setMaxResults(1);
        return query.getResultList().isEmpty() ? null : query.getSingleResult();
    }

    public LoginDbo findExisting(String login) {
        TypedQuery<LoginDbo> query = entityManager.createQuery("select p from LoginDbo p where lower(login) = lower(:login)", LoginDbo.class);
        query.setParameter("login", login).setMaxResults(1);
        return query.getResultList().isEmpty() ? null : query.getSingleResult();
    }

    public ResponseObject logIn(String login, String password) {
        String hash = getHash(password);
        LoginDbo user = checkLogin(login, hash);
        if (user == null) {
            return new ResponseObject(false, "error", "Invalid login credentials");
        }
        user.setLastVisit(new Date());
        repository.save(user);
        setUser(login, password);
        return new ResponseObject();
    }

    public ResponseObject regIn(String login, String password) {
        LoginDbo entry = findExisting(login);
        if (entry != null) {
            return new ResponseObject(false, "error", "Such login already exists");
        }
        var user = new LoginDbo(login, getHash(password), new Date(), new Date());
        repository.save(user);
        setUser(login, password);
        return new ResponseObject();
    }
}
