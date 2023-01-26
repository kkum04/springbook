package springbook.user.dao;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import springbook.user.DuplicateUserIdException;
import springbook.user.domain.User;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
  private JdbcTemplate jdbcTemplate;
  private RowMapper<User> rowMapper = new RowMapper<User>() {
    @Override
    public User mapRow(ResultSet rs, int i) throws SQLException {
      User user = new User();
      user.setId(rs.getString("id"));
      user.setName(rs.getString("name"));
      user.setPassword(rs.getString("password"));
      return user;
    }
  };

  public void setDataSource(DataSource dataSource) {
    this.jdbcTemplate = new JdbcTemplate(dataSource);
  }

  public void add(User user) throws DuplicateUserIdException {
    jdbcTemplate.update("insert into users(id, name, password) values(?, ?, ?)",
        user.getId(), user.getName(), user.getPassword()
    );
  }

  public User get(String id) {
    return jdbcTemplate.queryForObject(
        "select * from users where id = ?",
        new Object[]{id},
        this.rowMapper
    );
  }

  public List<User> getAll() {
    return this.jdbcTemplate.query(
        "select * from users order by id",
        this.rowMapper
    );
  }

  public void deleteAll() {
    jdbcTemplate.update("delete from users");
  }

  public int getCount() {
    return jdbcTemplate.queryForInt("select count(*) from users");
  }
}
