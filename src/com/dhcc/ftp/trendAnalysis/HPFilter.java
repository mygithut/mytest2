package com.dhcc.ftp.trendAnalysis;

import com.dhcc.ftp.algorithmbasic.bzztfbb.BzztfbFunction;
import com.dhcc.ftp.algorithmbasic.matrix.Matrix;
import com.dhcc.ftp.util.CommonFunctions;


/**
 * 趋势分析——H-P滤波法 模型求解
 * @author wang
 *
 */
public class HPFilter {
	
	/**
	 * 常数系数(延迟调整系数)一般取1500;[依经验，年度数据取100；季度数据取1600；月度数据取14400,<周度数据取14400*(30/7)^2?>]
	 */
	public double k;
	
	/**
	 * 列向量y值数组 (活期存款总额历史数据组<包括当前日>，一般最细取到月度数据)
	 */
	public double[] y_data;
	
	/**
	 * y_data的间隔周期的天数(其实也是H-P滤波法历史实际数据的间隔周期的天数)
	 */
	public int termDays;
	
	/**
	 * H-P滤波法模型计算结果列向量值数组(与历史数据同频的过去趋势数组<包括当前日>),与y_data维数相同(趋势分析数据的时间点与y_data相同)
	 */
	public double[] g_data;
	
	/**
	 * H-P滤波法 构造函数
	 * @param k 常数系数(延迟调整系数)一般取1500;[依经验，年度数据取100；季度数据取1600；月度数据取14400]
	 * @param y_data double[] 列向量y值数组 (活期存款总额历史数据组<包括当前日>，一般最细取到月度数据)
	 * @param termDays y_data的间隔周期的天数(即历史实际数据的间隔周期的天数)
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
	 * 求解H-P滤波法数学模型(活期存款的核心存款<历史>趋势分析——H-P滤波法)
	 * @return double[] H-P滤波法模型计算结果列向量值数组(与历史数据同频的过去趋势数组),与y_data维数相同(趋势分析数据的时间点与y_data相同)
	 */
	public double[] computeModel() {
		if(k==0){
			System.out.println("H-P滤波法算法类实例化失败！无法进行计算！");
			return null;
		}
		int n=y_data.length;
		Matrix F = constructF(n);
		Matrix y=Matrix.constructY(y_data);
		/*if (F.colNum != y.rowNum) {
			System.out.println("      输入的参数矩阵F和y其中一个有错误，F的列数 " + F.colNum+ " 不等于y的行数 " + y.rowNum);
			return null;
		}*/

		String STime = CommonFunctions.GetCurrentTime();
		Matrix Fk = Matrix.multiConstants(F, k);
		System.out.println("   k*F耗时 "
				+ CommonFunctions.GetCostTimeInSecond(STime, CommonFunctions
						.GetCurrentTime()) + " 秒");

		// 构造n维单位矩阵
		Matrix I = Matrix.constructI(F.rowNum);

		STime = CommonFunctions.GetCurrentTime();
		// 相加后求逆
		Matrix revF = Matrix.rev(Matrix.add(Fk, I));
		System.out.println("   rev(Fk+I)耗时 "+ CommonFunctions.GetCostTimeInSecond(STime, CommonFunctions.GetCurrentTime()) + " 秒");

		// Matrix.printMatrixData(Matrix.rev(Matrix.add(Fk, I)));//打印求逆后的结果
		STime = CommonFunctions.GetCurrentTime();
		// 相乘
		Matrix g = Matrix.multiply(revF, y);
		double[] g_data=new double[g.rowNum];
		for(int i=0;i<g.rowNum;i++){
			g_data[i]=g.matrixData[i][0];
		}
		
		System.out.println("   multiply(revF, y)耗时 "+ CommonFunctions.GetCostTimeInSecond(STime, CommonFunctions.GetCurrentTime()) + " 秒");
		this.g_data=g_data;
		return g_data;

	}
	
	/**
	 * 根据H-P滤波法得到的过去趋势，来预测当前日某一段时间后的未来数据值。预测算法为简单的‘等时间段线性模拟’。
	 * @param deltaDays (int) 目标时刻距当前日的天数(就是要预测的未来的那一天距离当前日的天数)
	 * @return double futureData 预测目标结果值
	 */
	public double getFutureData(int deltaDays){
		if(g_data==null){
			System.out.println("H-P滤波法算法<过去>趋势分析计算失败！无法进行未来趋势预测计算！");
			return Double.NaN;
		}
		double futureData=0;//预测的未来目标点数据
		double pastData=0;//与未来目标点关于当前日对称的过去点数据
		double nowData=g_data[g_data.length-1];//当前日数据
		
		if(deltaDays%termDays==0){//预测目标日期点 关于当前日的对称点就在 过去趋势已知点上(即为g_data中的一个)
			int k=deltaDays/termDays;
			if((g_data.length-1)-k>=0){
				pastData=g_data[(g_data.length-1)-k];
			}else{
				System.out.print("(历史数据点不足，无法预测指定的未来目标点数据！)");
				return Double.NaN;
			}
			
			
		}else{//预测目标日期点 关于当前日的对称点不在 过去趋势整点上，而在期限周期点之间
			int k=deltaDays/termDays;
			int remain=deltaDays%termDays;
			if((g_data.length-1)-(k+1)>=0){
				double pastData1=g_data[(g_data.length-1)-(k+1)];//pastData时间序列左边靠近的已知趋势点数据
			    double pastData2=g_data[(g_data.length-1)-k];//pastData时间序列右边靠近的已知趋势点数据
			    pastData=pastData1+(pastData2-pastData1)*(1-(double)remain/termDays);
			}else{
				System.out.println("历史数据点不足，无法预测指定的未来目标点数据！");
				return Double.NaN;
			}
			
			
		}
		
		futureData=nowData+(nowData-pastData);
		
		return futureData;
	}
	
