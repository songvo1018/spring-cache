package com.nosov.cachelearn.service;

import com.nosov.cachelearn.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.nosov.cachelearn.domain.User;

import java.util.List;

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
    @Cacheable(value = "users", key = "#user.name")
    public User createOrReturnCached(User user) {
        System.err.println("creating user: " + create(user));
        return repository.save(user);
    }

    @Override
    @CachePut(value = "users", key = "#user.name")
    public User createAndRefreshCache(User user) {
        System.err.println("creating user: " + create(user));
        return repository.save(user);
    }

    @Override
    @Cacheable(value = "users", key="#name")
    public User create(String name, String email) {
        System.err.println("creating user with parameters: " + name + email);
        return repository.save(new User(name, email));
    }

    @Override
    @Cacheable("users")
    public User get(Long id) {
        System.err.println("getting user by id: " + id);
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found by id" + id));
    }

    @Override
    public List<User> getAll() {
        return repository.findAll();
    }

    @Override
    public void delete(Long id) {
        System.err.println("deleting user by id: {}" + id);
        repository.deleteById(id);
    }

    @Override
    @CacheEvict("users")
    public void deleteAndEvict(Long id) {
        System.err.println("deleting and evicting user by id: {}" + id);
        repository.deleteById(id);
    }
}
