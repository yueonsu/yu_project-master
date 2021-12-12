package com.koreait.web.dao;

import com.koreait.web.DbUtils;
import com.koreait.web.board.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BoardCmtDAO {
    public static int insCmtBoard(BoardCmtEntity entity) {
        Connection con = null;
        PreparedStatement ps = null;
        String sql = "INSERT INTO t_board_cmt (ctnt, writer, iboard) VALUES (?, ?, ?) ";
        try {
            con = DbUtils.getCon();
            ps = con.prepareStatement(sql);
            ps.setString(1, entity.getCtnt());
            ps.setInt(2, entity.getWriter());
            ps.setInt(3, entity.getIboard());
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.close(con, ps);
        }
        return 0;
    }

    public static List<BoardCmtVO> selBoardCmtList(BoardDTO param) {
        List<BoardCmtVO> list = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT A.icmt, A.ctnt, A.rdt, A.writer, B.nm AS writerNm" +
                    " FROM t_board_cmt A" +
                    " INNER JOIN t_user B" +
                    " ON A.writer = B.iuser" +
                    " WHERE iboard = ?" +
                    " ORDER BY icmt DESC ";
        try {
            con = DbUtils.getCon();
            ps = con.prepareStatement(sql);
            ps.setInt(1, param.getIboard());
            rs = ps.executeQuery();
            while(rs.next()) {
                BoardCmtVO vo = BoardCmtVO.builder()
                        .icmt(rs.getInt("icmt"))
                        .ctnt(rs.getString("ctnt"))
                        .rdt(rs.getString("rdt"))
                        .writerNm(rs.getString("writerNm"))
                        .writer(rs.getInt("writer"))
                        .build();
                list.add(vo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.close(con, ps, rs);
        }
        return list;
    }

    public static int updCmtBoard(BoardCmtEntity entity) {
        Connection con = null;
        PreparedStatement ps = null;
        String sql = "UPDATE t_board_cmt SET ctnt=? WHERE writer = ? AND icmt = ?";
        try {
            con = DbUtils.getCon();
            ps = con.prepareStatement(sql);
            ps.setString(1, entity.getCtnt());
            ps.setInt(2, entity.getWriter());
            ps.setInt(3, entity.getIcmt());
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.close(con, ps);
        }
        return 0;
    }

    public static int delCmtBoard(BoardCmtEntity entity) {
        Connection con = null;
        PreparedStatement ps = null;
        String sql = "DELETE FROM t_board_cmt WHERE icmt=? AND writer = ? ";
        try {
            con = DbUtils.getCon();
            ps = con.prepareStatement(sql);
            ps.setInt(1, entity.getIcmt());
            ps.setInt(2, entity.getWriter());
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.close(con, ps);
        }
        return 0;
    }
}
