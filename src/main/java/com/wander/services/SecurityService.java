package com.wander.services;

public interface SecurityService {
    String findLoggedInUsername();

    void autoLogin(String username, String password);
}