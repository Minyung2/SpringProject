package com.springproject.study.user;

import com.springproject.study.utils.CommonQueryDSL;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final CommonQueryDSL commonQueryDSL;
    private final UserRepository userRepository;

    public List<User> findAllUsers() {
        return commonQueryDSL.selectList(QUser.user);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }
}
