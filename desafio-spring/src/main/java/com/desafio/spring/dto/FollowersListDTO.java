package com.desafio.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FollowersListDTO {
    private int userId;
    private String userName;
    List <FollowersDTO> followers;
}
