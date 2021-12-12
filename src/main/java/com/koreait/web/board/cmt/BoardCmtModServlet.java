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

@WebServlet("/board/cmt/mod")
public class BoardCmtModServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        int icmt = Utils.getParameterInt(req, "icmt");
        int writer = Utils.getLoginUserPk(req);
        int iboard = Utils.getParameterInt(req, "iboard");
        String ctnt = req.getParameter("ctnt");

        BoardCmtEntity entity = new BoardCmtEntity();
        entity.setIcmt(icmt);
        entity.setWriter(writer);
        entity.setCtnt(ctnt);

        int result = BoardCmtDAO.updCmtBoard(entity);

        switch (result) {
            case 1:

                break;
            default:
                req.setAttribute("err", "댓글 수정에 실패하였습니다.");
                break;
        }
        res.sendRedirect("/board/detail?iboard=" + iboard);
    }
}