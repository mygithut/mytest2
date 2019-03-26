package com.dhcc.ftp.trendAnalysis;

import com.dhcc.ftp.algorithmbasic.bzztfbb.BzztfbFunction;
import com.dhcc.ftp.algorithmbasic.matrix.Matrix;
import com.dhcc.ftp.util.CommonFunctions;


/**
 * ���Ʒ�������H-P�˲��� ģ�����
 * @author wang
 *
 */
public class HPFilter {
	
	/**
	 * ����ϵ��(�ӳٵ���ϵ��)һ��ȡ1500;[�����飬�������ȡ100����������ȡ1600���¶�����ȡ14400,<�ܶ�����ȡ14400*(30/7)^2?>]
	 */
	public double k;
	
	/**
	 * ������yֵ���� (���ڴ���ܶ���ʷ������<������ǰ��>��һ����ϸȡ���¶�����)
	 */
	public double[] y_data;
	
	/**
	 * y_data�ļ�����ڵ�����(��ʵҲ��H-P�˲�����ʷʵ�����ݵļ�����ڵ�����)
	 */
	public int termDays;
	
	/**
	 * H-P�˲���ģ�ͼ�����������ֵ����(����ʷ����ͬƵ�Ĺ�ȥ��������<������ǰ��>),��y_dataά����ͬ(���Ʒ������ݵ�ʱ�����y_data��ͬ)
	 */
	public double[] g_data;
	
	/**
	 * H-P�˲��� ���캯��
	 * @param k ����ϵ��(�ӳٵ���ϵ��)һ��ȡ1500;[�����飬�������ȡ100����������ȡ1600���¶�����ȡ14400]
	 * @param y_data double[] ������yֵ���� (���ڴ���ܶ���ʷ������<������ǰ��>��һ����ϸȡ���¶�����)
	 * @param termDays y_data�ļ�����ڵ�����(����ʷʵ�����ݵļ�����ڵ�����)
	 */
	public HPFilter(double k, double[] y_data, int termDays){
		this.termDays=termDays;
		if((y_data==null)?true:(y_data.length==0)){
			this.k=0;
			return;
		}
		this.k=k;
		this.y_data=y_data;
	}

	/**
	 * ���H-P�˲�����ѧģ��(���ڴ��ĺ��Ĵ��<��ʷ>���Ʒ�������H-P�˲���)
	 * @return double[] H-P�˲���ģ�ͼ�����������ֵ����(����ʷ����ͬƵ�Ĺ�ȥ��������),��y_dataά����ͬ(���Ʒ������ݵ�ʱ�����y_data��ͬ)
	 */
	public double[] computeModel() {
		if(k==0){
			System.out.println("H-P�˲����㷨��ʵ����ʧ�ܣ��޷����м��㣡");
			return null;
		}
		int n=y_data.length;
		Matrix F = constructF(n);
		Matrix y=Matrix.constructY(y_data);
		/*if (F.colNum != y.rowNum) {
			System.out.println("      ����Ĳ�������F��y����һ���д���F������ " + F.colNum+ " ������y������ " + y.rowNum);
			return null;
		}*/

		String STime = CommonFunctions.GetCurrentTime();
		Matrix Fk = Matrix.multiConstants(F, k);
		System.out.println("   k*F��ʱ "
				+ CommonFunctions.GetCostTimeInSecond(STime, CommonFunctions
						.GetCurrentTime()) + " ��");

		// ����nά��λ����
		Matrix I = Matrix.constructI(F.rowNum);

		STime = CommonFunctions.GetCurrentTime();
		// ��Ӻ�����
		Matrix revF = Matrix.rev(Matrix.add(Fk, I));
		System.out.println("   rev(Fk+I)��ʱ "+ CommonFunctions.GetCostTimeInSecond(STime, CommonFunctions.GetCurrentTime()) + " ��");

		// Matrix.printMatrixData(Matrix.rev(Matrix.add(Fk, I)));//��ӡ�����Ľ��
		STime = CommonFunctions.GetCurrentTime();
		// ���
		Matrix g = Matrix.multiply(revF, y);
		double[] g_data=new double[g.rowNum];
		for(int i=0;i<g.rowNum;i++){
			g_data[i]=g.matrixData[i][0];
		}
		
		System.out.println("   multiply(revF, y)��ʱ "+ CommonFunctions.GetCostTimeInSecond(STime, CommonFunctions.GetCurrentTime()) + " ��");
		this.g_data=g_data;
		return g_data;

	}
	
