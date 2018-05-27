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
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Vector;
import javafx.util.Pair;

/**
 *
 * @author singh
 */
public class GenerateEllipticCurvePoints {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
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
        
        if(!rs.getString(1).equals("0"))
        {
            System.out.println("Elliptic Curve poins already present.");
            System.out.println("Delete existing data to create new points.");
            System.exit(0);
        }
        
        
        //y^2 = (x^3 + ax + b) mod m 
        
        Long x,y;
        Map<Long, Vector<Long> > mx = new TreeMap<Long, Vector<Long> >();
        Map<Long, Vector<Long> > my = new TreeMap<Long, Vector<Long> >();
        Vector<Long> tmp;
        
        Long a = CurveParams.getA();
        Long b = CurveParams.getB();
        Long m = CurveParams.getM();
        
        for(x = new Long(0) ; x < m ; x++)
        {
            Long v = ( (x*x*x) + (a*x) + b ) % m;
            if(mx.containsKey(v))
            {
                tmp = mx.get(v);
                tmp.add(x);
                mx.put(v, tmp);
            }
            else
            {
                tmp = new Vector<Long>();
                tmp.add(x);
                mx.put(v,tmp);
            }
        }
        
        for(y = new Long(0) ; y < m ; y++)
        {
            Long v = ( (y*y) ) % m;
            if(my.containsKey(v))
            {
                tmp = my.get(v);
                tmp.add(y);
                my.put(v, tmp);
            }
            else
            {
                tmp = new Vector<Long>();
                tmp.add(y);
                my.put(v,tmp);
            }
        }
        
        Vector<Pair<Long,Long>> points = new Vector<Pair<Long,Long>>();
        
        Set<Long> set = mx.keySet();
        for(Long s:set)
        {
            if(my.containsKey(s))
            {
                Vector<Long> vx = mx.get(s);
                Vector<Long> vy = my.get(s);
                for(Long x1:vx)
                {
                    for(Long y1:vy)
                    {
                        points.add(new Pair(x1,y1));
                    }
                }
            }
        }
        points.add(new Pair(new Long("0"),new Long("0")));
        
        query="insert into abcd.pointsmapping values(?,?,?,?)";
        PreparedStatement pstmt = conn.prepareStatement(query);

        Integer sno = new Integer("1");
        for(Pair<Long,Long> p:points)
        {
            pstmt.setString(1,sno.toString());
            pstmt.setString(2, p.getKey().toString());
            pstmt.setString(3, p.getValue().toString());
            Long val = ( p.getKey()*m + p.getValue() );
            pstmt.setString(4, val.toString());
            pstmt.executeUpdate();
            sno++;
        }
        pstmt.close();
        conn.close();

        System.out.println("Equation:  y^2 = ( x^3 + a*x + b ) mod m");
        System.out.println("a = "+a);
        System.out.println("b = "+b);
        System.out.println("m = "+m);
        System.out.println("Number of Points = "+points.size());
    }
    
}
