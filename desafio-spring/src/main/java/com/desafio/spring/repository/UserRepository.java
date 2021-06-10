package com.desafio.spring.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.desafio.spring.models.User;
import lombok.Data;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class UserRepository implements IUserRepository {
    private HashMap<Integer, User> users;

    //mapa de usuarios
    @Override
    public HashMap<Integer, User> getUsers() {
        return this.users;
    }

    public UserRepository() {
        this.users = loadFromDB();
    }

    @Override
    public User getUser(int userId) throws NoSuchFieldException {
        return this.users.get(userId);
    }

    //modifica el mapa de usuarios
    @Override
    public void modifyUser(HashMap<Integer, User> repo) {
        File file = new File("src/main/resources/users.json");
        var objectmappers = new ObjectMapper();
        try {
            objectmappers.writeValue(file, repo);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //carga la lista de usuarios
    private HashMap<Integer, User> loadFromDB() {
        HashMap<Integer, User> ret = null;

        File file = null;

        try {
            file = ResourceUtils.getFile("classpath:users.json");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        var objectMapper = new ObjectMapper();

        try {
            ret = objectMapper.readValue(file, new TypeReference<HashMap<Integer, User>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ret;
    }

}