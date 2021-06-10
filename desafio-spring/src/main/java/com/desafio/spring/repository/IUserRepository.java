package com.desafio.spring.repository;

import com.desafio.spring.models.User;

import java.util.HashMap;

public interface IUserRepository {
    User getUser(int userId) throws NoSuchFieldException;

    HashMap<Integer, User> getUsers();

    void modifyUser(HashMap<Integer, User> repo);
}
