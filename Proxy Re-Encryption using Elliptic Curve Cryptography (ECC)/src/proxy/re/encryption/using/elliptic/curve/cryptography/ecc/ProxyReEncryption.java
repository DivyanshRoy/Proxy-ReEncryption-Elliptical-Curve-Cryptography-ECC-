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
public class ProxyReEncryption {
    
    public static String ReEncrypt(String str, ECC sender, ECC reciever) throws ClassNotFoundException, SQLException
    {
        Long ReEncryptionKey = (reciever.getPrivateKey()*PointArithmetic.modInverse(sender.getPrivateKey(), PointHelper.numPoints()))%PointHelper.numPoints();
        
        Vector<Point> v = PointHelper.streamToPoint(str);
        Point p1,p2;
        String str1 = "";
        for(int i=0;i<v.size();i+=2)
        {
            p1 = v.elementAt(i);
            p2 = v.elementAt(i+1);
            
            Point p3 = PointArithmetic.multiply(p1, ReEncryptionKey);
            str1 += PointHelper.pointToStream(p3);
            str1 += PointHelper.pointToStream(p2);
        }
        
        return str1;
    }
    
}
