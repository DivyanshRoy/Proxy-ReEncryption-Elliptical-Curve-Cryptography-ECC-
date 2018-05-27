/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proxy.re.encryption.using.elliptic.curve.cryptography.ecc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.Vector;
import javafx.util.Pair;

/**
 *
 * @author singh
 */
public class GenerateCharacterMappings {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        // TODO code application logic here
        Class.forName("org.apache.derby.jdbc.ClientDriver");

        Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/Points","abcd","1234");
        String query;
        ResultSet rs;
        query="select count(*) from abcd.pointsmapping";
        Statement stmt = conn.createStatement();
        rs = stmt.executeQuery(query);
        rs.next();
        Integer numPoints = new Integer(rs.getString(1));
        
        if(rs.getString(1).equals("0"))
        {
            System.out.println("Generate Elliptic Curve Points first.");
            System.exit(0);
        }
            
        query="select count(*) from abcd.charmapping";
        Statement stmt2 = conn.createStatement();
        rs = stmt2.executeQuery(query);
        rs.next();
        if(!rs.getString(1).equals("0"))
        {
            System.out.println("Character mapping already present.");
            System.out.println("Delete existing data to create new mappings.");
            System.exit(0);
        }
        
        Integer maxMappings = Math.min(numPoints, new Integer("65536"));
        System.out.println("Character Mappings can be created for only "+maxMappings+" characters.");
        
        Vector<Integer> asciicodes = new Vector<Integer>();
        
        for(Integer i = new Integer("0"); i<maxMappings ; i++)
        {
            asciicodes.add(i);
        }
        Collections.shuffle(asciicodes);
        
        query = "select x,y from pointsmapping where sno = ?";
        PreparedStatement pstmt = conn.prepareStatement(query);
        String query2 = "insert into abcd.charmapping values(?,?,?)";
        PreparedStatement pstmt2 = conn.prepareStatement(query2);

        Integer sno = new Integer("1");
        for(Integer asciicode:asciicodes)
        {
            pstmt.setString(1, sno.toString());
            rs = pstmt.executeQuery();
            rs.next();
            pstmt2.setString(1,asciicode.toString());
            pstmt2.setString(2, rs.getString(1));
            pstmt2.setString(3, rs.getString(2));
            pstmt2.executeUpdate();
            sno++;
        }
        pstmt.close();
        pstmt2.close();
        conn.close();
    }
    
}
