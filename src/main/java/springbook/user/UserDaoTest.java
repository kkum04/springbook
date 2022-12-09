package springbook.user;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import springbook.user.dao.DaoFactory;
import springbook.user.dao.UserDao;
import springbook.user.domain.User;

import java.sql.SQLException;

public class UserDaoTest {
  public static void main(String[] args) throws ClassNotFoundException, SQLException {
    //ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
    ApplicationContext context = new GenericXmlApplicationContext("applicationContext.xml");

    UserDao dao = context.getBean("userDao", UserDao.class);

    User user = new User();
    user.setId("kkum04");
    user.setName("박태환");
    user.setPassword("married");

    dao.add(user);

    System.out.println(user.getId() + " 등록");

    User user2 = dao.get(user.getId());
    System.out.println(user2.getName());
    System.out.println(user2.getPassword());

    System.out.println(user2.getId() + " 조회 성공");
  }
}
