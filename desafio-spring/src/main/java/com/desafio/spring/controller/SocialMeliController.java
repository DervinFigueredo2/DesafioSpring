package com.desafio.spring.controller;

import com.desafio.spring.dto.*;
import com.desafio.spring.models.Product;
import com.desafio.spring.service.IProductService;
import com.desafio.spring.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class SocialMeliController {
    @Autowired
    private IUserService userService;
    @Autowired
    private IProductService productService;

    //US0001
    @PostMapping("/users/{userId}/follow/{userIdToFollow}")
    public ResponseEntity follow(@PathVariable int userId, @PathVariable int userIdToFollow) {
        try {
            this.userService.follow(userId, userIdToFollow);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }

    }

    //US0002
    @GetMapping("/users/{userId}/followers/count/")
    public ResponseEntity<Object> followersCount(@PathVariable int userId) {
        FollowersCountDTO response = userService.followersCount(userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    //US0003, US0008
    @GetMapping("/users/{userID}/followers/list")
    public ResponseEntity<Object> followersList(@PathVariable int userID,@RequestParam(value = "order", required = false) String order) throws NoSuchFieldException {
        FollowersListDTO response= new FollowersListDTO();
        if (order==null)
            response = userService.followersList(userID);
        else
            response = userService.followersListOrder(userID, order);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //US0004, US0008
    @GetMapping("/users/{userID}/followed/list")
    public ResponseEntity<Object> followedList(@PathVariable int userID, @RequestParam(value = "order", required = false) String order) throws NoSuchFieldException {
        FollowedListDTO response = new FollowedListDTO();
        if (order==null)
            response = userService.followedList(userID);
        else
            response = userService.followedListOrder(userID, order);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //US0005
    @PostMapping("/products/newpost")
    public ResponseEntity newPost(@RequestBody PostDTO post) {
        try {
            this.productService.NewPost(post);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    //US0006,USOOO9
    @GetMapping("/products/followed/{userId}/list")
    public ResponseEntity<Object> listPost(@PathVariable int userId, @RequestParam(defaultValue = "date_asc") String order) throws NoSuchFieldException {
        ListPostDTO response = userService.listPost(userId, order);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //US0007
    @PostMapping("/users/{userId}/unfollow/{userIdToUnfollow}")
    public ResponseEntity Unfollow(@PathVariable int userId, @PathVariable int userIdToUnfollow) {
        try {
            this.userService.unfollow(userId, userIdToUnfollow);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }


    }
}