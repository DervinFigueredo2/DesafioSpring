package com.desafio.spring.repository;

import com.desafio.spring.dto.PostDTO;
import com.desafio.spring.models.Product;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@Repository
public class ProductRepository implements IProductRepository {
    private List<PostDTO> listPost;

    //lista de post
    @Override
    public List<PostDTO> getPosts() { return this.listPost; }

    public ProductRepository() {
        this.listPost= loadFromDB();
    }

    @Override
    public PostDTO getPost(int userId) throws NoSuchFieldException {
        return this.listPost.get(userId);
    }

    //sobreescribe la lista de productos
    @Override
    public void modifyPost(List<PostDTO> repo) {
        File file = new File("src/main/resources/products.json");
        var objectmappers = new ObjectMapper();
        try {
            objectmappers.writeValue(file, repo);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //carga la lista de productos
    private List<PostDTO> loadFromDB() {
        List<PostDTO> ret = null;

        File file = null;

        try {
            file = ResourceUtils.getFile("classpath:products.json");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        var objectMapper = new ObjectMapper();

        try {
            ret = objectMapper.readValue(file, new TypeReference<List<PostDTO>>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ret;
    }
}