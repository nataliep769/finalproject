package com.example.finalproject.models.data;

import com.example.finalproject.models.Post;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Natalie on 7/3/2017.
 */
public interface PostDao extends CrudRepository<Post, Integer>{}
