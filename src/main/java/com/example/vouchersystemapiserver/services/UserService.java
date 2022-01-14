package com.example.vouchersystemapiserver.services;

import com.example.vouchersystemapiserver.models.User;
import com.example.vouchersystemapiserver.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Optional<User> findUserByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public User createUser(User user){
        return userRepository.save(user);
    }

    public User update(User user,int value){
        user.update(value);
        user.setNumberOfIncorrectAttempts(0);
        user.setRecentTimeStampForIncorrectAttempt(0);
        return userRepository.save(user);
    }

    //Incorrect attempts
    public User updateInCorrectAttempt(User user){
        if (System.currentTimeMillis()-user.getRecentTimeStampForIncorrectAttempt()<5*60*1000){
            user.setNumberOfIncorrectAttempts(user.getNumberOfIncorrectAttempts()+1);
        }else {
            user.setNumberOfIncorrectAttempts(1);
        }
        user.setRecentTimeStampForIncorrectAttempt(System.currentTimeMillis());

        if (user.getNumberOfIncorrectAttempts()>3){
            user.setTransactionDisable(true);
        }

        User us=userRepository.save(user);

        return user;
    }
}
