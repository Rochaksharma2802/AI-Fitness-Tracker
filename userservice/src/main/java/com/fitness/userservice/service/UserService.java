package com.fitness.userservice.service;

import com.fitness.userservice.dto.RegisterRequest;
import com.fitness.userservice.dto.UserResponse;
import com.fitness.userservice.model.User;
import com.fitness.userservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

            public UserResponse register(RegisterRequest request)
            {

                if(userRepository.existsByEmail(request.getEmail()))
                {
                    throw new RuntimeException(("Email already Exists"));
                }

                User user = new User();
                user.setEmail(request.getEmail());
                user.setPassword(request.getPassword());
                user.setFirstName(request.getFirstName());
                user.setLastName(request.getLastName());

                //saving the user to the database
                User savedUser = userRepository.save(user);
                UserResponse userResponse = new UserResponse();
                userResponse.setId(savedUser.getId());
                userResponse.setEmail(savedUser.getEmail());
                userResponse.setFirstName(savedUser.getFirstName());
                userResponse.setLastName(savedUser.getLastName());
                userResponse.setCreatedAt(savedUser.getCreatedAt());
                userResponse.setUpdatedAt(savedUser.getUpdatedAt());

                return userResponse;
            }

            public UserResponse getUserProfile(String id)
            {
                User user = userRepository.findById((id)).orElseThrow(()-> new RuntimeException(("User not found")));

                UserResponse userResponse = new UserResponse();
                userResponse.setId(user.getId());
                userResponse.setEmail(user.getEmail());
                userResponse.setFirstName(user.getFirstName());
                userResponse.setLastName(user.getLastName());
                userResponse.setCreatedAt(user.getCreatedAt());
                userResponse.setUpdatedAt(user.getUpdatedAt());
                return  userResponse;

            }
}
