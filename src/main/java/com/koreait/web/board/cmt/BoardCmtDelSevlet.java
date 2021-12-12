package com.koreait.web.board.cmt;

import com.koreait.web.Utils;
import com.koreait.web.board.model.BoardCmtEntity;
import com.koreait.web.dao.BoardCmtDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/board/cmt/del")
public class BoardCmtDelSevlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        int icmt = Utils.getParameterInt(req, "icmt");
        int iboard = Utils.getParameterInt(req, "iboard");
        int writer = Utils.getLoginUserPk(req);

        BoardCmtEntity entity = new BoardCmtEntity();
        entity.setIcmt(icmt);
        entity.setWriter(Utils.getLoginUserPk(req));

        int result = BoardCmtDAO.delCmtBoard(entity);

        switch (result) {
            case 1:
                res.sendRedirect("/board/detail?iboard="+ iboard);
                break;
            default:
                res.sendRedirect("/user/logout");
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

    }
}