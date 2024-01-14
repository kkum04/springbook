package springbook.user.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import springbook.user.domain.Level;
import springbook.user.domain.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;
import java.util.Map;

public class UserDaoJdbc implements UserDao {
    private JdbcTemplate jdbcTemplate;
    private Map<String, String> sqlMap;

    public void setSqlMap(Map<String, String> sqlMap) {
        this.sqlMap = sqlMap;
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
            this.sqlMap.get("add"),
            user.getId(), user.getName(), user.getPassword(), user.getEmail(),
            user.getLevel().intValue(), user.getLogin(), user.getRecommend()
        );
    }

    public void deleteAll() {
        jdbcTemplate.update(this.sqlMap.get("deleteAll"));
    }

    public User get(String id) {
        return this.jdbcTemplate.queryForObject(
            this.sqlMap.get("get"),
            new Object[]{id},
            userMapper
        );
    }

    public List<User> getAll() {
        return this.jdbcTemplate.query(
            this.sqlMap.get("getAll"),
            userMapper
        );
    }

    public int getCount() {
        return this.jdbcTemplate.queryForInt(this.sqlMap.get("getCount"));
    }

    public void update(User user1) {
        this.jdbcTemplate.update(
            this.sqlMap.get("update"), user1.getName(), user1.getPassword(), user1.getEmail(), user1.getLevel().intValue(), user1.getLogin(), user1.getRecommend(), user1.getId()
        );
    }
}