	/**
	 * ����H-P�˲����õ��Ĺ�ȥ���ƣ���Ԥ�⵱ǰ��ĳһ��ʱ����δ������ֵ��Ԥ���㷨Ϊ�򵥵ġ���ʱ�������ģ�⡯��
	 * @param deltaDays (int) Ŀ��ʱ�̾൱ǰ�յ�����(����ҪԤ���δ������һ����뵱ǰ�յ�����)
	 * @return double futureData Ԥ��Ŀ����ֵ
	 */
	public double getFutureData(int deltaDays){
		if(g_data==null){
			System.out.println("H-P�˲����㷨<��ȥ>���Ʒ�������ʧ�ܣ��޷�����δ������Ԥ����㣡");
			return Double.NaN;
		}
		double futureData=0;//Ԥ���δ��Ŀ�������
		double pastData=0;//��δ��Ŀ�����ڵ�ǰ�նԳƵĹ�ȥ������
		double nowData=g_data[g_data.length-1];//��ǰ������
		
		if(deltaDays%termDays==0){//Ԥ��Ŀ�����ڵ� ���ڵ�ǰ�յĶԳƵ���� ��ȥ������֪����(��Ϊg_data�е�һ��)
			int k=deltaDays/termDays;
			if((g_data.length-1)-k>=0){
				pastData=g_data[(g_data.length-1)-k];
			}else{
				System.out.print("(��ʷ���ݵ㲻�㣬�޷�Ԥ��ָ����δ��Ŀ������ݣ�)");
				return Double.NaN;
			}
			
			
		}else{//Ԥ��Ŀ�����ڵ� ���ڵ�ǰ�յĶԳƵ㲻�� ��ȥ���������ϣ������������ڵ�֮��
			int k=deltaDays/termDays;
			int remain=deltaDays%termDays;
			if((g_data.length-1)-(k+1)>=0){
				double pastData1=g_data[(g_data.length-1)-(k+1)];//pastDataʱ��������߿�������֪���Ƶ�����
			    double pastData2=g_data[(g_data.length-1)-k];//pastDataʱ�������ұ߿�������֪���Ƶ�����
			    pastData=pastData1+(pastData2-pastData1)*(1-(double)remain/termDays);
			}else{
				System.out.println("��ʷ���ݵ㲻�㣬�޷�Ԥ��ָ����δ��Ŀ������ݣ�");
				return Double.NaN;
			}
			
			
		}
		
		futureData=nowData+(nowData-pastData);
		
		return futureData;
	}
	
	/**
	 * ��ȡH-P�˲�����������(������Ŀֵ�仯����)�Ĳ�������Ct�Ĳ�������ֵC
	 * @param a(double) ����ˮƽ(1-a)��aһ��ȡ0.01��������ˮƽΪ99%----99%�ĸ���Ŀ����󲨶�ֵ������C
	 * @return C(double) �仯���߲�������Ct�Ĳ�������ֵC
	 */
	public double getC(double a){
		double C=0;
		if(g_data==null){
			System.out.println("H-P�˲����㷨<��ȥ>���Ʒ�������ʧ�ܣ��޷���ȡ��������ֵC��");
			return Double.NaN;
		}
		if(a<=0 || a>=1){
			System.out.println("����ˮƽ(1-a)������0��1֮�䣬�������aʹ��Ϊ�˷�Χ�⣬�޷����㲨������ֵC��");
			return Double.NaN;
		}
		
		double sigma2=getSigma2();
		C=Math.sqrt(sigma2)*BzztfbFunction.N_(1-a/2);
		
		return C;
	}
	
	/**
	 * ��ȡH-P�˲�����������(������Ŀֵ�仯����)�Ĳ�������Ct�Ĳ�������ֵC;<br>
	 * Ĭ������ˮƽ(1-a)=99%��aһ��ȡ0.01��������ˮƽΪ99%----99%�ĸ���Ŀ����󲨶�ֵ������C
	 * @return C(double) �仯���߲�������Ct�Ĳ�������ֵC
	 */
	public double getC(){
		double a=0.01;
		double C=0;
		if(g_data==null){
			System.out.println("H-P�˲����㷨<��ȥ>���Ʒ�������ʧ�ܣ��޷���ȡ��������ֵC��");
			return Double.NaN;
		}
		if(a<=0 || a>=1){
			System.out.println("����ˮƽ(1-a)������0��1֮�䣬�������aʹ��Ϊ�˷�Χ�⣬�޷����㲨������ֵC��");
			return Double.NaN;
		}
		
		double sigma2=getSigma2();
		C=Math.sqrt(sigma2)*BzztfbFunction.N_(1-a/2);
		
		return C;
	}
	
