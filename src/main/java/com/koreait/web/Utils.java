package com.koreait.web;

import com.koreait.web.user.model.ResultLogin;
import com.koreait.web.user.model.UserEntity;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class Utils {
    public static int getParameterInt(HttpServletRequest req, String val) {
        return getParameterInt(req, val, 0);
    }
    public static int getParameterInt(HttpServletRequest req, String val, int defVal) {
        return parseStringToInt(req.getParameter(val), defVal);
    }
    public static int parseStringToInt(String strVal, int defVal) {
        try {
            return Integer.parseInt(strVal);
        } catch (Exception e) {
            return defVal;
        }
    }

    public static void displayView(String title, String page, HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.setAttribute("title", title);
        req.setAttribute("page", page);
        reqDispatcher(req, res);
    }
    public static void reqDispatcher(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/layout.jsp").forward(req, res);
    }

    public static UserEntity getLoginUser(HttpServletRequest req) {
        HttpSession hs = req.getSession();
        return (UserEntity) hs.getAttribute("loginUser");
    }

    public static int getLoginUserPk(HttpServletRequest req) {
        UserEntity entity = getLoginUser(req);
        return entity.getIuser();
    }
}
