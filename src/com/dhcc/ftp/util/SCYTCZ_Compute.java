package com.dhcc.ftp.util;


public class SCYTCZ_Compute {

	/**************************
	** ����������ֵ���� java
	** ������Ȼ�߽磬
	**     �гֱ߽磬
	**     ��Ť��߽�
	** �����µĲ�ֵ
	** 2008-12-27
	**####### ���η��ظ������ζ���ʽ������4������ֵ���ҽ������ʽ����ֵ�������»�����ȡֵ��
	          ��ÿ���Ա�������[x1,x2]����˵��Ԫ������ÿ����˵�x1����0���Ҷ˵�x2��Ϊ(x2-x1)���м�x��Ϊ(x-x1)��
	**************************/
	
	/**
	 * ����������ֵ�ı߽�����
	 */
	 static enum Condition
	{
		 /**
		  * ��Ȼ�߽�����
		  */
	    NATURAL,
	    /**
	     * �гֱ߽�����
	     */
	    CLAMPED,
	    /**
	     * ��Ť��߽�
	     */
	    NOTaKNOT
	};
	
	/**
	 * ԭʼ�������Ϣ��
	 * @author wang
	 *
	 */
	static class Point
	{
		
	    //Coefficient[] coe;//ÿ�ν�����ζ���ʽ��4��ϵ��  ����
	    double[] xCoordinate;//ԭʼ������x����������,xCoordinate[]�ڲ��ܳ����ظ����� 
	    double[] yCoordinate;//ԭʼ������y����������
	    double f0;//ʹ��ʱ��ʱû�ã�ֱ�Ӹ�ֵΪ0
	    double fn;//ʹ��ʱ��ʱû�ã�ֱ�Ӹ�ֵΪ0
	    int num;//ԭʼ������ܸ���
	    Condition con;//����������ֵ�ı߽�����
	};
	
	/**
	 * ��֪һϵ����ɢ�� ��������������ֵ����ȡ����������ֵ�������(�ֶ����ζ���ʽ)��
	 * <br>Ĭ�ϱ߽�����Ϊ����Ť��߽硯Condition.NOTaKNOT
	 * @param x ��֪��ɢ�� xֵ���� <��С��������>  --- ��֪��������벻����3��
	 * @param y x��Ӧ����֪��ɢ�� yֵ���� <��С��������>
	 * @return
	 */
	public static SCYTCZlineF getSCYTCZline(double[] x,double[] y){
		if(x.length!=y.length){
			System.out.println("������֪��������Ϣ����x��y�ĸ�����һ�£�");
			return null;
		}
		if(x.length<3){
			System.out.println("������֪�����Ϊ"+x.length+"������3�������ܽ�������������ֵ���㣡");
			return null;
		}
		
		int n = x.length;
	    Point p=new Point();
	    p.xCoordinate = x;
	    p.yCoordinate = y;
	    p.f0 = 0;
	    p.fn = 0;
	    p.num = n;
	    p.con = Condition.NOTaKNOT;
	    double[][] coe=spline(p);
	    
	    for(int i = 0; i < n - 1; i++){
	    	System.out.println(coe[i][0]+", "+coe[i][1]+", "+ coe[i][2]+", "+coe[i][3]);
	    }  
	    return new SCYTCZlineF(coe,x,y);
	}
	
	/**
	 * ��֪һϵ����ɢ�� ��������������ֵ����ȡ����������ֵ�������(�ֶ����ζ���ʽ)
	 * @param x  ��֪��ɢ�� xֵ���� <��С��������>
	 * @param y  x��Ӧ����֪��ɢ�� yֵ���� <��С��������>
	 * @param con 
	 * @return
	 */
	public static SCYTCZlineF getSCYTCZline(double[] x,double[] y,Condition con){
		if(x.length!=y.length){
			System.out.println("������֪��������Ϣ����x��y�ĸ�����һ�£�����null");
			return null;
		}
		if(x.length<3){
			System.out.println("������֪�����Ϊ"+x.length+"������3�������ܽ�������������ֵ���㣡����null");
			return null;
		}
		int n = x.length;
	    Point p=new Point();
	    p.xCoordinate = x;
	    p.yCoordinate = y;
	    p.f0 = 0;
	    p.fn = 0;
	    p.num = n;
	    p.con = con;
	    double[][] coe=spline(p);
	    
	    for(int i = 0; i < n - 1; i++){
	    	System.out.println(coe[i][0]+", "+coe[i][1]+", "+ coe[i][2]+", "+coe[i][3]);
	    }  
	    return new SCYTCZlineF(coe,x,y);
	}

