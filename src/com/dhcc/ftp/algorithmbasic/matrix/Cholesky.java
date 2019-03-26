package com.dhcc.ftp.algorithmbasic.matrix;


/**
 *  矩阵的Cholesky分解
 * @author wb
 *  
 * */
public class Cholesky {
      private double[] p;//存储分解后的矩阵的对角线元素
      private double[][] a;//分解后的上三角矩阵A=Lt*L形式，这里L是上三角,a也要是上三角。
      private int n;
      public Cholesky(double[][] a){
    	  if(a.length!=a[0].length){
    		  System.out.println("分解的矩阵不是方正！");
    		  System.exit(0);
    	  }
    	  this.n=a.length;
    	 this.a=new double[a.length][a.length];
    	 this.p=new double[a.length];  	 
    	 initA(a);
    	 this.resolve();
      }
      /**
       *  初始化数组a，使数组一个上三角矩阵
       * */
      public void initA(double[][] a){
    	  for(int i=0;i<a.length;i++){
    		  for(int j=0;j<a.length;j++){
    			  if(j<i){this.a[i][j]=0.0;}
    			  if(j>=i){this.a[i][j]=a[i][j];}
    		  }
    	  }
      }
      public void resolve(){
          int k;
    	  double sum=0.0;
    	  for(int i=0;i<n;i++){
    		  for(int j=i;j<n;j++){
    			  for(sum=a[i][j], k=i-1;k>=0;k--){
    				  sum-=a[i][k]*a[j][k];
    			  }
    			  if(i==j){
    				  if(sum<=0.0){System.out.println("分解失败");
    				  System.exit(0);
    				  }
    				  p[i]=Math.sqrt(sum);
    			  }else{
    				  a[j][i]=sum/p[i];
    			  }
    		  }
    	  }
    	  for(int i=0;i<n;i++){
    		  for(int j=i+1;j<n;j++){
    			  a[i][j]=0.0;
    		  }
    		  a[i][i]=p[i];
    	  }
    	  a=Matrix.turn(new Matrix(a)).matrixData;//转置使成为上三角矩阵。
      }
      
      
	public double[] getP() {
		return p;
	}
	public double[][] getA() {
		return a;
	}
}
