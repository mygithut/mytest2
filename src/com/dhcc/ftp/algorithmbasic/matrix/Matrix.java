package com.dhcc.ftp.algorithmbasic.matrix;

import com.dhcc.ftp.util.CommonFunctions;


/**
 * 矩阵
 * @author wang
 *
 */
public final class Matrix {
	/**
	 * 矩阵二维数值数组
	 */
	public double[][] matrixData = null;
	/**
	 * 矩阵行数
	 */
	public int rowNum = 0;//行数
	/**
	 * 矩阵列数
	 */
	public int colNum = 0;//列数

	/**
	 * 构造函数 由行数和列数构造矩阵,矩阵元素初始值全为0
	 * @param i 矩阵行数
	 * @param j 矩阵列数
	 */
	public Matrix(int i, int j) {
		if ((i <= 0) || (j <= 0)) {
		    System.out.println("\n构造函数 The parameter is not legal");
			return;
		}
		rowNum = i;
		colNum = j;
		matrixData = new double[rowNum][colNum];
	}
	
	/**
	 * 构造函数 由二维数组构建一个矩阵
	 * @param arg 二维数组
	 */
	public Matrix(double[][] arg) {
		
		rowNum = arg.length;
		colNum = arg[0].length;
		matrixData = new double[rowNum][colNum];
		for (int m = 0; m < rowNum; m++)
			for (int n = 0; n < colNum; n++)
				matrixData[m][n] = arg[m][n];
	}

	/**
	 * //获得矩阵数据(二维数组)
	 */
	public double[][] getMatrixData() {
		return this.matrixData;
	}
	
	/**
	 * //设置第i行第j列元素(从1开始算)
	 */
	public void setElement(double data, int i, int j) {
		if ((i < 1) || (j < 1) || (i > rowNum) || (j > colNum)) {
			//System.out.println("\n设置第i行第j列元素 The parameter is unnormal");
			return;
		}
		matrixData[i - 1][j - 1] = data;

	}

	
	/**
	 * //获得第i行第j列元素(都从1开始算)
	 */
	public double getElement(int i, int j) {
		return matrixData[i - 1][j - 1];
	}

	/**
	 * 获得阵长(行数)
	 */
	public int getRowNum() {
		return rowNum;
	}

	/**
	 * 获得阵宽(列数)
	 */
	public int getColNum() {
		return colNum;
	}

	/**
	 * 设置阵长(行数)
	 * @param len
	 */
	public void setLength(int len) {
		if (len <= 0) {
		//	System.out.println("\n设置阵长 The length is legal");
			return;
		}
		rowNum = len;
		
	}

	/**
	 * 设置阵宽(列数)
	 * @param wid
	 */
	public void setWide(int wid) {
		if (wid <= 0) {
		//	System.out.println("\n设置阵宽 The width is not legal");
			return;
		}
		colNum = wid;
		/*
		if (wid<=width) {
			width = wid;
			return;
		}
		double[][] tmp = new double[length][wid]; //extend memory.
		for (int i=0; i<length; i++) {//Copy
			for (int j=0; j<width; j++) {
				tmp[i][j] = matrix[i][j];
			}
		}
		
		matrix = tmp;
		width = wid;
		*/
	}

	
	/**
	 * 获得矩阵的第row行数据数组
	 * @param row int 要获取的行数(从1开始，没有0行)
	 */
	public double[] getRow(int row) {
		if ((row > rowNum) || (row < 1)) {
		//	System.out.println("\n获得矩阵的第row行 parameter error");
			return null;
		}
		double[] temp = matrixData[row - 1];
		return temp;
	}

	
	/**
	 * //获得矩阵的第col列
	 * @param col int 要获取的列数(从1开始，没有0列)
	 */
	public double[] getCol(int col) {
		if ((col > colNum) || (col < 1)) {
			// System.out.println("\n获得矩阵的第col列 parameter error");
			return null;
		}
		double[] temp = new double[rowNum];
		for (int i = 0; i < rowNum; i++)
			temp[i] = matrixData[i][col - 1];
		return temp;
	}