	/**
	 * 获取H-P滤波法分析对象(对象项目值变化曲线)的波动部分Ct的波动幅度值C
	 * @param a(double) 置信水平(1-a)；a一般取0.01，即置信水平为99%----99%的概率目标对象波动值不超过C
	 * @return C(double) 变化曲线波动部分Ct的波动幅度值C
	 */
	public double getC(double a){
		double C=0;
		if(g_data==null){
			System.out.println("H-P滤波法算法<过去>趋势分析计算失败！无法获取波动幅度值C！");
			return Double.NaN;
		}
		if(a<=0 || a>=1){
			System.out.println("置信水平(1-a)必须在0到1之间，输入参数a使其为此范围外，无法计算波动幅度值C！");
			return Double.NaN;
		}
		
		double sigma2=getSigma2();
		C=Math.sqrt(sigma2)*BzztfbFunction.N_(1-a/2);
		
		return C;
	}
	
	/**
	 * 获取H-P滤波法分析对象(对象项目值变化曲线)的波动部分Ct的波动幅度值C;<br>
	 * 默认置信水平(1-a)=99%；a一般取0.01，即置信水平为99%----99%的概率目标对象波动值不超过C
	 * @return C(double) 变化曲线波动部分Ct的波动幅度值C
	 */
	public double getC(){
		double a=0.01;
		double C=0;
		if(g_data==null){
			System.out.println("H-P滤波法算法<过去>趋势分析计算失败！无法获取波动幅度值C！");
			return Double.NaN;
		}
		if(a<=0 || a>=1){
			System.out.println("置信水平(1-a)必须在0到1之间，输入参数a使其为此范围外，无法计算波动幅度值C！");
			return Double.NaN;
		}
		
		double sigma2=getSigma2();
		C=Math.sqrt(sigma2)*BzztfbFunction.N_(1-a/2);
		
		return C;
	}
	
	/**
	 * 获取目标对象趋势变化的方差
	 * @return
	 */
	public double getSigma2(){
		double sigma2=0;
		if(g_data==null){
			System.out.println("H-P滤波法算法<过去>趋势分析计算失败！无法获取方差sigma2！");
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
		//System.out.println("方差sigma2="+sigma2);
		return sigma2;
	}
	
	/**
	  * 构造“趋势分析——H-P滤波法”数学模型中关键矩阵 F(n)[n*n的方阵];只要n确定了 F就唯一决定了
	  * @param n F的维数，必须大于0
	  * @return F 矩阵
	  */
	 private static Matrix constructF(int n){
		 double[][] f=new double[n][n];
		 if(n<=0){
			 System.out.println("输入的F矩阵维数参数n="+n+" 必须大于0！无法构造矩阵F");
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
		 }else{//当n>=5
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

		int n=50;//活期存款历史数据容量(历史数据的天数)
		//double k=1600;//常系数k,季度数据一般取1600
		double k=14400;//常系数k，月度数据取14400
		int termDays=30;//历史数据间隔周期 的天数 
		
		String STime=CommonFunctions.GetCurrentTime();
		//构造F方阵，F方阵只要维数n确定后，那么其值便固定
		Matrix F=constructF(n);
		if(F==null){
			return;
		}
		String ETime=CommonFunctions.GetCurrentTime();
		Matrix.printMatrixData(F,4);
		System.out.println("constructF耗时 "+CommonFunctions.GetCostTimeInSecond(STime, ETime)+" 秒");
		
		double[] yData=new double[n];
		for(int i=0;i<yData.length;i++){
			if(i<1){
				yData[i]=3;
			}else{
				yData[i]=6;
			}
			
		}		
		
	    STime=CommonFunctions.GetCurrentTime();
	    System.out.println("\n模型计算中，请等待... ");
	    //模型计算
	    HPFilter hp_filter=new HPFilter(k,yData,termDays);
		double[] g=hp_filter.computeModel();
		ETime=CommonFunctions.GetCurrentTime();
		
		Matrix.printMatrixData(Matrix.constructY(g),6);
		System.out.println("模型计算结束，总耗时 "+CommonFunctions.GetCostTimeInSecond(STime, ETime)+" 秒");

		//HPFilter hp_filter=new HPFilter(0,null,30);
		//double[] g_data={1,2,3,4,5};
		//hp_filter.g_data=g_data;
		System.out.println(hp_filter.getFutureData(15));
	}

}
