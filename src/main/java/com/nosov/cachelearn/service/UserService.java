package com.nosov.cachelearn.service;

import com.nosov.cachelearn.domain.User;

import java.util.List;

public interface UserService {
    User create(User user);

    User createOrReturnCached(User user);

    User createAndRefreshCache(User user);

    User create(String name, String email);

    User get(Long id);

    List<User> getAll();

    void delete(Long id);

    void deleteAndEvict(Long id);
}
