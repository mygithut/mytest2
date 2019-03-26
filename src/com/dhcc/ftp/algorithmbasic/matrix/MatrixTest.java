package com.dhcc.ftp.algorithmbasic.matrix;

import com.dhcc.ftp.util.CommonFunctions;


/**
 * 矩阵各种计算测试专用类
 * @author wang
 *
 */
public class MatrixTest {

	public static void main(String arg[]){
		choleskyTest();
		computeFormula_Axb();
		
	}
	/**
	 * 矩阵基本计算(加、减、乘、逆)测试
	 */
	public static void basicFunctions(){
		double[][] d1={{1,0,0},{0,1,0},{0,0,1}};//单位矩阵(一定是方阵) n*n
		double[][] d2={{1,2,3},{3,2,1},{3,4,3}};//一般方阵 n*n
		double[][] d3={{1},{1},{1}};//列向量数据 n*1
		double[][] d4={{1,1,1}};//行向量数据 1*n
		Matrix m1=new Matrix(d1);
		Matrix m2=new Matrix(d2);
		Matrix m3=new Matrix(d3);
		Matrix m4=new Matrix(d4);
		
		Matrix mResult;//=new Matrix(m1.rowNum,m1.colNum);
		
		//mResult=Matrix.add(m1,m2);
		//mResult=Matrix.subtract(m2, m1);
		//mResult= Matrix.multiply(m4,m2);
		mResult=Matrix.rev(m2);//注意 参数矩阵必须是可逆矩阵<->满秩矩阵(经过初等行变换或列变换后，没有全部为0的行或列)<->模|M|不等于0
		                       //可逆矩阵一定是方阵，也只有方阵才能求其模(即行列式)
		
		if(mResult!=null){
			Matrix.printMatrixData(mResult);
			System.out.println("mResult.rowNum = "+mResult.rowNum);
		}else{
			System.out.println("计算失败！");
		}
		
		
		Matrix.printMatrixData(Matrix.constructI(3));//单位矩阵
		Matrix.printMatrixData(Matrix.constructIx(6));//单位行向量
		
		double[] a={2,2,2,2,2,2};
		Matrix.printMatrixData(Matrix.constructX(a));//数值行向量
		Matrix.printMatrixData(Matrix.constructIy(6));//单位列向量
		Matrix.printMatrixData(Matrix.constructY(a));//数值列向量
		Matrix.printMatrixData(Matrix.constructF(6));//F
		
	}
	
	/**
	 * 趋势分析——h-p滤波法的矩阵模型测试
	 */
	public static void kFy(){
		String STime,ETime;
		
		int n=500;
		double k=1600;//常系数k
		STime=CommonFunctions.GetCurrentTime();
		//构造F方阵，F方阵只要维数n确定后，那么其值便固定
		Matrix F=Matrix.constructF(n);
		if(F==null){
			return;
		}
		ETime=CommonFunctions.GetCurrentTime();
		Matrix.printMatrixData(F,0);
		System.out.println("constructF耗时 "+CommonFunctions.GetCostTimeInSecond(STime, ETime)+" 秒");
		
		double[] yData=new double[n];
		for(int i=0;i<yData.length;i++){
			if(i<1){
				yData[i]=3;
			}else{
				yData[i]=6;
			}
			
		}
		
		//构造列向量Y
		Matrix y=Matrix.constructY(yData);
		
	    STime=CommonFunctions.GetCurrentTime();
	    System.out.println("\n模型计算中，请等待... ");
	    //模型计算
		Matrix g=Matrix.computeModel(k,F,y);
		ETime=CommonFunctions.GetCurrentTime();
		
		Matrix.printMatrixData(g,6);
		System.out.println("模型计算结束，总耗时 "+CommonFunctions.GetCostTimeInSecond(STime, ETime)+" 秒");
	}
	
	/**
	 * 乔累斯基(cholesky)矩阵分解测试
	 */
	public static void choleskyTest(){
		double[][] a={{3,2,3},{2,2,0},{3,0,12}};//对称正定矩阵,对称就一定是方阵
		Matrix A=new Matrix(a);
		Matrix L=Matrix.cholesky(A);
		Matrix.printMatrixData(L,6);
		Matrix A_=Matrix.multiply(L, Matrix.turn(L));
		Matrix.printMatrixData(A_,6);
	}
	
	/**
	 * 一次多元等式方程组求解测试
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
