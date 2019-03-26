package com.dhcc.ftp.util;


public class SCYTCZ_Compute {

	/**************************
	** 三次样条插值函数 java
	** 进行自然边界，
	**     夹持边界，
	**     非扭结边界
	** 条件下的插值
	** 2008-12-27
	**####### 依次返回各段三次多项式函数的4个参数值，且结果多项式函数值是在以下基础上取值：
	          “每段自变量区间[x1,x2]的左端点归元化，即每段左端点x1当做0，右端点x2化为(x2-x1)，中间x化为(x-x1)”
	**************************/
	
	/**
	 * 三次样条插值的边界条件
	 */
	 static enum Condition
	{
		 /**
		  * 自然边界条件
		  */
	    NATURAL,
	    /**
	     * 夹持边界条件
	     */
	    CLAMPED,
	    /**
	     * 非扭结边界
	     */
	    NOTaKNOT
	};
	
	/**
	 * 原始输入点信息类
	 * @author wang
	 *
	 */
	static class Point
	{
		
	    //Coefficient[] coe;//每段结果三次多项式的4个系数  数组
	    double[] xCoordinate;//原始输入点的x轴坐标数组,xCoordinate[]内不能出现重复数据 
	    double[] yCoordinate;//原始输入点的y轴坐标数组
	    double f0;//使用时暂时没用，直接赋值为0
	    double fn;//使用时暂时没用，直接赋值为0
	    int num;//原始输入点总个数
	    Condition con;//三次样条插值的边界条件
	};
	
	/**
	 * 已知一系列离散点 进行三次样条插值，获取三次样条插值结果函数(分段三次多项式)；
	 * <br>默认边界条件为‘非扭结边界’Condition.NOTaKNOT
	 * @param x 已知离散点 x值数组 <从小到大依序>  --- 已知点个数必须不少于3个
	 * @param y x对应的已知离散点 y值数组 <从小到大依序>
	 * @return
	 */
	public static SCYTCZlineF getSCYTCZline(double[] x,double[] y){
		if(x.length!=y.length){
			System.out.println("输入已知点坐标信息错误！x与y的个数不一致！");
			return null;
		}
		if(x.length<3){
			System.out.println("输入已知点个数为"+x.length+"，少于3个，不能进行三次样条插值计算！");
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
	 * 已知一系列离散点 进行三次样条插值，获取三次样条插值结果函数(分段三次多项式)
	 * @param x  已知离散点 x值数组 <从小到大依序>
	 * @param y  x对应的已知离散点 y值数组 <从小到大依序>
	 * @param con 
	 * @return
	 */
	public static SCYTCZlineF getSCYTCZline(double[] x,double[] y,Condition con){
		if(x.length!=y.length){
			System.out.println("输入已知点坐标信息错误！x与y的个数不一致！返回null");
			return null;
		}
		if(x.length<3){
			System.out.println("输入已知点个数为"+x.length+"，少于3个，不能进行三次样条插值计算！返回null");
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
	 * @param point 原始输入点信息类
	 * @return double[][] 每段结果三次多项式系数  double[i][0]为三次项系数、double[i][1]为二次项系数....
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
	    
	    a = (double *)malloc( (n + 1) * sizeof(double) );   特别使用,1--(n-1),       n 
	    b = (double *)malloc( (n + 1) * sizeof(double) );          0,1--(n-1),       n 
	    c = (double *)malloc( (n + 1) * sizeof(double) );          0,1--(n-1),特别使用 
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
	    /* 计算 h[] d[] */
	    for (i = 0; i < n; i++)
	    {
	        h[i] = x[i + 1] - x[i];
	        d[i] = (y[i + 1] - y[i]) / h[i];
	        /* printf("%f\t%f\n", h[i], d[i]); */
	    }
	    /**********************
	    ** 初始化系数增广矩阵
	    **********************/
	    a[0] = point.f0;
	    c[n] = point.fn;
	    /* 计算 a[] b[] d[] f[] */
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
	    ** 高斯消元
	    *************/
	    /* 第0行到第(n-3)行的消元 */
	    for(i = 0; i <= n - 3; i++)
	    {
	        /* 选主元 */
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
	    
	    /* 最后3行的消元 */
	    /* 选主元 */
	    if( Math.abs(a[n - 1]) > Math.abs(b[n - 2]) )
	    {
	        temp = a[n - 1]; a[n - 1] = b[n - 2]; b[n - 2] = temp;
	        temp = b[n - 1]; b[n - 1] = c[n - 2]; c[n - 2] = temp;
	        temp = c[n - 1]; c[n - 1] = a[n - 2]; a[n - 2] = temp;
	        temp = f[n - 1]; f[n - 1] = f[n - 2]; f[n - 2] = temp;
	    }
	    /* 选主元 */
	    if( Math.abs(c[n]) > Math.abs(b[n - 2]) )
	    {
	        temp = c[n]; c[n] = b[n - 2]; b[n - 2] = temp;
	        temp = a[n]; a[n] = c[n - 2]; c[n - 2] = temp;
	        temp = b[n]; b[n] = a[n - 2]; a[n - 2] = temp;
	        temp = f[n]; f[n] = f[n - 2]; f[n - 2] = temp;
	    }
	    /* 第(n-1)行消元 */
	    temp = a[n - 1] / b[n - 2];
	    a[n - 1] = 0;
	    b[n - 1] = b[n - 1] - temp * c[n - 2];
	    c[n - 1] = c[n - 1] - temp * a[n - 2];
	    f[n - 1] = f[n - 1] - temp * f[n - 2];
	    /* 第n行消元 */
	    temp = c[n] / b[n - 2];
	    c[n] = 0;
	    a[n] = a[n] - temp * c[n - 2];
	    b[n] = b[n] - temp * a[n - 2];
	    f[n] = f[n] - temp * f[n - 2];
	    /* 选主元 */
	    if( Math.abs(a[n]) > Math.abs(b[n - 1]) )
	    {
	        temp = a[n]; a[n] = b[n - 1]; b[n - 1] = temp;
	        temp = b[n]; b[n] = c[n - 1]; c[n - 1] = temp;
	        temp = f[n]; f[n] = f[n - 1]; f[n - 1] = temp;
	    }
	    /* 最后一次消元 */
	    temp = a[n] / b[n-1];
	    a[n] = 0;
	    b[n] = b[n] - temp * c[n - 1];
	    f[n] = f[n] - temp * f[n - 1];
	    
	    /* 回代求解 m[] */
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
	    ** 放置参数
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
	    /* x[]内不能出现重复数据 */
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


