package org.j2os.controller;

import org.j2os.entity.Role;
import org.j2os.entity.User;
import org.j2os.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/login.do")
public class Login extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<Role> roleList = UserService.getInstance().login(new User().setUsername(req.getParameter("username")).setPassword(req.getParameter("password")));
            req.getSession().setAttribute("roles", roleList);
            resp.sendRedirect("/" + roleList.get(0).getRoleName() + "/index.jsp");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
