package com.genival.home.broker.services;


import com.genival.home.broker.dto.user.UserMinDTO;
import com.genival.home.broker.entities.User;
import com.genival.home.broker.exception.BadRequestException;
import com.genival.home.broker.repositories.UserRepositories;
import com.genival.home.broker.requests.UserPostRequestBody;
import com.genival.home.broker.utils.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class UserServices {
    @Autowired
    private final UserRepositories userRepositories;
    @Autowired
    private final DateUtil dateUtil;

    @Transactional(readOnly = true)
    public Page<UserMinDTO> listAll(Long id, Pageable pageable){
        Optional<User> optionalUser = userRepositories.findById(id);
        boolean isValidUser = optionalUser.filter(user -> isValidUserTypeADMIN(user.getType())).isPresent();

        if (isValidUser) {
            Page<User> userList = userRepositories.findAll(pageable);

            return (Page<UserMinDTO>) userList.stream().map(UserMinDTO::new);
        } else {
            String errorMessage = "User is not authorized";
            log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()) + " " + errorMessage);
            throw new BadRequestException(errorMessage);
        }
    }

    @Transactional(readOnly = true)
    public List<UserMinDTO> listAllNonPageable(Long id){
        Optional<User> optionalUser = userRepositories.findById(id);
        boolean isValidUser = optionalUser.filter(user -> isValidUserTypeADMIN(user.getType())).isPresent();

        if (isValidUser) {
            List<User> userList = userRepositories.findAll();
            return userList.stream().map(UserMinDTO::new).collect(Collectors.toList());
        } else {
            String errorMessage = "User is not authorized";
            log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()) + " " + errorMessage);
            throw new BadRequestException(errorMessage);
        }
    }

    @Transactional(readOnly = true)
    public List<UserMinDTO> findById(Long id){
        Optional<User> optionalUser = userRepositories.findById(id);
        boolean isValidUser = optionalUser.filter(user -> isValidUserTypeADMIN(user.getType())).isPresent();

        if (isValidUser){
            return optionalUser.map(UserMinDTO::new).stream().collect(Collectors.toList());
        } else {
            String errorMessage = "User is not authorized";
            log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()) + " " + errorMessage);
            throw new BadRequestException(errorMessage);
        }
    }


    @Transactional
    public UserMinDTO save(UserPostRequestBody user) {
        String userType = user.getType();
        if (userType == null || !(isValidUserType(userType))) {
            String errorMessage = "Type user is not valid: " + userType;
            log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()) + " " + errorMessage);
            throw new BadRequestException(errorMessage);
        }
        LocalDateTime currentTime = LocalDateTime.now();
        try {
            User newUser = new User(null, user.getYear(), user.getPassword(), user.getName(),
                    user.getEmail(),  userType,   user.getCpf(),  user.getPhone(), user.getZipCode());
            newUser = userRepositories.save(newUser);
            return new UserMinDTO(newUser);
        } catch (DataAccessException error) {
            String errorMessage = "Error in creating a User";
            log.error(dateUtil.formatLocalDateTimeToDatabaseStyle(currentTime) + " " + errorMessage, error);
            throw new BadRequestException(errorMessage);
        }
    }

    private boolean isValidUserType(String userType) {
        return userType.equals("ADMIN") || userType.equals("USER");
    }
    private boolean isValidUserTypeADMIN(String userType) {
        return userType.equals("ADMIN");
    }
}
