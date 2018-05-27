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
public class Point {
    private Long x,y;
    
    public Point() {}
    
    public Point(Long x, Long y)
    {
        this.x = x;
        this.y = y;
    }
    
    public Long getX()
    {
        return x;
    }
    
    public Long getY()
    {
        return y;
    }
    
    public void setX(Long x)
    {
        this.x = x;
    }
    
    public void setY(Long y)
    {
        this.y = y;
    }
}
