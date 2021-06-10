package com.desafio.spring.service;

import com.desafio.spring.dto.FollowedListDTO;
import com.desafio.spring.dto.FollowersCountDTO;
import com.desafio.spring.dto.FollowersListDTO;
import com.desafio.spring.dto.ListPostDTO;
import com.desafio.spring.models.User;

import java.util.HashMap;

public interface IUserService {
    void follow(int userId, int userIdToFollow);

    FollowersCountDTO followersCount(int userid);

    FollowersListDTO followersList(int userID) throws NoSuchFieldException;

    FollowedListDTO followedList(int userID) throws NoSuchFieldException;

    ListPostDTO listPost(int userId, String order) throws NoSuchFieldException;

    void unfollow(int userId, int userIdToUnFollow);

    FollowersListDTO followersListOrder(int userID, String order) throws NoSuchFieldException;

    FollowedListDTO followedListOrder(int userID, String order) throws NoSuchFieldException;
}
