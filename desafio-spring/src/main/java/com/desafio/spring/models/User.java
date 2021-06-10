package com.desafio.spring.models;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private int id;
    private String name;
    private Boolean buyer;
    private HashMap <Integer,String> follow = new HashMap<Integer,String>();
    private HashMap <Integer,String> followers = new HashMap<Integer,String>();

}