	/**
	 * ��ȡĿ��������Ʊ仯�ķ���
	 * @return
	 */
	public double getSigma2(){
		double sigma2=0;
		if(g_data==null){
			System.out.println("H-P�˲����㷨<��ȥ>���Ʒ�������ʧ�ܣ��޷���ȡ����sigma2��");
			return Double.NaN;
		}
		
		double[] ct=new double[y_data.length];
		for(int i=0;i<ct.length;i++){
			ct[i]=y_data[i]-g_data[i];
		}
		
		for(int i=0;i<ct.length;i++){
			sigma2+=Math.pow(ct[i], 2);
		}
		sigma2=sigma2*1/ct.length;
		//System.out.println("����sigma2="+sigma2);
		return sigma2;
	}
	
	/**
	  * ���조���Ʒ�������H-P�˲�������ѧģ���йؼ����� F(n)[n*n�ķ���];ֻҪnȷ���� F��Ψһ������
	  * @param n F��ά�����������0
	  * @return F ����
	  */
	 private static Matrix constructF(int n){
		 double[][] f=new double[n][n];
		 if(n<=0){
			 System.out.println("�����F����ά������n="+n+" �������0���޷��������F");
			 return null;
		 }
		 if(n==1 || n==2){
			 f[0][0]=0;
		 }else if(n==3){
			 double[][] temp={{1,-2,1},{-2,4,-2},{1,-2,1}};
			 f=temp;
		 }else if(n==4){
			 double[][] temp={{1,-2,1,0},{-2,5,-4,1},{1,-4,5,-2},{0,1,-2,1}};
			 f=temp;
		 }else{//��n>=5
			 double[] temp0={1,-2,1};
			 for(int k=0;k<n;k++){
				 if(k>=temp0.length){
					 f[0][k]=0;
				 }else{
					 f[0][k]=temp0[k];
				 }			 
			 }
			 
			 double[] temp1={-2,5,-4,1};
			 for(int k=0;k<n;k++){
				 if(k>=temp1.length){
					 f[1][k]=0;
				 }else{
					 f[1][k]=temp1[k];
				 }				 
			 }
			 
			 double[] tempn_2={1,-4,5,-2};
			 for(int k=n-1;k>=0;k--){
				 if(k>=n-tempn_2.length){
					 f[n-2][k]=tempn_2[k-n+tempn_2.length];
				 }else{
					 f[n-2][k]=0;
				 }				 
			 }
			 
			 double[] tempn_1={0,1,-2,1};
			 for(int k=n-1;k>=0;k--){
				 if(k>=n-tempn_1.length){
					 f[n-1][k]=tempn_1[k-n+tempn_1.length];
				 }else{
					 f[n-1][k]=0;
				 }				 
			 }
			 
			 double[] u={1,-4,6,-4,1};
			 for(int i=2;i<n-2;i++){
				 for(int j=i-2;j<(i-2)+5;j++){
					 f[i][j]=u[j-(i-2)];				 
				 }
			 }
		 }
		 Matrix F=new Matrix(f);
		 return F;
	 }

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		int n=50;//���ڴ����ʷ��������(��ʷ���ݵ�����)
		//double k=1600;//��ϵ��k,��������һ��ȡ1600
		double k=14400;//��ϵ��k���¶�����ȡ14400
		int termDays=30;//��ʷ���ݼ������ ������ 
		
		String STime=CommonFunctions.GetCurrentTime();
		//����F����F����ֻҪά��nȷ������ô��ֵ��̶�
		Matrix F=constructF(n);
		if(F==null){
			return;
		}
		String ETime=CommonFunctions.GetCurrentTime();
		Matrix.printMatrixData(F,4);
		System.out.println("constructF��ʱ "+CommonFunctions.GetCostTimeInSecond(STime, ETime)+" ��");
		
		double[] yData=new double[n];
		for(int i=0;i<yData.length;i++){
			if(i<1){
				yData[i]=3;
			}else{
				yData[i]=6;
			}
			
		}		
		
	    STime=CommonFunctions.GetCurrentTime();
	    System.out.println("\nģ�ͼ����У���ȴ�... ");
	    //ģ�ͼ���
	    HPFilter hp_filter=new HPFilter(k,yData,termDays);
		double[] g=hp_filter.computeModel();
		ETime=CommonFunctions.GetCurrentTime();
		
		Matrix.printMatrixData(Matrix.constructY(g),6);
		System.out.println("ģ�ͼ���������ܺ�ʱ "+CommonFunctions.GetCostTimeInSecond(STime, ETime)+" ��");

		//HPFilter hp_filter=new HPFilter(0,null,30);
		//double[] g_data={1,2,3,4,5};
		//hp_filter.g_data=g_data;
		System.out.println(hp_filter.getFutureData(15));
	}

}
