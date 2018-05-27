/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proxy.re.encryption.using.elliptic.curve.cryptography.ecc;

/**
 *
 * @author singh
 */
public class ECC {
    
    private Point publicKey;
    private Long privateKey;
    
    public ECC() {}
    
    public ECC(Point publicKey, Long privateKey)
    {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }
    
    public Point getPublicKey()
    {
        return publicKey;
    }
    
    public Long getPrivateKey()
    {
        return privateKey;
    }
    
    public void setPublicKey(Point publicKey)
    {
        this.publicKey = publicKey;
    }
    
    public void setPrivateKey(Long privateKey)
    {
        this.privateKey = privateKey;
    }
}
