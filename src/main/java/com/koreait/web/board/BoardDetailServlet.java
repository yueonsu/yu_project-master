package com.koreait.web.board;

import com.koreait.web.Utils;
import com.koreait.web.board.model.BoardVO;
import com.koreait.web.dao.BoardCmtDAO;
import com.koreait.web.dao.BoardDAO;
import com.koreait.web.board.model.BoardDTO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/board/detail")
public class BoardDetailServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        int iboard = Utils.getParameterInt(req, "iboard");
        int iuser = Utils.getLoginUserPk(req, 0);

        BoardDTO param = new BoardDTO();
        param.setIboard(iboard);

        int writer = BoardDAO.getWriter(param);
        if(iuser != writer) {
            BoardDAO.upHits(param);
        }

        //댓글 리스트
        req.setAttribute("cmtData", BoardCmtDAO.selBoardCmtList(param));
        //댓글 개수

        BoardVO vo = BoardDAO.selDetailBoardList(param);
        req.setAttribute("detailData", vo);
        Utils.displayView(vo.getTitle(), "board/detail", req, res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

    }
}
