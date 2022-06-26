package com.nosov.cachelearn;

import com.nosov.cachelearn.service.UserService;
import com.nosov.cachelearn.domain.User;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CacheLearnApplicationTests {

//    @Test
//    void contextLoads() {
//    }
    private Logger log = LoggerFactory.getLogger(CacheLearnApplicationTests.class);

    @Autowired
    private UserService service;

    @Test
    public void get() {
        separateTestStart();
        User user1 = service.create(new User("Vasya", "vasya@mail.ru"));
        User user2 = service.create(new User("Kolya", "kolya@mail.ru"));

        getAndPrint(user1.getId());
        getAndPrint(user2.getId());
        getAndPrint(user1.getId());
        getAndPrint(user2.getId());

        separateTestEnd();
    }

    @Test
    public void create() {
        separateTestStart();
        createAndPrint("Ivan", "bombey@asdwe.we");
        createAndPrint("Lin", "berlin@asdwe.we");
        createAndPrint("Lin", "moscow@asdqwewqeqweqewqe.we");
        createAndPrint("Karla", "moscow@asdqwewqeqweqewqe.we");

        System.err.println("all entries are below:");
        service.getAll().forEach(user -> System.out.println(user.toString()));
        separateTestEnd();
    }

    @Test
    public void createAndRefresh() {
        separateTestStart();

        User user1 = service.createOrReturnCached(new User("Vasya", "lomov@mail.com"));
        System.err.println("created user" + user1);

        User user2 = service.createOrReturnCached(new User("Vasya", "berlin_my_home@mail.com"));
        System.err.println("created user" + user2);

        User user3 = service.createAndRefreshCache(new User("Vasya", "baba@mail.com"));
        System.err.println("created user" + user3);

        User user4 = service.createOrReturnCached(new User("Vasya", "ProbakerProbaker@mail.com"));
        System.err.println("created user" + user4);

        separateTestEnd();
    }

    @Test
    public void delete() {
        separateTestStart();

        User user1 = service.create(new User("Vasya", "lomov@mail.com"));
        System.err.println("created user " + service.get(user1.getId()));

        User user2 = service.create(new User("Vasya", "berlin_my_home@mail.com"));
        System.err.println("created user " + service.get(user2.getId()));

        service.delete(user1.getId());
        service.deleteAndEvict(user2.getId());

        System.err.println("created user " + service.get(user1.getId()));
        System.err.println("created user " + service.get(user2.getId()));

        separateTestEnd();
    }

    @Test
    public void checkSettings() throws InterruptedException {
        separateTestStart();

        User user1 = service.createOrReturnCached(new User("Vasya", "lomov@mail.com"));
        System.err.println("created user " + service.get(user1.getId()));

        User user2 = service.createOrReturnCached(new User("Vasya", "wwwww@mail.com"));
        System.err.println("created user " + service.get(user2.getId()));

        Thread.sleep(1000L);

        User user3 = service.create(new User("Vasya", "berlin_my_home@mail.com"));
        System.err.println("created user " + service.get(user3.getId()));

        separateTestEnd();
    }

    private void separateTestStart() {
        System.err.println("============================");
    }
    private void separateTestEnd() {
        System.err.println("-----------------------------");
        System.err.println("");
    }

    private void createAndPrint(String name, String email) {
        System.err.println("created user: " + service.create(name, email));
    }

    private void getAndPrint(Long id) {
        System.err.println("user found: " + service.get(id));
    }
}
