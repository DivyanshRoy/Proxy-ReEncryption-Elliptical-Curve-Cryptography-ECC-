/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proxy.re.encryption.using.elliptic.curve.cryptography.ecc;

import java.sql.SQLException;
import java.util.Vector;

/**
 *
 * @author singh
 */
public class Encryption {
    
    public static String Encrypt(String str, Point public_key, Point G) throws ClassNotFoundException, SQLException
    {
        int len=str.length(),i;
        Long r;
        Point A,B;
        String str1=new String("");
        for(i=0;i<len;i++)
        {
            r=PointHelper.randomNumber();
            A=PointArithmetic.multiply(public_key, r);
            B=PointArithmetic.add(PointHelper.charToPoint(str.charAt(i)), PointArithmetic.multiply(G, r));
            str1+=PointHelper.pointToStream(A);
            str1+=PointHelper.pointToStream(B);
        }
        return str1;
    }
    
    public static String Decrypt(String str,Long private_key, Point G) throws ClassNotFoundException, SQLException
    {
        String str1 = "";
        Vector<Point> v = PointHelper.streamToPoint(str);
        
        int len = v.size();
        
        for(int i=0;i<len;i+=2)
        {
            Point c1 = v.elementAt(i);
            Point c2 = v.elementAt(i+1);
            Point DM=PointArithmetic.subtract(c2, PointArithmetic.multiply(c1, PointArithmetic.modInverse(private_key, PointHelper.numPoints())));
            str1+=PointHelper.pointToChar(DM);
        }
        
        return str1;
    }
    
    
}
