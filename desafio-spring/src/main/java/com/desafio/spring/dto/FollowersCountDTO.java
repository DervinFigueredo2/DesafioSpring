package com.desafio.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FollowersCountDTO {
    private int userId;
    private String userName;
    private int followers_counts;
}
