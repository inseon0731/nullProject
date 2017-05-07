package com.example.hong_inseon.projectlouvre.dao;

import android.util.Log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by samsung on 2016-12-03.
 * MuseumDAO : for LikeMuseum.java (view)
 * getMuseumList()
 * @return mslist
 * getMuseumData(Museum msData)
 * @return
 *
추가할 사항 : like한 museum출력하기
//query by user no, temporary user_no = 1
//liked table : user_no, liked_exhi_no/museum_no, liked_no
//select * from liked where user_no = 1 order by desc;
 */

public class MuseumDAO {
    // 미술관 리스트 선택
    private static final String msListSQL = "select * from museum order by ms_no desc;";
    // 좋아요한 미술관 리스트 선택
    private static final String msLikeListSQL = "select * from liked order by ms_no desc;";
    // 해당하는 미술관 정보 선택
    private static final String msSelectSQL = "select * from museum where ms_no = ? ;";

    // 미술관 리스트에 각 미술관 정보 가져오기
    public ArrayList<Museum> getMuseumList(){
        Connection conn = null;
        //PreparedStatement stmt = null;
        Statement stmt = null;
        ResultSet rst = null;

        ArrayList<Museum> mslist = new ArrayList<Museum>();
        try{
            conn = JDBCUtil.getConnection();
            //하나의 접속요청에 하나의 getConnection으로 접속요청을 할 것, 종료시 커넥션 종료 (그래야 요청 섞이지 않음)

            if(conn!=null){
                stmt = conn.createStatement();//statement객체생성,
                Log.v("stmt",stmt+", stmt is null?");
                rst = stmt.executeQuery(msListSQL);
            } else{
                rst = null;
            }
            //stmt = conn.prepareStatement(msListSQL);
            //rst = stmt.executeQuery();
            Museum msData = null;
            //반환하는 것 여러개일수 있으므로 반복수행, 결과를 데이터객체에 저장
            while(rst.next()) { //rst의 마지막 까지실행
                // 리스트뷰에서 보여줄 정보만 남기기
                msData = new Museum();
                msData.setMs_no(rst.getString(1));
                msData.setMs_address(rst.getString(2));
                msData.setMs_phone(rst.getString(3));
                msData.setMs_url(rst.getString(4));
                msData.setMs_holiday(rst.getString(5));
                msData.setMs_operating(rst.getString(6));
                msData.setMs_name(rst.getString(7));
                msData.setMs_rating(rst.getString(8));
                msData.setMs_like(rst.getString(9));
                msData.setMs_Img(rst.getString(10));
                msData.setMs_exp(rst.getString(11));

                mslist.add(msData);
            }
        } catch(SQLException e){
            System.out.println("list e : " + e);
        } finally {
            //JDBCUtil.close(stmt, conn);
        }

        return mslist; // 미술관리스트 객체 반환
    }

    /**
     * @param msData
     * ms_no만 있는 미술관객체를 받아서 미술관 객체의 번호를 기준으로 값을 가져오는 함수
     */
    public void	getMuseumData(Museum msData){
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rst = null;

        try{
            conn = JDBCUtil.getConnection();
            //하나의 접속요청에 하나의 getConnection으로 접속요청을 할 것, 종료시 커넥션 종료 (그래야 요청 섞이지 않음)

            stmt = conn.prepareStatement(msSelectSQL);
            stmt.setInt(1, Integer.parseInt(msData.getMs_no()));
            rst = stmt.executeQuery();

            if(rst.next()) { //rst이 있는지 확인
                // 번호가 db상에서 int이니까 int로 받아야할듯?
                msData.setMs_no(rst.getString(1));
                msData.setMs_address(rst.getString(2));
                msData.setMs_phone(rst.getString(3));
                msData.setMs_url(rst.getString(4));
                msData.setMs_holiday(rst.getString(5));
                msData.setMs_operating(rst.getString(6));
                msData.setMs_name(rst.getString(7));
                msData.setMs_rating(rst.getString(8));
                msData.setMs_like(rst.getString(9));
                msData.setMs_Img(rst.getString(10));
                msData.setMs_exp(rst.getString(11));
            }
        } catch(SQLException e){
            System.out.println("getMsData e : " + e);
        } finally {
            JDBCUtil.close(stmt, conn);
        }
    }

}
