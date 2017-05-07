package com.example.hong_inseon.projectlouvre.dao;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created by samsung on 2016-12-03.
 */

public class JDBCUtil {
    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://35.161.181.60:3306/louvre"+"nullteam"+"null");
            Log.v("jdbc getConnection","result is"+conn);
            //return DriverManager.getConnection("jdbc:mysql://35.161.181.60:3306/louvre"+"nullteam"+"null");
            return conn;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    //select안하는경우
    public static void close(PreparedStatement stmt, Connection conn) {
        try {
            if(stmt != null)
                stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            stmt = null;
        }
        try {
            if(conn != null)
                conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn = null;
        }
    }

    //select 하는 경우 : result set을 받음.
    public static void close(ResultSet rst, PreparedStatement stmt, Connection conn) {
        try {
            if(rst != null)
                rst.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            rst = null;
        }
        try {
            if(stmt != null)
                stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            stmt = null;
        }
        try {
            if(conn != null)
                conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn = null;
        }
    }

}
