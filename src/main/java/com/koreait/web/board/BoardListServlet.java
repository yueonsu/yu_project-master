package com.koreait.web.board;

import com.koreait.web.Utils;
import com.koreait.web.board.model.BoardDTO;
import com.koreait.web.dao.BoardDAO;
import com.koreait.web.dao.UserDAO;
import com.koreait.web.user.model.ResultLogin;
import com.koreait.web.user.model.UserEntity;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/board/list")
public class BoardListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        int page = Utils.getParameterInt(req, "page", 1);
        int searchType = Utils.getParameterInt(req, "searchType", 0);
        String searchText = req.getParameter("searchText");
        int rowCnt = Utils.getParameterInt(req, "rowCnt", 5);

        BoardDTO param = new BoardDTO();
        param.setPage(page);
        param.setRowCnt(rowCnt);
        param.setIdx((page-1) * param.getRowCnt());
        param.setSearchText(searchText);
        param.setSearchType(searchType);

        req.setAttribute("maxPage", BoardDAO.getMaxPage(param));
        req.setAttribute("hotList", BoardDAO.selBoardHotList());

        req.setAttribute("list", BoardDAO.selBoardList(param));
        Utils.displayView("게시판", "board/list", req, res);
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
