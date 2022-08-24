package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import VO.*;
import CM.ConnectionManager;

public class dao {
    public ArrayList<BookVO> selectBookData() throws SQLException {
        ArrayList<BookVO> list;
        list = new ArrayList<>();

        // 컨택션 정보
        Connection con = ConnectionManager.getConnetion();

        // 쿼리 부분
        String sql = "SELECT * FROM book";
        // 특정한 쿼리만 통과 하는 전용 통로 작성.
        PreparedStatement pstmt = con.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();

        // 쿼리 처리
        BookVO vo;
        // 테이블의 데이터를 원하는 부분만큼 저장해야함.
        // VO를 이용해서 컬랙션에 저장.
        while (rs.next()) {
            vo = new BookVO(rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getInt(4),
                    rs.getString(5));
            list.add((vo));
        }

        ConnectionManager.closeConnection(rs, pstmt, con);

        return list;
    }

    public ArrayList<StudentVO> selectStudentData() throws SQLException {
        ArrayList<StudentVO> list;
        list = new ArrayList<>();

        // 컨택션 정보
        Connection con = ConnectionManager.getConnetion();

        // 쿼리 부분
        String sql = "SELECT * FROM student";
        // 특정한 쿼리만 통과 하는 전용 통로 작성.
        PreparedStatement pstmt = con.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();

        // 쿼리 처리
        StudentVO vo;
        // 테이블의 데이터를 원하는 부분만큼 저장해야함.
        // VO를 이용해서 컬랙션에 저장.
        while (rs.next()) {
            vo = new StudentVO(rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getInt(4));
            list.add((vo));
        }

        ConnectionManager.closeConnection(rs, pstmt, con);

        return list;
    }
}
