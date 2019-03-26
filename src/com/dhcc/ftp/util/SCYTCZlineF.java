package com.dhcc.ftp.util;

import com.dhcc.ftp.algorithmbasic.polynomial.PolynomialF;

/**
 * ����������ֵ�������---�ֶΡ����������ζ���ʽ
 * @author Administrator
 *
 */
public class SCYTCZlineF {
	/**
	 * ÿ�ν�����ζ���ʽ��4��ϵ��  ����
	 */
	public double[][] A;//ÿ�ν�����ζ���ʽ��4��ϵ��  ����
	
	int n;//���ζ���ʽ�� �ֶ�����
	/**
	 * ����ؼ���x����ֵ
	 */
	public double[] X;//����ؼ���x����ֵ
	
	/**
	 * ����ؼ���y����ֵ
	 */
	public double[] Y;//����ؼ���y����ֵ
	
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
	 * ��֪x����ĳһ����������ֵ��������� yֵ�������x���������������� ��Ч�����������򷵻� NaN����ǰ���������ߵ�����˵�Ϊ1�죬��1/30�����Ҷ˵�Ϊ30�꣬��360��
	 *<br>�󲹣��������x����30��(��360��)ֱ��ȡ30���ֵ��С��1/30.0ʱ��Ȼ����NaN
	 * @param x ������x����ֵ�������޳��ȣ���ǰϵͳ��λ���¡�
	 * @return
	 */
	public double getY_SCYTCZline(double x){
		if(x>360){//���x����30��(��360��)ֱ��ȡ30���ֵ
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
	 * ��ӡ����������ֵ����
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
