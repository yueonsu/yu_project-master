package com.koreait.web.dao;

import com.koreait.web.DbUtils;
import com.koreait.web.user.model.ResultLogin;
import com.koreait.web.user.model.UserEntity;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {
    public static UserEntity userInfo(UserEntity param) {
        UserEntity vo = null;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT upw, uid, nm, rdt, gender" +
                " FROM t_user WHERE iuser = ?";
        try {
            con = DbUtils.getCon();
            ps = con.prepareStatement(sql);
            ps.setInt(1, param.getIuser());
            rs = ps.executeQuery();
            if(rs.next()) {
                vo = new UserEntity();
                vo.setUpw(rs.getString("upw"));
                vo.setUid(rs.getString("uid"));
                vo.setNm(rs.getString("nm"));
                vo.setRdt(rs.getString("rdt"));
                vo.setGender(rs.getInt("gender"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.close(con, ps, rs);
        }
        return vo;
    }

    public static int Join(UserEntity entity) {
        Connection con = null;
        PreparedStatement ps = null;
        String sql = "INSERT INTO t_user (uid, upw, nm, gender)" +
                " VALUES (?, ?, ?, ?) ";
        try {
            con = DbUtils.getCon();
            ps = con.prepareStatement(sql);
            ps.setString(1, entity.getUid());
            ps.setString(2, entity.getUpw());
            ps.setString(3, entity.getNm());
            ps.setInt(4, entity.getGender());
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.close(con, ps);
        }
        return 0;
    }
    public static ResultLogin Login(UserEntity entity) {
        int result = 0;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT upw, nm, gender, iuser FROM t_user WHERE uid = ? ";
        try {
            con = DbUtils.getCon();
            ps = con.prepareStatement(sql);
            ps.setString(1, entity.getUid());
            rs = ps.executeQuery();
            if(rs.next()) {
                String dbPw = rs.getString("upw");
                if(BCrypt.checkpw(entity.getUpw(), dbPw)) {
                    result = 1;
                    entity.setIuser(rs.getInt("iuser"));
                    entity.setGender(rs.getInt("gender"));
                    entity.setNm(rs.getString("nm"));
                } else {
                    result = 3;
                }
            } else {
                result = 2;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.close(con, ps, rs);
        }
        return new ResultLogin(result, entity);
    }
}