	/**
	 * 
	 * @param point ԭʼ�������Ϣ��
	 * @return double[][] ÿ�ν�����ζ���ʽϵ��  double[i][0]Ϊ������ϵ����double[i][1]Ϊ������ϵ��....
	 */
	public static double[][] spline(Point point)
	{
	    double[] x = point.xCoordinate, y = point.yCoordinate;
	    int n = point.num - 1;
	    double[][] coe = new double[n][4];
	    Condition con = point.con;
	    double[] h=new double[n], d=new double[n];
	    double[] a=new double[n+1], b=new double[n+1], c=new double[n+1], f=new double[n+1], m=new double[n+1];
	    double temp;
	    int i;
	    /*h = (double *)malloc( n * sizeof(double) );  0,1--(n-1),n 
	    d = (double *)malloc( n * sizeof(double) );  0,1--(n-1),n 
	    
	    a = (double *)malloc( (n + 1) * sizeof(double) );   �ر�ʹ��,1--(n-1),       n 
	    b = (double *)malloc( (n + 1) * sizeof(double) );          0,1--(n-1),       n 
	    c = (double *)malloc( (n + 1) * sizeof(double) );          0,1--(n-1),�ر�ʹ�� 
	    f = (double *)malloc( (n + 1) * sizeof(double) );          0,1--(n-1),       n */
	    m = b;
	    if(f == null)
	    {
	        /*free(h);
	        free(d);
	        free(a);
	        free(b);
	        free(c);*/
	        return null;
	    }
	    /* ���� h[] d[] */
	    for (i = 0; i < n; i++)
	    {
	        h[i] = x[i + 1] - x[i];
	        d[i] = (y[i + 1] - y[i]) / h[i];
	        /* printf("%f\t%f\n", h[i], d[i]); */
	    }
	    /**********************
	    ** ��ʼ��ϵ���������
	    **********************/
	    a[0] = point.f0;
	    c[n] = point.fn;
	    /* ���� a[] b[] d[] f[] */
	    switch(con)
	    {
	        case NATURAL:
	            f[0] = a[0];
	            f[n] = c[n];
	            a[0] = 0;
	            c[n] = 0;
	            c[0] = 0;
	            a[n] = 0;
	            b[0] = 1;
	            b[n] = 1;
	        break;
	        
	        case CLAMPED:
	            f[0] = 6 * (d[0] - a[0]);
	            f[n] = 6 * (c[n] - d[n - 1]);
	            a[0] = 0;
	            c[n] = 0;
	            c[0] = h[0];
	            a[n] = h[n - 1];
	            b[0] = 2 * h[0];
	            b[n] = 2 * h[n - 1];
	        break;
	        
	        case NOTaKNOT:
	            f[0] = 0;
	            f[n] = 0;
	            a[0] = h[0];
	            c[n] = h[n - 1];
	            c[0] = -(h[0] + h[1]);
	            a[n] = -(h[n - 2] + h[n - 1]);
	            b[0] = h[1];
	            b[n] = h[n - 2];
	        break;
	    }

	    for (i = 1; i < n; i++)
	    {
	        a[i] = h[i - 1];
	        b[i] = 2 * (h[i - 1] + h[i]);
	        c[i] = h[i];
	        f[i] = 6 * (d[i] - d[i - 1]);
	    }
	    /* for (i = 0; i < n+1; i++)   printf("%f\n", c[i]); */
	    
	    /*************
	    ** ��˹��Ԫ
	    *************/
	    /* ��0�е���(n-3)�е���Ԫ */
	    for(i = 0; i <= n - 3; i++)
	    {
	        /* ѡ��Ԫ */
	        if( Math.abs(a[i + 1]) > Math.abs(b[i]) )
	        {
	            temp = a[i + 1]; a[i + 1] = b[i]; b[i] = temp;
	            temp = b[i + 1]; b[i + 1] = c[i]; c[i] = temp;
	            temp = c[i + 1]; c[i + 1] = a[i]; a[i] = temp;
	            temp = f[i + 1]; f[i + 1] = f[i]; f[i] = temp;
	        }
	        temp = a[i + 1] / b[i];
	        a[i + 1] = 0;
	        b[i + 1] = b[i + 1] - temp * c[i];
	        c[i + 1] = c[i + 1] - temp * a[i];
	        f[i + 1] = f[i + 1] - temp * f[i];
	    }
	    
	    /* ���3�е���Ԫ */
	    /* ѡ��Ԫ */
	    if( Math.abs(a[n - 1]) > Math.abs(b[n - 2]) )
	    {
	        temp = a[n - 1]; a[n - 1] = b[n - 2]; b[n - 2] = temp;
	        temp = b[n - 1]; b[n - 1] = c[n - 2]; c[n - 2] = temp;
	        temp = c[n - 1]; c[n - 1] = a[n - 2]; a[n - 2] = temp;
	        temp = f[n - 1]; f[n - 1] = f[n - 2]; f[n - 2] = temp;
	    }
	    /* ѡ��Ԫ */
	    if( Math.abs(c[n]) > Math.abs(b[n - 2]) )
	    {
	        temp = c[n]; c[n] = b[n - 2]; b[n - 2] = temp;
	        temp = a[n]; a[n] = c[n - 2]; c[n - 2] = temp;
	        temp = b[n]; b[n] = a[n - 2]; a[n - 2] = temp;
	        temp = f[n]; f[n] = f[n - 2]; f[n - 2] = temp;
	    }
	    /* ��(n-1)����Ԫ */
	    temp = a[n - 1] / b[n - 2];
	    a[n - 1] = 0;
	    b[n - 1] = b[n - 1] - temp * c[n - 2];
	    c[n - 1] = c[n - 1] - temp * a[n - 2];
	    f[n - 1] = f[n - 1] - temp * f[n - 2];
	    /* ��n����Ԫ */
	    temp = c[n] / b[n - 2];
	    c[n] = 0;
	    a[n] = a[n] - temp * c[n - 2];
	    b[n] = b[n] - temp * a[n - 2];
	    f[n] = f[n] - temp * f[n - 2];
	    /* ѡ��Ԫ */
	    if( Math.abs(a[n]) > Math.abs(b[n - 1]) )
	    {
	        temp = a[n]; a[n] = b[n - 1]; b[n - 1] = temp;
	        temp = b[n]; b[n] = c[n - 1]; c[n - 1] = temp;
	        temp = f[n]; f[n] = f[n - 1]; f[n - 1] = temp;
	    }
	    /* ���һ����Ԫ */
	    temp = a[n] / b[n-1];
	    a[n] = 0;
	    b[n] = b[n] - temp * c[n - 1];
	    f[n] = f[n] - temp * f[n - 1];
	    
	    /* �ش���� m[] */
	    m[n] = f[n] / b[n];
	    m[n - 1] = (f[n - 1] - c[n - 1] * m[n]) / b[n-1];
	    for(i = n - 2; i >= 0; i--)
	    {
	        m[i] = ( f[i] - (m[i + 2] * a[i] + m[i + 1] * c[i]) ) / b[i];
	    }
	    /* for (i = 0; i < n+1; i++)   printf("%f\n", m[i]); */
	    /*free(a);
	    free(c);
	    free(f);*/
	    /************
	    ** ���ò���
	    ************/
	    for(i = 0; i < n; i++)
	    {
	        coe[i][0] = (m[i + 1] - m[i]) / (6 * h[i]);
	        coe[i][1] = m[i] / 2;
	        coe[i][2] = d[i] - (h[i] / 6) * (2 * m[i] + m[i + 1]);
	        coe[i][3] = y[i];
	    }
	    
	    /*free(h);
	    free(d);
	    free(b);*/
	    return coe;
	}
	
	

