package springbook.user.service;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import springbook.user.dao.UserDao;
import springbook.user.domain.Level;
import springbook.user.domain.User;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/test-applicationContext.xml")
public class UserServiceTest extends TestCase {
    @Autowired
    UserService userService;

    @Autowired
    UserDao userDao;

    List<User> users;

    @Test
    public void bean() {
        assertThat(this.userService, is(notNullValue()));
    }

    @Test
    public void upgradeLevels() {
        userDao.deleteAll();
        for (User user : users) userDao.add(user);

        userService.upgradeLevels();

        checkLevel(users.get(0), Level.BASIC);
        checkLevel(users.get(1), Level.SILVER);
        checkLevel(users.get(2), Level.SILVER);
        checkLevel(users.get(3), Level.GOLD);
        checkLevel(users.get(4), Level.GOLD);
    }

    private void checkLevel(User user, Level expectedLevel) {
        User userUpdate = userDao.get(user.getId());
        assertThat(userUpdate.getLevel(), is(expectedLevel));
    }

    @Before
    public void setUp() {
        users = Arrays.asList(
            new User("bumjin", "bumkjin", "p1", Level.BASIC, 49, 0),
            new User("joytouch", "joytouch", "p2", Level.BASIC, 50, 0),
            new User("erwins", "erwins", "p3", Level.SILVER, 60, 29),
            new User("madnite1", "madnite1", "p4", Level.SILVER, 60, 30),
            new User("green", "green", "p5", Level.GOLD, 100, 100)
        );
    }
}