	//
	/**
	 * 设置矩阵第row行
	 * @param data double[] 要设置成的新行数据数组
	 * @param row int 要进行设置的行数(从1开始，没有0行)
	 */
	public void setRow(double[] data, int row) {
		if ((row < 1) || (row > rowNum)) {
			//System.out.println("\n设置矩阵第row行 parameter error");
			return;
		}
		int len = data.length;
		if (len != colNum) {
		//	System.out.println("\n设置矩阵第row行 The data is not normal");
			return;
		}
		for (int i = 0; i < colNum; i++)
			matrixData[row - 1][i] = data[i];
	}

	
	/**
	 * //设置矩阵第col列
	 *  @param data double[] 要设置成的新列数据数组
	 *  @param col int 要进行设置的列数(从1开始，没有0列)
	 */
	public void setCol(double[] data, int col) {
		if ((col < 1) || (col > colNum)) {
		//	System.out.println("\n设置矩阵第col列 parameter error");
			return;
		}
		int len = data.length;
		if (len != rowNum) {
		//	System.out.println("\n设置矩阵第col列 The data is not normal");
			return;
		}
		for (int i = 0; i < rowNum; i++)
			matrixData[i][col - 1] = data[i];
	}

	
	/**
	 * //矩阵交换两行
	 * @param chgfrom int 交换的原位置行数(从1开始算的，没有0行)
	 * @param chgto int 交换到的位置行数(从1开始算的，没有0行)
	 */
	public void chgRow(int chgfrom, int chgto) {
		//double[] temp = null;
		if ((chgfrom > rowNum) || (chgto > rowNum) || (chgfrom < 1)
				|| (chgto < 1)) {
		//	System.out.println("\n矩阵交换两行 parameter error");
			return;
		}
		double[] temp = new double[colNum];
		for (int i = 0; i < colNum; i++) {
			temp[i] = matrixData[chgfrom - 1][i];
			matrixData[chgfrom - 1][i] = matrixData[chgto - 1][i];
			matrixData[chgto - 1][i] = temp[i];
		}
	}

	
	/**
	 * 矩阵交换两列
	 * @param chgfrom int 交换的原位置列数(从1开始算的，没有0列)
	 * @param chgto int 交换到的位置列数(从1开始算的，没有0列)
	 */
	public void chgCol(int chgfrom, int chgto) {
		if ((chgfrom > colNum) || (chgto > colNum) || (chgfrom < 1)
				|| (chgto < 1)) {
		//	System.out.println("\n parameter error");
			return;
		}
		double[] temp = new double[rowNum];
		for (int i = 0; i < rowNum; i++) {
			temp[i] = matrixData[i][chgfrom - 1];
			matrixData[i][chgfrom - 1] = matrixData[i][chgto - 1];
			matrixData[i][chgto - 1] = temp[i];
		}
	}
	
	/**
	 * //两个矩阵求和,null:失败；否则成功
	 * @param matrixAdd1
	 * @param matrixAdd2
	 * @return
	 */
	public static Matrix add(Matrix matrixAdd1,Matrix matrixAdd2) {
		int lenghAdd1 = matrixAdd1.rowNum;
		int widthAdd1 = matrixAdd1.colNum;
		int lenghAdd2 = matrixAdd2.rowNum;
		int widthAdd2 = matrixAdd2.colNum;
		if ((lenghAdd1 != lenghAdd2) || (widthAdd1 != widthAdd2)) {
			//System.out.println("THe parameter is wrong,two Matrix can not do add operation");
			return null;
		}
		double[][] matrixData1 = new double[lenghAdd1][widthAdd1];
		double[][] matrixData2 = new double[lenghAdd2][widthAdd2];
		matrixData1 = matrixAdd1.matrixData;
		matrixData2 = matrixAdd2.matrixData;
		double[][] matrixData=new double[lenghAdd1][widthAdd1];
		for (int i = 0; i < lenghAdd1; i++)
			for (int j = 0; j < widthAdd1; j++) {
				matrixData[i][j] = matrixData1[i][j] + matrixData2[i][j];
			}
		return new Matrix(matrixData);
	}
	
	/**
	 * 打印矩阵数据,结果数据自动保留两位小数
	 * @param matrix
	 * @return
	 */
	public static void printMatrixData(Matrix matrix){
		
		if(matrix==null){
			System.out.println("矩阵为null！其无数据可打印！");
			return;
		}
		System.out.println("##############################################");
		for (int m = 0; m < matrix.rowNum; m++){
			for (int n = 0; n < matrix.colNum; n++){
				System.out.print("    "+doublecut(matrix.matrixData[m][n],2));
				//System.out.print("    "+matrix.matrixData[m][n]);
			}
			System.out.println();
		}
		//System.out.println("##############################################");
		System.out.println();
	}
	
