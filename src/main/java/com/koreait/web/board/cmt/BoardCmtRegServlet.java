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

@WebServlet("/board/cmt/reg")
public class BoardCmtRegServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String ctnt = req.getParameter("ctnt");
        int writer = Utils.getLoginUserPk(req, 0);
        int iboard = Utils.getParameterInt(req, "iboard");

        BoardCmtEntity entity = new BoardCmtEntity();
        entity.setCtnt(ctnt);
        entity.setWriter(writer);
        entity.setIboard(iboard);

        int result = BoardCmtDAO.insCmtBoard(entity);

        res.sendRedirect("/board/detail?iboard=" + iboard);
    }
}