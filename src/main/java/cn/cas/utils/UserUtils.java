package cn.cas.utils;

import cn.cas.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserUtils {

    public static User findUser(String username) {
        // JDBC模板依赖于连接池来获得数据库的连接，所以必须先要构造连接池
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://192.168.64.33:3306/vsdb?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&useSSL=false");
        dataSource.setUsername("root");
        dataSource.setPassword("root");

        System.out.println("dataSource ready");

        // 创建JDBC模板
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource);

        System.out.println("template ready");

        String sql = "select * from sp_manager where mg_name=?";
        Object[] params = {username};

        User user = jdbcTemplate.queryForObject(sql, params, new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet resultSet, int i) throws SQLException {
                User u = new User();
                try {
                    u.setUsername(resultSet.getString("mg_name"));
                    u.setPassword(resultSet.getString("mg_pwd"));
                    u.setExpired(resultSet.getInt("mg_expired"));
                } catch (Exception ignored) {
                }
                return u;
            }
        });
        return user;
    }

}
