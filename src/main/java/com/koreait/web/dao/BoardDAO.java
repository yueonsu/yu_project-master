package com.koreait.web.dao;

import com.koreait.web.DbUtils;
import com.koreait.web.board.model.BoardDTO;
import com.koreait.web.board.model.BoardEntity;
import com.koreait.web.board.model.BoardVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BoardDAO {
    public static void upHits(BoardDTO param) {
        Connection con = null;
        PreparedStatement ps = null;
        String sql = " UPDATE t_board SET hit=hit+1 WHERE iboard=?";
        try {
            con = DbUtils.getCon();
            ps = con.prepareStatement(sql);
            ps.setInt(1, param.getIboard());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.close(con, ps);
        }
    }

    public static int getWriter(BoardDTO param) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT writer FROM t_board WHERE iboard = ?";
        try {
            con = DbUtils.getCon();
            ps = con.prepareStatement(sql);
            ps.setInt(1, param.getIboard());
            rs = ps.executeQuery();
            if(rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.close(con, ps, rs);
        }
        return 0;
    }

    public static int insBoardWithPk(BoardEntity entity) {
        int result = 0;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "INSERT INTO t_board (title, ctnt, writer)" +
                " VALUES (?, ?, ?) ";
        try {
            con = DbUtils.getCon();
            ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, entity.getTitle());
            ps.setString(2, entity.getCtnt());
            ps.setInt(3, entity.getWriter());
            result = ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int iboard = rs.getInt(1);
                entity.setIboard(iboard);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.close(con, ps);
        }
        return result;
    }

    public static String getSearchType(BoardDTO param) {
        if (param.getSearchText() != null || !"".equals(param.getSearchType())) {
            switch (param.getSearchType()) {
                case 1:
                    return String.format("WHERE A.title LIKE '%%%s%%' OR A.ctnt LIKE '%%%s%%' OR B.nm LIKE '%%%s%%'", param.getSearchText(), param.getSearchText(), param.getSearchText());
                case 2:
                    return String.format("WHERE A.title LIKE '%%%s%%'", param.getSearchText());
                case 3:
                    return String.format("WHERE A.ctnt LIKE '%%%s%%'", param.getSearchText());
                case 4:
                    return String.format("WHERE B.nm LIKE '%%%s%%'", param.getSearchText());
            }
        }
        return "";
    }

    public static int getMaxPage(BoardDTO param) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT CEIL(COUNT(*) / ?) FROM t_board A" +
                " INNER JOIN t_user B ON A.writer = B.iuser ";
        sql += getSearchType(param);
        try {
            con = DbUtils.getCon();
            ps = con.prepareStatement(sql);
            ps.setInt(1, param.getRowCnt());
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.close(con, ps, rs);
        }
        return 0;
    }

    public static List<BoardVO> selBoardHotList() {
        List<BoardVO> list = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT A.iboard, A.title, A.writer, A.hit, A.rdt, B.nm AS writerNm " +
                " FROM t_board A" +
                " INNER JOIN t_user B" +
                " ON A.writer = B.iuser " +
                " ORDER BY A.hit DESC " +
                " LIMIT 10 ";
        try {
            con = DbUtils.getCon();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()) {
                BoardVO vo = BoardVO.builder()
                        .iboard(rs.getInt("iboard"))
                        .title(rs.getString("title"))
                        .writer(rs.getInt("writer"))
                        .hit(rs.getInt("hit"))
                        .rdt(rs.getString("rdt"))
                        .writerNm(rs.getString("writerNm"))
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

    public static BoardVO selDetailBoardList(BoardDTO param) {
        BoardVO vo = null;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT A.ctnt, A.iboard, A.title, A.writer, A.hit, A.rdt, B.nm AS writerNm " +
                " FROM t_board A" +
                " INNER JOIN t_user B" +
                " ON A.writer = B.iuser" +
                " WHERE iboard = ? ";
        try {
            con = DbUtils.getCon();
            ps = con.prepareStatement(sql);
            ps.setInt(1, param.getIboard());
            rs = ps.executeQuery();
            while (rs.next()) {
                vo = BoardVO.builder()
                        .ctnt(rs.getString("ctnt"))
                        .iboard(rs.getInt("iboard"))
                        .title(rs.getString("title"))
                        .writer(rs.getInt("writer"))
                        .hit(rs.getInt("hit"))
                        .rdt(rs.getString("rdt"))
                        .writerNm(rs.getString("writerNm"))
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.close(con, ps, rs);
        }
        return vo;
    }

    public static List<BoardVO> selBoardList(BoardDTO param) {
        List<BoardVO> list = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT A.iboard, A.title, A.writer, A.hit, A.rdt, B.nm AS writerNm " +
                " FROM t_board A" +
                " INNER JOIN t_user B" +
                " ON A.writer = B.iuser ";
        sql += getSearchType(param);
        sql += " ORDER BY iboard DESC " +
                " LIMIT ?, ?";
        try {
            con = DbUtils.getCon();
            ps = con.prepareStatement(sql);
            ps.setInt(1, param.getIdx());
            ps.setInt(2, param.getRowCnt());
            rs = ps.executeQuery();
            while (rs.next()) {
                BoardVO vo = BoardVO.builder()
                        .iboard(rs.getInt("iboard"))
                        .title(rs.getString("title"))
                        .writer(rs.getInt("writer"))
                        .hit(rs.getInt("hit"))
                        .rdt(rs.getString("rdt"))
                        .writerNm(rs.getString("writerNm"))
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

    public static int updBoard(BoardEntity entity) {
        Connection con = null;
        PreparedStatement ps = null;
        String sql = "UPDATE t_board SET title = ?, ctnt = ? WHERE iboard = ? AND writer = ? ";
        try {
            con = DbUtils.getCon();
            ps = con.prepareStatement(sql);
            ps.setString(1, entity.getTitle());
            ps.setString(2, entity.getCtnt());
            ps.setInt(3, entity.getIboard());
            ps.setInt(4, entity.getWriter());
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.close(con, ps);
        }
        return 0;
    }

    public static int delBoard(BoardEntity entity) {
        Connection con = null;
        PreparedStatement ps = null;
        String sql = "DELETE FROM t_board WHERE iboard= ? AND writer = ? ";
        try {
            con = DbUtils.getCon();
            ps = con.prepareStatement(sql);
            ps.setInt(1, entity.getIboard());
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
