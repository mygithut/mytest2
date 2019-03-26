package com.dhcc.ftp.algorithmbasic.matrix;

import com.dhcc.ftp.util.CommonFunctions;


/**
 * ������ּ������ר����
 * @author wang
 *
 */
public class MatrixTest {

	public static void main(String arg[]){
		choleskyTest();
		computeFormula_Axb();
		
	}
	/**
	 * �����������(�ӡ������ˡ���)����
	 */
	public static void basicFunctions(){
		double[][] d1={{1,0,0},{0,1,0},{0,0,1}};//��λ����(һ���Ƿ���) n*n
		double[][] d2={{1,2,3},{3,2,1},{3,4,3}};//һ�㷽�� n*n
		double[][] d3={{1},{1},{1}};//���������� n*1
		double[][] d4={{1,1,1}};//���������� 1*n
		Matrix m1=new Matrix(d1);
		Matrix m2=new Matrix(d2);
		Matrix m3=new Matrix(d3);
		Matrix m4=new Matrix(d4);
		
		Matrix mResult;//=new Matrix(m1.rowNum,m1.colNum);
		
		//mResult=Matrix.add(m1,m2);
		//mResult=Matrix.subtract(m2, m1);
		//mResult= Matrix.multiply(m4,m2);
		mResult=Matrix.rev(m2);//ע�� ������������ǿ������<->���Ⱦ���(���������б任���б任��û��ȫ��Ϊ0���л���)<->ģ|M|������0
		                       //�������һ���Ƿ���Ҳֻ�з����������ģ(������ʽ)
		
		if(mResult!=null){
			Matrix.printMatrixData(mResult);
			System.out.println("mResult.rowNum = "+mResult.rowNum);
		}else{
			System.out.println("����ʧ�ܣ�");
		}
		
		
		Matrix.printMatrixData(Matrix.constructI(3));//��λ����
		Matrix.printMatrixData(Matrix.constructIx(6));//��λ������
		
		double[] a={2,2,2,2,2,2};
		Matrix.printMatrixData(Matrix.constructX(a));//��ֵ������
		Matrix.printMatrixData(Matrix.constructIy(6));//��λ������
		Matrix.printMatrixData(Matrix.constructY(a));//��ֵ������
		Matrix.printMatrixData(Matrix.constructF(6));//F
		
	}
	
	/**
	 * ���Ʒ�������h-p�˲����ľ���ģ�Ͳ���
	 */
	public static void kFy(){
		String STime,ETime;
		
		int n=500;
		double k=1600;//��ϵ��k
		STime=CommonFunctions.GetCurrentTime();
		//����F����F����ֻҪά��nȷ������ô��ֵ��̶�
		Matrix F=Matrix.constructF(n);
		if(F==null){
			return;
		}
		ETime=CommonFunctions.GetCurrentTime();
		Matrix.printMatrixData(F,0);
		System.out.println("constructF��ʱ "+CommonFunctions.GetCostTimeInSecond(STime, ETime)+" ��");
		
		double[] yData=new double[n];
		for(int i=0;i<yData.length;i++){
			if(i<1){
				yData[i]=3;
			}else{
				yData[i]=6;
			}
			
		}
		
		//����������Y
		Matrix y=Matrix.constructY(yData);
		
	    STime=CommonFunctions.GetCurrentTime();
	    System.out.println("\nģ�ͼ����У���ȴ�... ");
	    //ģ�ͼ���
		Matrix g=Matrix.computeModel(k,F,y);
		ETime=CommonFunctions.GetCurrentTime();
		
		Matrix.printMatrixData(g,6);
		System.out.println("ģ�ͼ���������ܺ�ʱ "+CommonFunctions.GetCostTimeInSecond(STime, ETime)+" ��");
	}
	
	/**
	 * ����˹��(cholesky)����ֽ����
	 */
	public static void choleskyTest(){
		double[][] a={{3,2,3},{2,2,0},{3,0,12}};//�Գ���������,�Գƾ�һ���Ƿ���
		Matrix A=new Matrix(a);
		Matrix L=Matrix.cholesky(A);
		Matrix.printMatrixData(L,6);
		Matrix A_=Matrix.multiply(L, Matrix.turn(L));
		Matrix.printMatrixData(A_,6);
	}
	
	/**
	 * һ�ζ�Ԫ��ʽ������������
	 */
	public static void computeFormula_Axb(){
		double[][] a={{12,78,650},{78,650,6084},{650,6084,60710}};//
		double[] b={1662,11392,109750};
		Matrix A=new Matrix(a);
		Matrix B=Matrix.constructY(b);
		Matrix X=Matrix.multiply(Matrix.rev(A), B);
		Matrix.printMatrixData(X,2);
		
	}
	
}
