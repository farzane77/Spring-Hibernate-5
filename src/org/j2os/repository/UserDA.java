package org.j2os.repository;

import org.j2os.common.InvalidUsernameOrPassword;
import org.j2os.entity.Role;
import org.j2os.entity.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserDA {
    public static void main(String[] args) throws Exception {
        UserDA userDA = new UserDA();
        List<Role> roleList = userDA.selectByUsernameAndPassword(new User().setUsername("Raznahan").setPassword("Myjava123"));
        roleList.stream().map((role) -> role.getRoleName()).forEach(System.out::println);
    }

    private UserDA(){}
    private static final UserDA USER_DA = new UserDA();

    public static UserDA getInstance() {
        return USER_DA;
    }
    public List<Role> selectByUsernameAndPassword(User user) throws Exception {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "amirsam", "myjava123");
        PreparedStatement preparedStatement = connection.prepareStatement("select * from users where upper (username) = upper (?) and password= ?");
        preparedStatement.setString(1, user.getUsername());
        preparedStatement.setString(2,user.getPassword());
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            preparedStatement = connection.prepareStatement("select * from roles where upper(username)=upper (?)");
            preparedStatement.setString(1, user.getUsername());
            resultSet = preparedStatement.executeQuery();
            List<Role> roleList = new ArrayList<>();
            while (resultSet.next()) {
                Role role = new Role().setRoleName(resultSet.getString("role_name"));
                roleList.add(role);
            }
            preparedStatement.close();
            connection.close();
            return roleList;
        }
        preparedStatement.close();
        connection.close();
        throw new InvalidUsernameOrPassword();
    }
}
