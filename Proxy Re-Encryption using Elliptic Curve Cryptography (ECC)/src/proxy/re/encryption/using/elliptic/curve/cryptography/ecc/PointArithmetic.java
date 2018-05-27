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
public class PointArithmetic {
    
    public static Point add(Point p1, Point p2)
    {
        long x3,y3,arr[]={0,0};
        long x1 = p1.getX();
        long y1 = p1.getY();
        Long x2 = new Long(p2.getX());
        Long y2 = new Long(p2.getY());
        
        if(x1==0&&y1==0)
        {
            return p2;
        }
        if(x2==0&&y2==0)
        {
            return p1;
        }
        if(x1==x2&&y1==CurveParams.getM()-y2)
            return new Point(new Long(0),new Long(0));
        if(x1==x2&&y1==y2)
        {
            return doubling(p1);
        }
        Long s,d,n=PointHelper.modulus(new Long(y2-y1));
        d=PointHelper.modulus(x2-x1);
        d=PointArithmetic.modInverse(d,CurveParams.getM());
        s=PointHelper.modulus(d*n);
        x3=PointHelper.modulus((s*s)-x1-x2);
        y3=PointHelper.modulus((s*(x1-x3))-y1);
        return new Point(x3,y3);
    }
    
    public static Long modInverse(Long a, Long m)
    {
        a = PointHelper.modulus(a);
        a = a%m;
        Long x;
        for(x = new Long(1) ; x<m ; x++)
        {
            if(((a*x)%m)==1)
                return x;
        }
        return x;
    }
    
    public static Point doubling(Point p)
    {
        Long x,y,x3,y3,s;
        x=p.getX();
        y=p.getY();
        if(y==0)
            return new Point(new Long(0),new Long(0));
        s=PointHelper.modulus(((3*x*x)+CurveParams.getA()));
        Long d=(2*y);
        d=PointHelper.modulus(PointArithmetic.modInverse(PointHelper.modulus(d),CurveParams.getM()));
        s=PointHelper.modulus(s*d);   
        x3=PointHelper.modulus((s*s)-(2*x));
        y3=PointHelper.modulus((s*(x-x3)-y));
        return new Point(x3,y3);
    }
    
    public static Point multiply(Point p, Long l)
    {
        if(l.longValue()==1)
        {
            return p;
        }
        Point halfMultiply=multiply(p,new Long(l.intValue()/2));
        Point ans=doubling(halfMultiply);
        if((l%2)==1)
            ans=add(ans,p);
        return ans;
    }
            
    public static Point subtract(Point p1, Point p2)
    {
        Point p3 = new Point(p2.getX(),CurveParams.getM()-p2.getY());
        return add(p1,p3);
    }
    
}
