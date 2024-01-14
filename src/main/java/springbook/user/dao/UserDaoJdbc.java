package springbook.user.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import springbook.user.domain.Level;
import springbook.user.domain.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class UserDaoJdbc implements UserDao {
    private JdbcTemplate jdbcTemplate;
    private String sqlAdd;
    private String sqlGet;
    private String sqlGetAll;
    private String sqlDeleteAll;
    private String sqlGetCount;
    private String sqlUpdate;
    public void setSqlGetAll(String sqlGetAll) {
        this.sqlGetAll = sqlGetAll;
    }

    public void setSqlAdd(String sqlAdd) {
        this.sqlAdd = sqlAdd;
    }

    public void setSqlGet(String sqlGet) {
        this.sqlGet = sqlGet;
    }

    public void setSqlDeleteAll(String sqlDeleteAll) {
        this.sqlDeleteAll = sqlDeleteAll;
    }

    public void setSqlGetCount(String sqlGetCount) {
        this.sqlGetCount = sqlGetCount;
    }

    public void setSqlUpdate(String sqlUpdate) {
        this.sqlUpdate = sqlUpdate;
    }

    private RowMapper<User> userMapper = new RowMapper<User>() {
        @Override
        public User mapRow(ResultSet resultSet, int i) throws SQLException {
            User user = new User();
            user.setId(resultSet.getString("id"));
            user.setName(resultSet.getString("name"));
            user.setPassword(resultSet.getString("password"));
            user.setEmail(resultSet.getString("email"));
            user.setLevel(Level.valueOf(resultSet.getInt("level")));
            user.setLogin(resultSet.getInt("login"));
            user.setRecommend(resultSet.getInt("recommend"));
            return user;
        }
    };

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void add(final User user) {
        this.jdbcTemplate.update(
            this.sqlAdd,
            user.getId(), user.getName(), user.getPassword(), user.getEmail(),
            user.getLevel().intValue(), user.getLogin(), user.getRecommend()
        );
    }

    public void deleteAll() {
        jdbcTemplate.update(this.sqlDeleteAll);
    }

    public User get(String id) {
        return this.jdbcTemplate.queryForObject(
            this.sqlGet,
            new Object[]{id},
            userMapper
        );
    }

    public List<User> getAll() {
        return this.jdbcTemplate.query(
            this.sqlGetAll,
            userMapper
        );
    }

    public int getCount() {
        return this.jdbcTemplate.queryForInt(this.sqlGetCount);
    }

    public void update(User user1) {
        this.jdbcTemplate.update(
            this.sqlUpdate, user1.getName(), user1.getPassword(), user1.getEmail(), user1.getLevel().intValue(), user1.getLogin(), user1.getRecommend(), user1.getId()
        );
    }
}
