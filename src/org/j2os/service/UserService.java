package org.j2os.service;

import org.j2os.entity.Role;
import org.j2os.entity.User;
import org.j2os.repository.UserDA;

import java.util.List;

public class UserService {
    private static final UserService USER_SERVICE = new UserService();

    public static UserService getInstance() {
        return USER_SERVICE;
    }
    private UserService(){}

    public List<Role> login(User user)throws Exception
    {
        return UserDA.getInstance().selectByUsernameAndPassword(user);
    }
}