	/**
	 * 打印矩阵数据,数据保留指定小数位数,最高支持保留9位小数，输入参数k大于9位时，不做任何截位处理，直接打印全部
	 * @param matrix 要打印的矩阵
	 * @param k 打印结果要保留的小数位数
	 * @return
	 */
	public static void printMatrixData(Matrix matrix,int k){
		
		if(matrix==null){
			System.out.println("矩阵为null！其无数据可打印！");
			return;
		}
		System.out.println("##############################################");
		for (int m = 0; m < matrix.rowNum; m++){
			for (int n = 0; n < matrix.colNum; n++){
				if(k>=0 && k<10){//保留小数位数在支持范围以内，就打印指定的保留小数位数结果
					if(k==0){//打印整数形式
						System.out.print("    "+(int)(doublecut(matrix.matrixData[m][n],k)));
					}else{
						System.out.print("    "+doublecut(matrix.matrixData[m][n],k));
					}
					
				}else{//保留小数位数超出支持范围，直接打印没有截取的原始小数数据
					System.out.print("    "+matrix.matrixData[m][n]);
				}
				
			}
			System.out.println();
		}
		//System.out.println("##############################################");
		System.out.println();
	}

	//矩阵求和
	public int add(double[][] matrixAdd) {
		double[] sap = matrixAdd[0];
		if(matrixAdd.length<this.rowNum||sap.length<this.colNum){
			return -1;		
		}
		for (int i = 0; i < this.rowNum; i++)
			for (int j = 0; j < this.colNum; j++) {
				matrixData[i][j] = matrixData[i][j] + matrixAdd[i][j];
			}
		return 1;
	}

	/**
	 * //两个矩阵求差
	 * @param matrixSub1 被减数
	 * @param matrixSub2 减数
	 * @return
	 */
    
	public static Matrix subtract(Matrix matrixSub1,Matrix matrixSub2) {
		int lengthSub1 = matrixSub1.rowNum;
		int widthSub1 = matrixSub1.colNum;
		int lengthSub2 = matrixSub2.rowNum;
		int widthSub2 = matrixSub2.colNum;
		if ((lengthSub1 != lengthSub2) || (widthSub1 != widthSub2)) {
			//System.out.println("THe parameter is wrong,two Matrix can not do Sub operation");
			return null;
		}
		double[][] matrixData1 = new double[lengthSub1][widthSub1];
		double[][] matrixData2 = new double[lengthSub2][widthSub2];
		matrixData1 = matrixSub1.matrixData;
		matrixData2 = matrixSub2.matrixData;
		double[][] matrixData = new double[lengthSub1][widthSub1];
		for (int i = 0; i < lengthSub1; i++)
			for (int j = 0; j < widthSub1; j++)
			{
				matrixData[i][j] = matrixData1[i][j] - matrixData2[i][j];
			}
		return new Matrix(matrixData);
	}

	/**
	 * 矩阵求差
	 * @param matrixAdd 被减数矩阵的matrixData
	 * @return
	 */
	public int subtract(double[][] matrixAdd) {
		double[] sap = matrixAdd[0];
		if(matrixAdd.length<this.rowNum||sap.length<this.colNum){
			return -1;		
		}

		for (int i = 0; i < this.rowNum; i++)
			for (int j = 0; j < this.colNum; j++) {
				matrixData[i][j] = matrixData[i][j] - matrixAdd[i][j];
			}
		return 1;
	}
	
    /**
     * 矩阵乘常数
     * @param m Matrix 矩阵
     * @param k double 常数
     */
	public static Matrix multiConstants(Matrix m,double k) {
		Matrix resultMatrix=new Matrix(m.rowNum,m.colNum);
		//double[][] a=m.matrixData;//地址赋值，此二者就是同一内存地址的两个标记而已。这里a和m.matrixData具有类似c里地址传递的功能，当a的值变化时，m.matrixData的值也会跟着变化
		//double[][] x=a;
		//x[0][0]=100;
		for (int i = 1; i <= m.getRowNum(); i++){
			for (int j = 1; j <=m.getColNum(); j++) {
				resultMatrix.setElement(m.getElement(i, j)*k, i, j);
				//resultMatrix.setElement(a[i-1][j-1]*k, i, j);
			}
		}		
		return resultMatrix;
	}

