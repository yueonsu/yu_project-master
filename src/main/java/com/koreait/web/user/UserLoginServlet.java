package com.koreait.web.user;

import com.koreait.web.Utils;
import com.koreait.web.dao.UserDAO;
import com.koreait.web.user.model.ResultLogin;
import com.koreait.web.user.model.UserEntity;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/user/login")
public class UserLoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        Utils.displayView("Login", "user/login", req, res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String uid = req.getParameter("uid");
        String upw = req.getParameter("upw");

        UserEntity entity = new UserEntity();
        entity.setUid(uid);
        entity.setUpw(upw);

        ResultLogin loginUser = UserDAO.Login(entity);
        if(loginUser.getResult() == 1) {
            HttpSession hs = req.getSession();
            hs.setAttribute("loginUser", loginUser.getLoginUser());
            res.sendRedirect("/board/list");
            return;
        }

        String err = "";
        switch (loginUser.getResult()) {
            case 2:
                err = "아이디가 올바르지 않습니다.";
                break;
            case 3:
                err = "비밀번호를 확인해 주세요.";
                break;
            case 0:
                err = "로그인에 실패하였습니다.";
                break;
        }
        req.setAttribute("err", err);
        doGet(req, res);
    }
}
