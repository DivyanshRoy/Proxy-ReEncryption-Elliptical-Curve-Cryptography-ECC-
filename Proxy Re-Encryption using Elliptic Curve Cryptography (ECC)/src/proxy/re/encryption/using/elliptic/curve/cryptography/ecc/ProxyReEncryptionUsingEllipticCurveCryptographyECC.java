/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proxy.re.encryption.using.elliptic.curve.cryptography.ecc;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;

/**
 *
 * @author singh
 */
public class ProxyReEncryptionUsingEllipticCurveCryptographyECC {

    /**
     * @param args the command line arguments
     */
    private static Point generateReferencePoint() throws ClassNotFoundException, SQLException
    {
        Point G = PointHelper.randomPoint();
        return G;
    }
    
    private static ECC generateUser(Point G) throws ClassNotFoundException, SQLException
    {
        Point publicKey;
        Long privateKey;
        privateKey = PointHelper.randomNumber();
        publicKey = PointArithmetic.multiply(G, privateKey);
        return new ECC(publicKey,privateKey);
    }
    
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        // TODO code application logic here
     
        //Set to any value not more than the number of points on the Elliptic Curve
        CurveParams.length = new Long(142);
        
        Point G = generateReferencePoint();
        CurveParams.calculateLength(G);
        ECC sender = generateUser(G);
        ECC reciever = generateUser(G);
        
        String message;
        Scanner sc = new Scanner(System.in);
        
        System.out.println("Enter Message: ");
        message = sc.nextLine();
        
        String encryptedMessage = Encryption.Encrypt(message, sender.getPublicKey(), G);
        System.out.println("Encrypted Message: "+encryptedMessage);
        
        String reEncryptedMessage = ProxyReEncryption.ReEncrypt(encryptedMessage, sender, reciever);
        System.out.println("ReEncrypted Message: "+reEncryptedMessage);
        
        String decryptedMessage = Encryption.Decrypt(reEncryptedMessage, reciever.getPrivateKey(),G);
        System.out.println("Decrypted Message = "+decryptedMessage);
    }
    
}
