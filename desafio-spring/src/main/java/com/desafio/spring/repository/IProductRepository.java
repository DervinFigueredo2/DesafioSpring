package com.desafio.spring.repository;

import com.desafio.spring.dto.PostDTO;
import com.desafio.spring.models.Product;

import java.util.List;

public interface IProductRepository {
    PostDTO getPost(int userId) throws NoSuchFieldException;

    List<PostDTO> getPosts();

    void modifyPost(List<PostDTO> repo);
}
