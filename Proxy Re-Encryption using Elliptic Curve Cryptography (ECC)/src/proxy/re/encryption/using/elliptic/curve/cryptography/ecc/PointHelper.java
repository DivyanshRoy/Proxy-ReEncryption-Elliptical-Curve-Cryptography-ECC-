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
import java.util.Random;
import java.util.Vector;

/**
 *
 * @author singh
 */
public class PointHelper {
    
    public static int getSegementSize() throws ClassNotFoundException, SQLException
    {
        Long numPoints = new Long(CurveParams.getM()*CurveParams.getM()+1);
        
        int size = 0;
        while(numPoints.longValue()!=0)
        {
            numPoints/=26;
            size++;
        }
        return size;
    }
    
    public static Point charToPoint(char c) throws ClassNotFoundException, SQLException
    {
        Class.forName("org.apache.derby.jdbc.ClientDriver");

        Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/Points","abcd","1234");
        String query;
        ResultSet rs;
        query="select x,y from abcd.charmapping where asciicode = ?";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, new Integer((int)c).toString());
        rs = pstmt.executeQuery();
        rs.next();
        Point p = new Point(new Long(rs.getString(1)),new Long(rs.getString(2)));
        rs.close();
        pstmt.close();
        conn.close();
        return p;
    }
    
    public static char pointToChar(Point p) throws ClassNotFoundException, SQLException
    {
        Class.forName("org.apache.derby.jdbc.ClientDriver");

        Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/Points","abcd","1234");
        String query;
        ResultSet rs;
        
        query="select asciicode from abcd.charmapping where x = ? and y = ?";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, p.getX().toString());
        pstmt.setString(2, p.getY().toString());
        rs = pstmt.executeQuery();
        if(rs.next())
        {
            char c = (char)new Integer(rs.getString(1)).intValue();
            rs.close();
            pstmt.close();
            conn.close();
            return c;
        }
        else
        {
            System.out.println(p.getX()+" , "+p.getY());
            return '/';
        }
    }
    
    public static Vector<Point> streamToPoint(String stream) throws ClassNotFoundException, SQLException
    {
        Vector<Point> v = new Vector<>();
        int segmentSize = getSegementSize();
        
        int i=0;
        while(i<stream.length())
        {
            String temp = stream.substring(i, i+segmentSize);
            i+=segmentSize;
            
            Long val = new Long("0");
            int j;
            for(j=0;j<segmentSize;j++)
            {
                int d = temp.charAt(j) - 'a';
                val = val*26 + d;
            }
            v.add(new Point(val/CurveParams.getM(),val%CurveParams.getM()));
        }
        
        return v;
    }
    
    public static String pointToStream(Point p) throws ClassNotFoundException, SQLException
    {
        String stream = "";
        int segmentSize = getSegementSize();
        Long val = p.getX()*CurveParams.getM() + p.getY();
        String temp = "";
        for(int i=0;i<segmentSize;i++)
        {
            temp += (char)('a' + val%26);
            val /= 26;
        }
        for(int i=segmentSize-1;i>=0;i--)
        {
            stream += temp.charAt(i);
        }
        return stream;
    }
    
    public static Long numPoints() throws ClassNotFoundException, SQLException
    {
        Class.forName("org.apache.derby.jdbc.ClientDriver");

        Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/Points","abcd","1234");
        String query;
        ResultSet rs;
        query="select count(*) from abcd.pointsmapping";
        Statement stmt = conn.createStatement();
        rs = stmt.executeQuery(query);
        rs.next();
        Long numPoints = new Long(rs.getString(1));
        return numPoints;
    }
    
    public static Point randomPoint() throws ClassNotFoundException, SQLException
    {
        Class.forName("org.apache.derby.jdbc.ClientDriver");

        Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/Points","abcd","1234");
        String query;
        ResultSet rs;
        query="select count(*) from abcd.pointsmapping";
        Statement stmt = conn.createStatement();
        rs = stmt.executeQuery(query);
        rs.next();
        Integer numPoints = new Integer(rs.getString(1));
        
        Random random=new Random();
        Integer r = random.nextInt(CurveParams.length.intValue());
        
        
        query="select x,y from abcd.pointsmapping where sno = ?";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, r.toString());
        rs = pstmt.executeQuery();
        rs.next();
        
        Point p = new Point(new Long(rs.getString(1)),new Long(rs.getString(2)));
        rs.close();
        stmt.close();
        pstmt.close();
        conn.close();
        return p;
    }
    
    public static Long randomNumber() throws ClassNotFoundException, SQLException
    {
        Class.forName("org.apache.derby.jdbc.ClientDriver");

        Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/Points","abcd","1234");
        String query;
        ResultSet rs;
        query="select count(*) from abcd.pointsmapping";
        Statement stmt = conn.createStatement();
        rs = stmt.executeQuery(query);
        rs.next();
        Long numPoints = new Long(rs.getString(1));
        
        Random random=new Random();
        Integer r = new Integer(random.nextInt((CurveParams.getM().intValue()-1)) + 1);
        Long r1 = new Long(r.intValue());
        return r1;
    }
    
    public static Long modulus(Long l)
    {
        while(l<0)
        {
            l += CurveParams.getM();
        }
        l %= CurveParams.getM();
        return l;
    }
}