	/**
	 * 
	 * 两个矩阵相乘
	 * */
	public static Matrix multiply(Matrix matrix1, Matrix matrix2) {
		Matrix ResultMul = null;
		int length1 = matrix1.getRowNum();
		int width1 = matrix1.getColNum();
		int length2 = matrix2.getRowNum();
		int width2 = matrix2.getColNum();
		if ((length1 <= 0)
			|| (length2 <= 0)
			|| (width1 <= 0)
			|| (width2 <= 0)
			|| (width1 != length2)) {
			System.out.println("\n两个矩阵相乘 The compute is not legal：“矩阵1的列数” 必须等于 “矩阵2 的行数”");
			return null;
		}
		ResultMul = new Matrix(length1,width2);
		for (int i = 1; i <= length1; i++) {
			for (int j = 1; j <= width2; j++) {
				double temp = 0;
				for (int k = 1; k <= width1; k++) {
					temp = temp + matrix1.getElement(i, k) * matrix2.getElement(k, j);
				}
				ResultMul.setElement(temp, i, j);
			}
		}
		return ResultMul;
	}
	
	
	/**
	 * 
	 * 矩阵的转置
	 * */
	public static Matrix turn(Matrix matrix1) {
		Matrix resultTurn = null;
		int len = matrix1.getRowNum();
		int wid = matrix1.getColNum();
		if ((len <= 0) || (wid <= 0)) {
			//System.out.println("\n矩阵的转置 The matrix is not legal");
			return null;
		}
		resultTurn = new Matrix(wid,len);
		resultTurn.setLength(wid);
		resultTurn.setWide(len);
		for (int i = 1; i <= wid; i++) {
			for (int j = 1; j <= len; j++) {
				double temp = matrix1.getElement(j, i);
				resultTurn.setElement(temp, i, j);
			}
		}
		return resultTurn;
	}

	/**
	 * 
	 * 矩阵求逆（线性变换法）
	 * */
	 public static Matrix rev(Matrix matrix){
		 int len = matrix.getRowNum();
		 int wid = matrix.getColNum();
		 if((len <= 0) || (wid <= 0) || (len != wid)){
			 System.out.println("\n矩阵求逆（线性变换法） The matrix is not legal");
			 return null;
		 }
		 //构造A|E矩阵
		 Matrix matrixA = new Matrix(len,wid*2);
		 for (int i=1; i<=len; i++)
			 for (int j=1; j<=wid; j++){
			 matrixA.setElement(matrix.getElement(i,j),i,j);
		 }
		 
		 for (int i=1; i<=len; i++)
			 for (int j=wid+1; j<=wid*2; j++){
			 if (i==j-wid){
				 matrixA.setElement(1,i,j);
			 }
			 else 
				 matrixA.setElement(0,i,j);
		 }
		 
		 
		 //开始对A|E矩阵作线性变换
		 for (int i=1; i<=len; i++){
			 double max = matrixA.getElement(i,i);
			 double absmax = Math.abs(max);
			 
			 int maxrow = i;
			 for (int j=i; j<=len; j++){
				 double max2 = matrixA.getElement(j,i);
				 double absmax2 = Math.abs(max2);
				 if (absmax2>absmax){
					 maxrow = j;
					 max = max2;
					 absmax = absmax2;
				 }
			 }
			 //System.out.println(max);
			 if (absmax==0){
				 System.out.println("\n矩阵不满秩,即其不可逆");
				 return null;
			 }
			 matrixA.chgRow(i,maxrow);
			 
			 //对第i行作归一化处理
			 for (int k=1; k<=wid*2; k++){
				 double temp = matrixA.getElement(i,k) / max;
				 matrixA.setElement(temp,i,k);
			 }
			 
			 //对其他行进行处理
			 //对上三角阵进行处理
			 for (int m=1; m<i; m++){
				 double para = matrixA.getElement(m,i);
				 if (para!=0){
					 for (int n=1; n<=wid*2; n++){
						 double num = matrixA.getElement(m,n);
						 num = num - matrixA.getElement(i,n) * para;
						 matrixA.setElement(num,m,n);
					 }
				 }
			 }
			 
			 //对下三角阵进行处理
			 for (int m=i+1; m<=len; m++){
				 double para = matrixA.getElement(m,i);
				 if (para!=0){
					 for (int n=1; n<=wid*2; n++){
						 double num = matrixA.getElement(m,n);
						 num = num - matrixA.getElement(i,n) * para;
						 matrixA.setElement(num,m,n);
					 }
				 }
			 }
			 /////printMatrixData(matrixA);
			 //System.out.println("##############################################");
		 }
		 /*
		 * 返回结果E|B矩阵
		 * */
		 Matrix matrixB = new Matrix(len, wid);
		 for (int i = 1; i <= len; i++)
			 for (int j = wid+1; j <= wid*2; j++) {
				 double temp = matrixA.getElement(i,j);
				 matrixB.setElement(temp,i,j-wid);
			 }
		 return matrixB;
	 }
	 
