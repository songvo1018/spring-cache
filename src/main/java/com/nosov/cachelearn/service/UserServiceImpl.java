package com.nosov.cachelearn.service;

import com.nosov.cachelearn.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.nosov.cachelearn.domain.User;

import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    public UserServiceImpl (UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User create(User user) {
        return repository.save(user);
    }

    @Override
    public User createOrReturnCached(User user) {
        log.info("creating user: {}", user);
        return repository.save(user);
    }

    @Override
    public User createAndRefreshCache(User user) {
        log.info("creating user: {}", user);
        return repository.save(user);
    }

    @Override
    public User create(String name, String email) {
        log.info("creating user with parameters: {}, {}", name, email);
        return repository.save(new User(name, email));
    }

    @Override
    public User get(Long id) {
        log.info("getting user by id: {}", id);
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found by id" + id));
    }

    @Override
    public List<User> getAll() {
        return repository.findAll();
    }

    @Override
    public void delete(Long id) {
        log.info("deleting user by id: {}", id);
        repository.deleteById(id);
    }

    @Override
    public void deleteAndEvict(Long id) {
        log.info("deleting user by id: {}", id);
        repository.deleteById(id);
    }
}
