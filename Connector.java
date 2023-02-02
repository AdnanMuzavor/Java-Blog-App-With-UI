/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbmsproject;

import java.sql.*;

/**
 *
 * @author ADNAN
 */
public class Connector {

    String DRIVER = "com.mysql.cj.jdbc.Driver";
    String user = "root";
    String url = "jdbc:mysql://localhost/jdbc1";
    String pw = "";
    Connection conn;

    Connector() {
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(url, user, pw);
            System.out.println("Connection with database succesful!!");
        } catch (Exception e) {
            System.out.println("Connection with databse failed as: " + e);
        }

    }

    Connection getConn() {
        return conn;
    }
}