	public static void main(String[] args) {
	    /* x[]�ڲ��ܳ����ظ����� */
	    /*double x[] = { 0, 0.5, 0.75, 1.25, 2.5,  5, 7.5, 10, 15,
	                  20,  25,   30,   35,  40, 45,  50, 55, 60,
	                  65,  70,   75,   80,  85, 90,  95, 100};
	    double y[] = {     0, 0.6107, 0.7424, 0.9470, 1.3074, 1.7773,    2.1,
	                  2.3414, 2.6726, 2.8688, 2.9706, 3.0009, 2.9743, 2.9015,
	                  2.7904, 2.6470, 2.4762, 2.2817, 2.0662, 1.8320, 1.5802,
	                  1.3116, 1.0263, 0.7239, 0.4033, 0.0630};*/

		/*double x[] = { 0, 2, 4, 5, 6, 7, 8, 9, 10};
	    //double y[] = { 0, 2, 4, 6, 8, 10, 12, 14, 16, 18, 20};
		double y[] = { 0,  4, 16,25,36,49,64,81,100};*/
		double x[] = { 0, 2};
		double y[] = { 0, 4};
		
		SCYTCZlineF F=getSCYTCZline(x,y,SCYTCZ_Compute.Condition.CLAMPED);
		System.out.println(F.getY_SCYTCZline(2.8));
	}
}


