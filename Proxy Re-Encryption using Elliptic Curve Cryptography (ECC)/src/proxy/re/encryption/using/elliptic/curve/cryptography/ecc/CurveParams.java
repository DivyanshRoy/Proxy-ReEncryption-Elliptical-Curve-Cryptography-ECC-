/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proxy.re.encryption.using.elliptic.curve.cryptography.ecc;

/**
 *
 * @author x
 */


public class CurveParams {
    
    
    private static Long a = new Long(17);
    private static Long b = new Long(50);
    private static Long m = new Long(191);
    
    //Alternative Curve Params
    /*
    private static Long a = new Long(22);
    private static Long b = new Long(55);
    private static Long m = new Long(137);
    */
    
    //Alternative Curve Params
    /*
    private static Long a = new Long(2);
    private static Long b = new Long(9);
    private static Long m = new Long(43);
    */
    
    public static Long length = new Long(m);
    
        
    public static Long getA()
    {
        return a;
    }
    
    public static Long getB()
    {
        return b;
    }
    
    public static Long getM()
    {
        return m;
    }
    
    public static void calculateLength(Point G)
    {
        Point p = new Point(G.getX(),G.getY());
        Integer i = new Integer(0);
        do
        {
            p = PointArithmetic.add(p, G);
            i++;
        }while(p.getX().longValue()!=G.getX().longValue()||p.getY().longValue()!=G.getY().longValue());
        length = new Long(i);
    }
}















