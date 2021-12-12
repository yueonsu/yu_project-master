package com.koreait.web.user.model;

import com.koreait.web.Utils;
import com.koreait.web.dao.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user/info")
public class UserInfoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        int writer = Utils.getParameterInt(req, "writer");

        UserEntity param = new UserEntity();
        param.setIuser(writer);

        UserEntity entity = UserDAO.userInfo(param);
        if(Utils.getLoginUser(req) != null) {
            if (Utils.getLoginUserPk(req) != writer) {
                entity.setUpw(null);
            }
        } else {
            entity.setUpw(null);
        }
        req.setAttribute("userData", entity);
        Utils.displayView("회원정보", "user/info", req, res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

    }
}