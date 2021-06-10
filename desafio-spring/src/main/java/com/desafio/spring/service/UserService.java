package com.desafio.spring.service;

import com.desafio.spring.dto.*;
import com.desafio.spring.models.User;
import com.desafio.spring.repository.IProductRepository;
import com.desafio.spring.repository.IUserRepository;
import com.desafio.spring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService {
    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IProductRepository productRepository;

    //Realiza la opcion de seguir
    @Override
    public void follow(int userId, int userIdToFollow) {
        try {
            userRepository.getUser(userId).getFollow().put(userIdToFollow, userRepository.getUser(userIdToFollow).getName());
            userRepository.getUser(userIdToFollow).getFollowers().put(userId, userRepository.getUser(userId).getName());
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        userRepository.modifyUser(userRepository.getUsers());
    }

    //Cantidad de usuarios que siguen a un vendedor
    @Override
    public FollowersCountDTO followersCount(int userid) {
        FollowersCountDTO response = new FollowersCountDTO();
        response.setUserId(userid);
        try {
            response.setUserName(userRepository.getUser(userid).getName());
            response.setFollowers_counts(userRepository.getUser(userid).getFollowers().size());
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return response;
    }

    //Listado de usuarios que siguen a un vendedor y orden alfabetico ascendente y descendente
    @Override
    public FollowersListDTO followersList(int userID) throws NoSuchFieldException {
        User user = userRepository.getUser(userID);
        List<FollowersDTO> followers =
                user.getFollowers()
                        .entrySet().stream().map(integerStringEntry ->
                        new FollowersDTO(integerStringEntry.getKey(), integerStringEntry.getValue())).collect(Collectors.toList());

        return new FollowersListDTO(userID, user.getName(), followers);

    }

    //Listado de usuarios que siguen a un comprador
    @Override
    public FollowedListDTO followedList(int userID) throws NoSuchFieldException {
        User user = userRepository.getUser(userID);
        List<FollowedDTO> followed =
                user.getFollow()
                        .entrySet().stream().map(integerStringEntry ->
                        new FollowedDTO(integerStringEntry.getKey(), integerStringEntry.getValue())).collect(Collectors.toList());

        return new FollowedListDTO(userID, user.getName(), followed);

    }

    //Listado de publicaciones en las ultimas dos semanas y orden por fecha ascendente y descendente
    @Override
    public ListPostDTO listPost(int userId, String order) throws NoSuchFieldException {
        User user = userRepository.getUser(userId);
        ListPostDTO lista = new ListPostDTO(new ArrayList<>());
        Comparator<PostDTO> comparator;
        if (order.equals("date_asc")) comparator = (PostDTO o1, PostDTO o2) -> o1.getDate().compareTo(o2.getDate());
        else if (order.equals("date_desc"))
            comparator = (PostDTO o1, PostDTO o2) -> o2.getDate().compareTo(o1.getDate());
        else throw new RuntimeException();
        for (Integer key : user.getFollow().keySet()) {
            User user1 = userRepository.getUser(key);
            for (PostDTO post : productRepository.getPosts()) {
                if (post.getUserId() == user1.getId()) {
                    lista.getList().add(post);
                }
            }
        }
        return new ListPostDTO(lista.getList().stream().filter(post -> post.getDate().after(date2WeeksAgo())).sorted(comparator).collect(Collectors.toList()));
    }

    //Realiza la opcion de dejar de seguir
    @Override
    public void unfollow(int userId, int userIdToUnFollow) {
        try {
            userRepository.getUser(userId).getFollow().remove(userIdToUnFollow);
            userRepository.getUser(userIdToUnFollow).getFollowers().remove(userId);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        userRepository.modifyUser(userRepository.getUsers());
    }

    //Orden alfabetico ascendente y descendente de usuarios que siguen a un vendedor
    @Override
    public FollowersListDTO followersListOrder(int userID, String order) throws NoSuchFieldException {
        FollowersListDTO result = followersList(userID);
        Comparator<FollowersDTO> comparator;
        if (order.equals("name_asc")) comparator = (FollowersDTO o1, FollowersDTO o2) -> o1.getUserName().compareTo(o2.getUserName());
        else if (order.equals("name_desc"))
            comparator = (FollowersDTO o1, FollowersDTO o2) -> o2.getUserName().compareTo(o1.getUserName());
        else throw new RuntimeException();
        result.getFollowers().sort(comparator);
        return result;
    }

    //Orden alfabetico ascendente y descendente de usuarios que siguen a un comprador
    @Override
    public FollowedListDTO followedListOrder(int userID, String order) throws NoSuchFieldException {
        FollowedListDTO result = followedList(userID);
        Comparator<FollowedDTO> comparator;
        if (order.equals("name_asc")) comparator = (FollowedDTO o1, FollowedDTO o2) -> o1.getUserName().compareTo(o2.getUserName());
        else if (order.equals("name_desc"))
            comparator = (FollowedDTO o1, FollowedDTO o2) -> o2.getUserName().compareTo(o1.getUserName());
        else throw new RuntimeException();
        result.getFollowed().sort(comparator);
        return result;
    }

    private Date date2WeeksAgo() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, -14);
        return calendar.getTime();
    }
}