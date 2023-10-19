package springbook.user.dao;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import springbook.user.domain.User;

import java.sql.SQLException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class UserDaoTest {
    @Test
    public void addAndGet() throws SQLException {
        ApplicationContext context = new GenericXmlApplicationContext("applicationContext.xml");
        UserDao dao = context.getBean("userDao", UserDao.class);

        dao.deleteAll();
        assertThat(dao.getCount(), is(0));

        User user = new User("kkum04", "Ken Park", "married");
        User user2 = new User("ji", "Ji Hyun", "solo");

        dao.add(user);
        dao.add(user2);
        assertThat(dao.getCount(), is(2));

        User foundUser = dao.get(user.getId());
        assertThat(foundUser.getName(), is(user.getName()));
        assertThat(foundUser.getPassword(), is(user.getPassword()));

        User foundUser2 = dao.get(user2.getId());
        assertThat(foundUser2.getName(), is(user2.getName()));
        assertThat(foundUser2.getPassword(), is(user2.getPassword()));
    }

    @Test
    public void count() throws SQLException {
        ApplicationContext context = new GenericXmlApplicationContext("applicationContext.xml");
        UserDao dao = context.getBean("userDao", UserDao.class);

        User user1 = new User("we", "we eun jung", "married");
        User user2 = new User("ji", "park ji hyun", "solo");
        User user3 = new User("sang", "park sang hyun", "solo");

        dao.deleteAll();
        assertThat(dao.getCount(), is(0));

        dao.add(user1);
        assertThat(dao.getCount(), is(1));

        dao.add(user2);
        assertThat(dao.getCount(), is(2));

        dao.add(user3);
        assertThat(dao.getCount(), is(3));
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void getUserFailure() throws SQLException {
        ApplicationContext context = new GenericXmlApplicationContext("applicationContext.xml");

        UserDao dao = context.getBean("userDao", UserDao.class);
        dao.deleteAll();
        assertThat(dao.getCount(), is(0));

        dao.get("unknown_id");
    }
}
