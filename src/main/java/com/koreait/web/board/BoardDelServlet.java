package com.koreait.web.board;

import com.koreait.web.Utils;
import com.koreait.web.board.model.BoardEntity;
import com.koreait.web.dao.BoardDAO;
import com.koreait.web.user.model.UserEntity;
import org.apache.catalina.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/board/del")
public class BoardDelServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        int iboard = Utils.getParameterInt(req, "iboard");
        int writer = Utils.getLoginUserPk(req);
        if(Utils.getLoginUser(req) == null ) {
            res.sendRedirect("/user/login");
            return;
        }
        BoardEntity entity = new BoardEntity();
        entity.setIboard(iboard);
        entity.setWriter(writer);

        int result = BoardDAO.delBoard(entity);

        switch (result) {
            case 1:
                res.sendRedirect("/board/list");
                break;
            default:
                res.sendRedirect("/board/detail?iboard=" + iboard);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

    }
}