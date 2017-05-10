package com.example.hong_inseon.projectlouvre.dao;

import android.util.Log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ExhibitionDAO {
    private static final String msListSQL = "select * from exhibition order by ex_no desc;";
    // 좋아요한 미술관 리스트 선택
    //private static final String msLikeListSQL = "select * from liked order by ms_no desc;";
    // 해당하는 미술관 정보 선택
    private static final String msSelectSQL = "select * from exhibition where ex_no = ? ;";

    // 미술관 리스트에 각 미술관 정보 가져오기
    public ArrayList<Exhibition> getExhibitionList(){
        Connection conn = null;
        //PreparedStatement stmt = null;
        Statement stmt = null;
        ResultSet rst = null;

        ArrayList<Exhibition> exlist = new ArrayList<Exhibition>();
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
            Exhibition msData = null;
            //반환하는 것 여러개일수 있으므로 반복수행, 결과를 데이터객체에 저장
            while(rst.next()) { //rst의 마지막 까지실행
                // 리스트뷰에서 보여줄 정보만 남기기
                msData = new Exhibition();
                msData.setEx_no(rst.getString(1));
                msData.setEx_name(rst.getString(2));
                msData.setEx_theme(rst.getString(3));
                msData.setEx_like(rst.getString(4));
                msData.setEx_img(rst.getString(5));
                msData.setMs_no(rst.getString(6));
                msData.setEx_location(rst.getString(7));
                msData.setEx_rating(rst.getString(8));
                msData.setEx_pay(rst.getString(9));
                msData.setEx_exp(rst.getString(10));
                msData.setEx_period(rst.getString(11));
                msData.setEx_ing(rst.getString(12));

                exlist.add(msData);
            }
        } catch(SQLException e){
            System.out.println("list e : " + e);
        } finally {
            //JDBCUtil.close(stmt, conn);
        }

        return exlist; // 미술관리스트 객체 반환
    }

    public void	getExhibitionData(Exhibition msData){
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rst = null;

        try{
            conn = JDBCUtil.getConnection();
            //하나의 접속요청에 하나의 getConnection으로 접속요청을 할 것, 종료시 커넥션 종료 (그래야 요청 섞이지 않음)

            stmt = conn.prepareStatement(msSelectSQL);
            stmt.setInt(1, Integer.parseInt(msData.getEx_no()));
            rst = stmt.executeQuery();

            if(rst.next()) { //rst이 있는지 확인
                // 번호가 db상에서 int이니까 int로 받아야할듯?
                msData.setEx_no(rst.getString(1));
                msData.setEx_name(rst.getString(2));
                msData.setEx_theme(rst.getString(3));
                msData.setEx_like(rst.getString(4));
                msData.setEx_img(rst.getString(5));
                msData.setMs_no(rst.getString(6));
                msData.setEx_location(rst.getString(7));
                msData.setEx_rating(rst.getString(8));
                msData.setEx_pay(rst.getString(9));
                msData.setEx_exp(rst.getString(10));
                msData.setEx_period(rst.getString(11));
                msData.setEx_ing(rst.getString(12));
            }
        } catch(SQLException e){
            System.out.println("getMsData e : " + e);
        } finally {
            JDBCUtil.close(stmt, conn);
        }
    }
}
