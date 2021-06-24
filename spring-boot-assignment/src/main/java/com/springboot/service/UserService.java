package com.springboot.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.dao.UserDao;
import com.springboot.entity.User;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserDao userDao;

    public User addUser(User user)
    {
        return userDao.save(user);
    }

    public User getUserByEmail(String email)
    {
        return userDao.getUserByEmail(email);
    }

    @Modifying
    public void updateUser(String id,User newUser)
    {
        userDao.deleteById(id);
        User u=userDao.save(newUser);
    }

}
