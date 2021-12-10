package com.koreait.web.board;

import com.koreait.web.Utils;
import com.koreait.web.board.model.BoardDTO;
import com.koreait.web.board.model.BoardEntity;
import com.koreait.web.board.model.BoardVO;
import com.koreait.web.dao.BoardDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/board/regmod")
public class BoardRegmodServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        int iboard = Utils.getParameterInt(req, "iboard", 0);
        String title = "글쓰기";

        // 로그인이 되어 있지않으면 로그인 페이지로
        if(Utils.getLoginUser(req) == null) {
            res.sendRedirect("/user/login");
            return;
        }

        // 글수정
        if(iboard > 0) {
            BoardDTO param = new BoardDTO();
            param.setIboard(iboard);
            BoardVO result = BoardDAO.selDetailBoardList(param);
            req.setAttribute("updBoard", result);
            title = "글수정";
            // 로그인 한 회원이 아닐 경우
            if(Utils.getLoginUserPk(req) != result.getWriter()) {
                res.sendRedirect("/board/detail?iboard="+iboard);
                return;
            }
        }
        Utils.displayView(title, "board/regmod", req, res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        int iboard = Utils.getParameterInt(req, "iboard", 0);
        String title = req.getParameter("title");
        String ctnt = req.getParameter("ctnt");
        int writer = Utils.getLoginUserPk(req);

        // Script 못 쓰게 막기
        title = title.replace("<", "&lt").replace(">", "&gt");
        ctnt = ctnt.replace("<", "&lt").replace(">", "&gt");

        // 글쓰기, 글수정 분기
        int result = 0;
        BoardEntity entity = new BoardEntity();
        entity.setTitle(title);
        entity.setCtnt(ctnt);
        entity.setWriter(writer);

        if(iboard == 0) {
            result = BoardDAO.insBoardWithPk(entity);
        } else {
            entity.setIboard(iboard);
            result = BoardDAO.updBoard(entity);
        }

        switch (result) {
            case 1:
                res.sendRedirect("/board/detail?iboard=" + entity.getIboard());
                break;
            default:
                req.setAttribute("data", entity);
                doGet(req, res);
                break;
        }
    }
}