	 /**
		* 小数保留位数截取 并四舍五入,最高支持保留9位小数，大于9位时不做任何处理 直接返回
		* @param d(double) 要进行截位的小数
		* @param n(int) 保留的小数位数
		* @return 截位后的结果
		*/
	 public static double doublecut(double d,int n){
	    	if(d==Double.POSITIVE_INFINITY || d==Double.NEGATIVE_INFINITY ||d==Double.NaN){
				 return  Double.NaN;
			 }
			 
			 if(n>=10){
				 return d;
			 }
			 boolean isLowZero=false;
			 if(d<0){
				 d=-d;
				 isLowZero=true;
			 }
			 long jishu=(int)Math.pow(10, n);
		     long longd=(long)(d*jishu);
		     if(d*jishu>=(longd+0.5)){
		    	 longd++;
		     }
		     d=longd/(double)jishu;
		     
		     if(isLowZero){
		    	 d=-d;
		     }
		     //去除-0.0的情况，直接显示0.0
		     if(d==0){
		    	 d=0;
		     }
		     return d;
	   }
	 
	 /**
	  * 构造“趋势分析——H-P滤波法”数学模型中关键矩阵 F(n)[n*n的方阵];只要n确定了 F就唯一决定了
	  * @param n F的维数，必须大于0
	  * @return F
	  */
	 public static Matrix constructF(int n){
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
	  * 构造n维单位矩阵I
	  * @param n 单位矩阵维数
	  * @return 
	  */
	 public static Matrix constructI(int n){
		 double[][] data=new double[n][n];
		 for(int i=0;i<n;i++){
			 for(int j=0;j<n;j++){
				 if(i==j){
					 data[i][j]=1;
				 }//其余0元素不用设置，因为double数组对象实例化时初始值全为0
			 }
		 }
		 
		 Matrix I=new Matrix(data);
		 return I;
	 }
	 
	 /**
	  * 构造单位行向量Ix
	  * @param len 单位行向量的长度
	  * @return 
	  */
	 public static Matrix constructIx(int len){
		 double[][] data=new double[1][len];
		 for(int j=0;j<len;j++){
			 data[0][j]=1;
		 }
		 
		 Matrix Ix=new Matrix(data);
		 return Ix;
	 }
	 
	 /**
	  * 由指定一维数组数据构造行向量(矩阵)X
	  * @param rowData 行向量的数据
	  * @return 
	  */
	 public static Matrix constructX(double[] rowData){
		 double[][] data=new double[1][rowData.length];
		 for(int j=0;j<rowData.length;j++){
			 data[0][j]=rowData[j];
		 }
		 
		 Matrix X=new Matrix(data);
		 return X;
	 }
	 
	 /**
	  * 构造单位列向量Iy
	  * @param len 单位列向量的长度
	  * @return 
	  */
	 public static Matrix constructIy(int len){
		 double[][] data=new double[len][1];
		 for(int i=0;i<len;i++){
			 data[i][0]=1;
		 }
		 
		 Matrix Iy=new Matrix(data);
		 return Iy;
	 }
	  
	 /**
	  * 由指定一维数组数据构造列向量(矩阵)Y
	  * @param columeData 列向量的数据
	  * @return 
	  */
	 public static Matrix constructY(double[] columeData){
		 double[][] data=new double[columeData.length][1];
		 for(int i=0;i<columeData.length;i++){
			 data[i][0]=columeData[i];
		 }
		 
		 Matrix Y=new Matrix(data);
		 return Y;
	 }
	 
	 /**
	  * 对一个方阵A(该方阵一般为对称正定矩阵)进行Cholesky分解,分解为一个下三角矩阵L与其转置矩阵(上三角矩阵)的乘积，即A=L*LT
	  * @param A
	  * @return Matrix L(下三角矩阵)
	  */
	 public static Matrix cholesky(Matrix A){
		 double[][] a=A.matrixData;
		 
	   	 if(a.length!=a[0].length){
	   		 System.out.println("分解的矩阵不是方阵！");
	   		 return null;
	   	 }
	   	 int n=a.length;
	   	 double[][] a1=new double[a.length][a.length];
	   	 double[] p=new double[a.length]; 
	   	 //初始化数组a，使数组变为一个上三角形式矩阵数组
	   	 for(int i=0;i<n;i++){
	   		 for(int j=0;j<n;j++){
	   			 if(j<i){
	   				 a1[i][j]=0.0;
	   			 }
  			     if(j>=i){
  			    	 a1[i][j]=a[i][j];
  			     }
  		     }
  	     }
	   	 int k;
	   	 double sum=0.0;
	   	 for(int i=0;i<n;i++){
	   		 for(int j=i;j<n;j++){
	   			 for(sum=a1[i][j], k=i-1;k>=0;k--){
	   				 sum-=a1[i][k]*a1[j][k];
	   			 }
	   			 if(i==j){
	   				 if(sum<=0.0){
	   					 System.out.println("该矩阵不满足分解要求(非正定)，cholesky分解失败！");
	   				     return null;
	   				  }
	   				  p[i]=Math.sqrt(sum);
	   			 }else{
	   				  a1[j][i]=sum/p[i];
	   			 }
	   		  }
	   	  }
	   	  for(int i=0;i<n;i++){
	   		  for(int j=i+1;j<n;j++){
	   			  a1[i][j]=0.0;
	   		  }
	   		  a1[i][i]=p[i];
	   	  }
	   	  Matrix A_below=new Matrix(a1);
	   	  //Matrix A_up=turn(A_below);//转置使成为上三角矩阵
	   	  //Matrix[] Result={A_below,A_up};
	   	  return A_below;
     }
	 
	 /**
	  * 求解矩阵模型(趋势分析——H-P滤波法)
	  * @param k 常数系数
	  * @param F 参数矩阵F
	  * @param y 列向量
	  * @return
	  */
	 public static  Matrix computeModel(double k,Matrix F,Matrix y){
			if(F.colNum!=y.rowNum){
				System.out.println("      输入的参数矩阵F和y其中一个有错误，F的列数 "+F.colNum+" 不等于y的行数 "+y.rowNum);
				return null;
			}
			
			String STime=CommonFunctions.GetCurrentTime();
			Matrix Fk=Matrix.multiConstants(F,k);
			System.out.println("   k*F耗时 "+CommonFunctions.GetCostTimeInSecond(STime, CommonFunctions.GetCurrentTime())+" 秒");
			
			//构造n维单位矩阵
			Matrix I=Matrix.constructI(F.rowNum);
			
			STime=CommonFunctions.GetCurrentTime();
			//相加后求逆
			Matrix revF=Matrix.rev(Matrix.add(Fk, I));
			System.out.println("   rev(Fk+I)耗时 "+CommonFunctions.GetCostTimeInSecond(STime, CommonFunctions.GetCurrentTime())+" 秒");
			
			
			//Matrix.printMatrixData(Matrix.rev(Matrix.add(Fk, I)));//打印求逆后的结果
			STime=CommonFunctions.GetCurrentTime();
			//相乘
			Matrix g=Matrix.multiply(revF, y);
			System.out.println("   multiply(revF, y)耗时 "+CommonFunctions.GetCostTimeInSecond(STime, CommonFunctions.GetCurrentTime())+" 秒");
			return g;
			
		}
	 
	    /**
	     * 运用矩阵逆运算原理，求解Ax=b式的方程组
	     * @param a double[][] 方程组系数矩阵的二维数组值
	     * @param b double[] 方程组右端项列向量的数组值
	     * @return x double[] 方程组计算结果数组值
	     */
	    public static double[] computeFormula_Axb(double[][] a,double[] b){
			Matrix A=new Matrix(a);
			Matrix B=Matrix.constructY(b);
			Matrix X=Matrix.multiply(Matrix.rev(A), B);
			double[] x=new double[X.rowNum];
			for(int i=0;i<x.length;i++){
				x[i]=X.matrixData[i][0];
			}
			return x;		
		}
}
