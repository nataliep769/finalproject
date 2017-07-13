package com.example.finalproject.models.data;

import com.example.finalproject.models.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Natalie on 7/3/2017.
 */
public interface UserDao extends CrudRepository<User, Integer> {

}
