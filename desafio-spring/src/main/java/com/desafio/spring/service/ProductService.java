package com.desafio.spring.service;

import com.desafio.spring.dto.PostDTO;
import com.desafio.spring.models.Product;
import com.desafio.spring.repository.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService implements IProductService {
    @Autowired
    private IProductRepository productRepository;

    //Da de alta una publicacion
    @Override
    public void NewPost(PostDTO newPost){
        List<PostDTO> listPost = productRepository.getPosts();
        for (PostDTO p: listPost) {
            if (p.getId_post()==newPost.getId_post());
            return;
        }
        listPost.add(newPost);
        productRepository.modifyPost(listPost);

    }
}