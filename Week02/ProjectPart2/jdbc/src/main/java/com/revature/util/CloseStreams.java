package com.revature.util;


import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CloseStreams {

    public static void close(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            }
            catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    public static void close(Connection c) {
        if (c != null) {
            try {
                c.close();
            }
            catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    public static  void close (ResultSet r) {
         if (r != null) {
            try {
                r.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
         }
    }

    public static void close(FileInputStream f) {
        if (f != null) {
            try {
                f.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
