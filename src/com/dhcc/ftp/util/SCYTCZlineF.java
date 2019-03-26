package com.dhcc.ftp.util;

import com.dhcc.ftp.algorithmbasic.polynomial.PolynomialF;

/**
 * 三次样条插值结果函数---分段、连续、三次多项式
 * @author Administrator
 *
 */
public class SCYTCZlineF {
	/**
	 * 每段结果三次多项式的4个系数  数组
	 */
	public double[][] A;//每段结果三次多项式的4个系数  数组
	
	int n;//三次多项式的 分段总数
	/**
	 * 输入关键点x坐标值
	 */
	public double[] X;//输入关键点x坐标值
	
	/**
	 * 输入关键点y坐标值
	 */
	public double[] Y;//输入关键点y坐标值
	
	public SCYTCZlineF(){
		this.A=null;
		this.n=-1;
		this.X=null;
		this.Y=null;
	}
	
	public SCYTCZlineF(double[][] A,double[] X){
		this.A=A;
		this.n=A.length;
		this.X=X;
		this.Y=null;
	}
	
	public SCYTCZlineF(double[][] A,double[] X,double[] Y){
		this.A=A;
		this.n=A.length;
		this.X=X;
		this.Y=Y;
	}

	/**
	 * 已知x，求某一三次样条插值结果函数的 y值；如果该x不在三次样条函数 有效定义区间上则返回 NaN【当前收益率曲线的最左端点为1天，即1/30；最右端点为30年，即360】
	 *<br>后补：大于如果x大于30年(即360月)直接取30年的值，小于1/30.0时仍然返回NaN
	 * @param x 所求点的x坐标值，即期限长度，当前系统单位‘月’
	 * @return
	 */
	public double getY_SCYTCZline(double x){
		if(x>360){//如果x大于30年(即360月)直接取30年的值
			x=360;
		}
		double y=Double.NaN;
		for(int i=0;i<n;i++){
			if(x==X[i]){
				if(Y!=null){
					y=Y[i];
				}else{
					PolynomialF fi=new PolynomialF(A[i]);
					y=fi.getY(x-X[i]); 
				}				
				break;
			}else if(x>X[i] && x<X[i+1]){
				PolynomialF fi=new PolynomialF(A[i]);
				y=fi.getY(x-X[i]);
				break;
			}else if(x==X[i+1]){
				if(Y!=null){
					y=Y[i+1];
				}else{
					PolynomialF fi=new PolynomialF(A[i]);
					y=fi.getY(x-X[i]);
				}
				break;
			}
		}
		return y;
	}
	
	/**
	 * 打印三次样条插值函数
	 * @param f
	 */
	public static void print_SCYTCZline(SCYTCZlineF f){
		double[][] a=f.A;
		double[] x=f.X;
		for(int i = 0; i < a.length; i++){
			System.out.print("["+x[i]+","+x[i+1]+"]-----");
			PolynomialF pf=new PolynomialF(a[i]);
			System.out.println(pf.toString());
	    	
	    } 
	}
	
}
