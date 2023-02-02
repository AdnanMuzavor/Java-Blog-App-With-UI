/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbmsproject;

import dbmsproject.HomeFrame;
import dbmsproject.Connector;
import java.awt.*;

/**
 *
 * @author ADNAN
 */
public class DBMSProject {

    /**
     * @param args the command line arguments
     */
    Boolean user_login = false;

    public static void main(String[] args) {
        //Firts establish connection with DB
        Connector con1 = new Connector();
        // TODO code application logic here
        HomeFrame h1 = new HomeFrame(con1.getConn());
        h1.setSize(new Dimension(500, 500));
        h1.setVisible(true);

    }

}
