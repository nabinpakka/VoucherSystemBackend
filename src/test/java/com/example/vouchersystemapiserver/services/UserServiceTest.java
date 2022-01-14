package com.example.vouchersystemapiserver.services;

import com.example.vouchersystemapiserver.models.User;
import com.example.vouchersystemapiserver.repo.UserRepository;
import org.hibernate.annotations.ManyToAny;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class UserServiceTest {

    @InjectMocks
    UserService userService;

    @MockBean
    UserRepository userRepository;

    @Test
    void findUserByUsername() {
        User user=new User(UUID.randomUUID(),"user");

        Mockito.when(userRepository.findByUsername("user")).thenReturn(java.util.Optional.of(user));

        Optional<User> created=userService.findUserByUsername("user");

        assertEquals(created.get(),user);

        //created=userRepository.findByUsername("name");
        //assertEquals(created.isEmpty(),true);
    }

    @Test
    void createUser() {
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenAnswer(new Answer<User>() {
            @Override
            public User answer(InvocationOnMock invocationOnMock) throws Throwable {
                return invocationOnMock.getArgument(0);
            }
        });
        User user=new User(UUID.randomUUID(),"user");
        User created=userService.createUser(user);
        assertEquals(created,user);
    }

    @Test
    void update() {
        UUID uuid=UUID.randomUUID();
        String username="user";

        User user=new User(uuid,username);
        user.update(100);
        user.setNumberOfIncorrectAttempts(0);
        user.setRecentTimeStampForIncorrectAttempt(0);

        Mockito.when(userRepository.save(new User(uuid,username))).thenReturn(user);

        User updated=userService.update(new User(uuid,username),100);

       assertEquals(updated.getValue(),100);
       assertEquals(updated.getNumberOfIncorrectAttempts(),0);
       assertEquals(updated.getRecentTimeStampForIncorrectAttempt(),0);
    }

    @Test
    void updateInCorrectAttempt() {
        UUID uuid=UUID.randomUUID();
        String username="user";
        User user=new User(uuid,username);

        Mockito.when(userRepository.save(Mockito.any(User.class))).thenAnswer(new Answer<User>() {
            @Override
            public User answer(InvocationOnMock invocationOnMock) throws Throwable {
                return invocationOnMock.getArgument(0);
            }
        });

        //1st case :: time_difference > 5 mins
        User updatedUser=userService.updateInCorrectAttempt(user);
        assertEquals(updatedUser.getNumberOfIncorrectAttempts(),1);

        user.setRecentTimeStampForIncorrectAttempt(System.currentTimeMillis());
        user.setNumberOfIncorrectAttempts(2);
        updatedUser=userService.updateInCorrectAttempt(user);
        assertEquals(updatedUser.getNumberOfIncorrectAttempts(),3);

        user.setNumberOfIncorrectAttempts(3);
        updatedUser=userService.updateInCorrectAttempt(user);
        assertEquals(updatedUser.isTransactionDisable(),true);

    }
}