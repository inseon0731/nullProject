//package com.example.hong_inseon.projectlouvre.dao;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//
///**
// * Created by samsung on 2016-12-03.
// */
//
//public class DataDAO {
//// 정보 입력하는 sql
//    //private static final String insertSQL = "INSERT INTO museum( TITLE, WRITER, CONTENT )"
//    //        + " VALUES( ?, ?, ?);"
//// 정보 업데이트(수정)하는 sql
////    private static final String updateSQL = "update board set title=? , content=? where seq = ? ;";
////삭제
////    private static final String deleteSQL = "delete from BOARD where seq = ? ;";
//
//    // 미술관 리스트 선택
//    private static final String msListSQL = "select * from museum order by ms_no desc;";
//    // 좋아요한 미술관 리스트 선택
//    private static final String msLikeListSQL = "select * from likeMuseum order by ms_no desc;";
//    // 해당하는 미술관 정보 선택
//    private static final String msSelectSQL = "select * from museum where ms_no = ? ;";
//    // 전시 리스트 선택
//    private static final String exhiListSQL = "select * from exhibition order by exhi_no desc;";
//    // 해당하는 전시 정보 선택
//    private static final String exhiSelectSQL = "select * from exhibition where exhi_no = ? ;";
//    // 도록 리스트 선택
//    private static final String wbListSQL = "select * from workbook order by wb_no desc;";
//    // 해당하는 도록 정보 선택
//    private static final String wbSelectSQL = "select * from workbook where wb_no = ? ;";
//    // 오디오가이드 리스트 선택
//    private static final String audioListSQL = "select * from audio order by audio_no desc;";
//    // 해당하는 오디오가이드 정보 선택
//    private static final String audioSelectSQL = "select * from audio where audio_no = ? ;";
//
//    //private static final String updateCntSQL = "update board set cnt=cnt+1 where seq = ? ;";
//
////    public void doDelete(ExhiData board){
////
////        Connection conn = null;
////        PreparedStatement stmt = null;
////
////        try{
////            conn = JDBCUtil.getConnection();
////            stmt = conn.prepareStatement(deleteSQL);
////            stmt.setInt(1, Integer.parseInt(board.getSeq()));
////            int cnt = stmt.executeUpdate(); //영향받은 레코드 개수 리턴
////            System.out.println("delete " + (cnt==1 ? "success" : "fail") );
////            //삭제할 데이터가 없는경우 : post로 seq값을 변경해서 넘긴 경우 에러유발
////        } catch(SQLException e){
////            System.out.println("delete e : " + e);
////        } finally {
////            //try/catch실행 후
////            JDBCUtil.close(stmt, conn);
////        }
////    }
//
////    public void doUpdate(ExhiData board){
////
////        Connection conn = null;
////        PreparedStatement stmt = null;
////
////        try{
////            conn = JDBCUtil.getConnection();
////            stmt = conn.prepareStatement(updateSQL);
////            stmt.setString(1, board.getTitle());
////            stmt.setString(2, board.getContent());
////            stmt.setInt(3, Integer.parseInt(board.getSeq())); //
////            int cnt = stmt.executeUpdate(); //영향받은 레코드 개수 리턴
////            System.out.println("update " + (cnt==1 ? "success" : "fail"));
////        } catch(SQLException e){
////            System.out.println("update e : " + e);
////        } finally {
////            //try/catch실행 후
////            JDBCUtil.close(stmt, conn);
////        }
////
//// 객체안의 변수가 아니라 객체를 입력받고 리턴하자(캡슐화)
////    public void doInsert(ExhiData board){
////
////        Connection conn = null;
////        PreparedStatement stmt = null;
////
////        try{
////            conn = JDBCUtil.getConnection();
////            stmt = conn.prepareStatement(insertSQL);
////            stmt.setString(1, board.getTitle());
////            stmt.setString(2, board.getWriter());
////            stmt.setString(3, board.getContent());
////            int cnt = stmt.executeUpdate(); //영향받은 레코드 개수 리턴
////            System.out.println("insert " + (cnt==1 ? "success" : "fail"));
////        } catch(SQLException e){
////            System.out.println("insert e : " + e);
////        } finally {
////            //try/catch실행 후
////            JDBCUtil.close(stmt, conn);
////        }
////    }
//
//    public ArrayList<Exhibition> getExhiList(){
//        Connection conn = null;
//        PreparedStatement stmt = null;
//        ResultSet rst = null;
//
//        ArrayList<Exhibition> exhilist = new ArrayList<Exhibition>();
//        try{
//            conn = JDBCUtil.getConnection();
//            //하나의 접속요청에 하나의 getConnection으로 접속요청을 할 것, 종료시 커넥션 종료 (그래야 요청 섞이지 않음)
//
//            stmt = conn.prepareStatement(exhiListSQL);
//            rst = stmt.executeQuery();
//            Exhibition exhiData = null;
//            //반환하는 것 여러개일수 있으므로 반복수행
//            while(rst.next()) { //rst의 마지막 까지실행
//                exhiData = new Exhibition();
//                exhiData.setExhi_no(rst.getInt(1)); //int에서 문자열로 형변환
//                exhiData.setExhi_intro(rst.getString(2));
//                exhiData.setExhi_start(rst.getString(3));
//                exhiData.setExhi_finish(rst.getString(4));
//                exhiData.setExhi_name(rst.getString(5));
//                exhiData.setExhi_pic1(rst.getString(6));
//                exhiData.setExhi_pic2(rst.getString(7));
//
//                exhilist.add(exhiData);
//            }
//        } catch(SQLException e){
//            System.out.println("list e : " + e);
//        } finally {
//            JDBCUtil.close(stmt, conn);
//        }
//
//        return exhilist; //객체 반환
//    }
//
//    //string seq 할수도 있지만 객체로 받아서 추후 기능확장 가능하도록 구현
//    public void	getExhiData(Exhibition exhiData){
//        Connection conn = null;
//        PreparedStatement stmt = null;
//        ResultSet rst = null;
//
//        try{
//            conn = JDBCUtil.getConnection();
//            //하나의 접속요청에 하나의 getConnection으로 접속요청을 할 것, 종료시 커넥션 종료 (그래야 요청 섞이지 않음)
//            //stmt = conn.prepareStatement(updateCntSQL);
//            stmt.setInt(1, exhiData.getExhi_no());
//            stmt.executeUpdate();
//
//            stmt = conn.prepareStatement(exhiSelectSQL);
//            //selectSQL 의 ?자리 채우기
////          stmt.setInt(1, Integer.parseInt(exhiData.getExhi_no()));
//            stmt.setInt(1, exhiData.getExhi_no());
//            rst = stmt.executeQuery();
//            //반환하는 것 여러개일수 있으므로 반복수행
//            if(rst.next()) { //rst이 있는지 확인
//                exhiData.setExhi_no(rst.getInt(1)); //int에서 문자열로 형변환
//                exhiData.setExhi_intro(rst.getString(2));
//                exhiData.setExhi_finish(rst.getString(3));
//                exhiData.setExhi_name(rst.getString(4));
//                exhiData.setExhi_pic1(rst.getString(5));
//                exhiData.setExhi_pic2(rst.getString(6));
//            }
//        } catch(SQLException e){
//            System.out.println("getExhiData e : " + e);
//        } finally {
//            JDBCUtil.close(stmt, conn);
//        }
//    }
//
//    // 미술관 리스트와 각 미술관 정보 가져오기
//    public ArrayList<Museum> getMslist(){
//        Connection conn = null;
//        PreparedStatement stmt = null;
//        ResultSet rst = null;
//
//        ArrayList<Museum> mslist = new ArrayList<Museum>();
//        try{
//            conn = JDBCUtil.getConnection();
//            //하나의 접속요청에 하나의 getConnection으로 접속요청을 할 것, 종료시 커넥션 종료 (그래야 요청 섞이지 않음)
//
//            stmt = conn.prepareStatement(msListSQL);
//            rst = stmt.executeQuery();
//            Museum museumData = null;
//            //반환하는 것 여러개일수 있으므로 반복수행
//            while(rst.next()) { //rst의 마지막 까지실행
//                museumData = new Museum();
//                museumData.setMs_no(rst.getString(1)); //int에서 문자열로 형변환
//                museumData.setMs_address(rst.getString(2));
//                museumData.setMs_phone(rst.getString(3));
//                museumData.setMs_webpage(rst.getString(4));
//                museumData.setMs_operating(rst.getString(5));
//                museumData.setMs_name(rst.getString(6));
//
//                mslist.add(museumData);
//            }
//        } catch(SQLException e){
//            System.out.println("mslist e : " + e);
//        } finally {
//            JDBCUtil.close(stmt, conn);
//        }
//
//        return mslist; //객체 반환
//    }
//
//    //string seq 할수도 있지만 객체로 받아서 추후 기능확장 가능하도록 구현
//    public void	getMuseumData(Museum museumData){
//        Connection conn = null;
//        PreparedStatement stmt = null;
//        ResultSet rst = null;
//
//        try{
//            conn = JDBCUtil.getConnection();
//            //하나의 접속요청에 하나의 getConnection으로 접속요청을 할 것, 종료시 커넥션 종료 (그래야 요청 섞이지 않음)
//            //stmt = conn.prepareStatement(updateCntSQL);
//            stmt = conn.prepareStatement(exhiSelectSQL);
//            //selectSQL 의 ?자리 채우기
//            stmt.setInt(1, Integer.parseInt(museumData.getMs_no()));
//            rst = stmt.executeQuery();
//            //반환하는 것 여러개일수 있으므로 반복수행
//            if(rst.next()) { //rst이 있는지 확인
//                museumData.setMs_no(rst.getString(1));
//                museumData.setMs_address(rst.getString(2));
//                museumData.setMs_phone(rst.getString(3));
//                museumData.setMs_webpage(rst.getString(4));
//                museumData.setMs_holiday(rst.getString(5));
//                museumData.setMs_operating(rst.getString(6));
//                museumData.setMs_name(rst.getString(7));
//            }
//        } catch(SQLException e){
//            System.out.println("getMuseumData e : " + e);
//        } finally {
//            JDBCUtil.close(stmt, conn);
//        }
//    }
//
//    // 도록 리스트와 각 도록의 정보 가져오기
//    public ArrayList<Workbook> getWorkbooklist(){
//        Connection conn = null;
//        PreparedStatement stmt = null;
//        ResultSet rst = null;
//
//        ArrayList<Workbook> workbooklist = new ArrayList<Workbook>();
//        try{
//            conn = JDBCUtil.getConnection();
//            //하나의 접속요청에 하나의 getConnection으로 접속요청을 할 것, 종료시 커넥션 종료 (그래야 요청 섞이지 않음)
//
//            stmt = conn.prepareStatement(wbListSQL);
//            rst = stmt.executeQuery();
//            Workbook workbookData = null;
//            //반환하는 것 여러개일수 있으므로 반복수행
//            while(rst.next()) { //rst의 마지막 까지실행
//                workbookData = new Workbook();
//                workbookData.setWb_no(rst.getInt(1));
//                workbookData.setWb_story(rst.getString(2));
//                //workbookData.setWb_name(rst.getString(3));
//
//                workbooklist.add(workbookData);
//            }
//        } catch(SQLException e){
//            System.out.println("workbooklist e : " + e);
//        } finally {
//            JDBCUtil.close(stmt, conn);
//        }
//
//        return workbooklist; //객체 반환
//    }
//
//    //string seq 할수도 있지만 객체로 받아서 추후 기능확장 가능하도록 구현
//    public void	getWorkbookData(Workbook workbookData){
//        Connection conn = null;
//        PreparedStatement stmt = null;
//        ResultSet rst = null;
//
//        try{
//            conn = JDBCUtil.getConnection();
//            //하나의 접속요청에 하나의 getConnection으로 접속요청을 할 것, 종료시 커넥션 종료 (그래야 요청 섞이지 않음)
//            //stmt = conn.prepareStatement(updateCntSQL);
//            stmt.setInt(1, workbookData.getWb_no());
//            stmt.executeUpdate();
//
//            stmt = conn.prepareStatement(wbSelectSQL);
//            //selectSQL 의 ?자리 채우기
//            stmt.setInt(1, workbookData.getWb_no());
//            rst = stmt.executeQuery();
//            //반환하는 것 여러개일수 있으므로 반복수행
//            if(rst.next()) { //rst이 있는지 확인
//                workbookData.setWb_no(rst.getInt(1));
//                workbookData.setWb_story(rst.getString(2));
//                //workbookData.setMs_phone(rst.getString(3));
//            }
//        } catch(SQLException e){
//            System.out.println("getWorkbookData e : " + e);
//        } finally {
//            JDBCUtil.close(stmt, conn);
//        }
//    }
//}
