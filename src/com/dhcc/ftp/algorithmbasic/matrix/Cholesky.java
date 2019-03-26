package com.dhcc.ftp.algorithmbasic.matrix;


/**
 *  �����Cholesky�ֽ�
 * @author wb
 *  
 * */
public class Cholesky {
      private double[] p;//�洢�ֽ��ľ���ĶԽ���Ԫ��
      private double[][] a;//�ֽ��������Ǿ���A=Lt*L��ʽ������L��������,aҲҪ�������ǡ�
      private int n;
      public Cholesky(double[][] a){
    	  if(a.length!=a[0].length){
    		  System.out.println("�ֽ�ľ����Ƿ�����");
    		  System.exit(0);
    	  }
    	  this.n=a.length;
    	 this.a=new double[a.length][a.length];
    	 this.p=new double[a.length];  	 
    	 initA(a);
    	 this.resolve();
      }
      /**
       *  ��ʼ������a��ʹ����һ�������Ǿ���
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
    				  if(sum<=0.0){System.out.println("�ֽ�ʧ��");
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
    	  a=Matrix.turn(new Matrix(a)).matrixData;//ת��ʹ��Ϊ�����Ǿ���
      }
      
      
	public double[] getP() {
		return p;
	}
	public double[][] getA() {
		return a;
	}
}
