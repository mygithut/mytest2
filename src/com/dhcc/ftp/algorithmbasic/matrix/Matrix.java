package com.dhcc.ftp.algorithmbasic.matrix;

import com.dhcc.ftp.util.CommonFunctions;


/**
 * ����
 * @author wang
 *
 */
public final class Matrix {
	/**
	 * �����ά��ֵ����
	 */
	public double[][] matrixData = null;
	/**
	 * ��������
	 */
	public int rowNum = 0;//����
	/**
	 * ��������
	 */
	public int colNum = 0;//����

	/**
	 * ���캯�� �������������������,����Ԫ�س�ʼֵȫΪ0
	 * @param i ��������
	 * @param j ��������
	 */
	public Matrix(int i, int j) {
		if ((i <= 0) || (j <= 0)) {
		    System.out.println("\n���캯�� The parameter is not legal");
			return;
		}
		rowNum = i;
		colNum = j;
		matrixData = new double[rowNum][colNum];
	}
	
	/**
	 * ���캯�� �ɶ�ά���鹹��һ������
	 * @param arg ��ά����
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
	 * //��þ�������(��ά����)
	 */
	public double[][] getMatrixData() {
		return this.matrixData;
	}
	
	/**
	 * //���õ�i�е�j��Ԫ��(��1��ʼ��)
	 */
	public void setElement(double data, int i, int j) {
		if ((i < 1) || (j < 1) || (i > rowNum) || (j > colNum)) {
			//System.out.println("\n���õ�i�е�j��Ԫ�� The parameter is unnormal");
			return;
		}
		matrixData[i - 1][j - 1] = data;

	}

	
	/**
	 * //��õ�i�е�j��Ԫ��(����1��ʼ��)
	 */
	public double getElement(int i, int j) {
		return matrixData[i - 1][j - 1];
	}

	/**
	 * �����(����)
	 */
	public int getRowNum() {
		return rowNum;
	}

	/**
	 * ������(����)
	 */
	public int getColNum() {
		return colNum;
	}

	/**
	 * ������(����)
	 * @param len
	 */
	public void setLength(int len) {
		if (len <= 0) {
		//	System.out.println("\n������ The length is legal");
			return;
		}
		rowNum = len;
		
	}

	/**
	 * �������(����)
	 * @param wid
	 */
	public void setWide(int wid) {
		if (wid <= 0) {
		//	System.out.println("\n������� The width is not legal");
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
	 * ��þ���ĵ�row����������
	 * @param row int Ҫ��ȡ������(��1��ʼ��û��0��)
	 */
	public double[] getRow(int row) {
		if ((row > rowNum) || (row < 1)) {
		//	System.out.println("\n��þ���ĵ�row�� parameter error");
			return null;
		}
		double[] temp = matrixData[row - 1];
		return temp;
	}

	
	/**
	 * //��þ���ĵ�col��
	 * @param col int Ҫ��ȡ������(��1��ʼ��û��0��)
	 */
	public double[] getCol(int col) {
		if ((col > colNum) || (col < 1)) {
			// System.out.println("\n��þ���ĵ�col�� parameter error");
			return null;
		}
		double[] temp = new double[rowNum];
		for (int i = 0; i < rowNum; i++)
			temp[i] = matrixData[i][col - 1];
		return temp;
	}

	//
	/**
	 * ���þ����row��
	 * @param data double[] Ҫ���óɵ�������������
	 * @param row int Ҫ�������õ�����(��1��ʼ��û��0��)
	 */
	public void setRow(double[] data, int row) {
		if ((row < 1) || (row > rowNum)) {
			//System.out.println("\n���þ����row�� parameter error");
			return;
		}
		int len = data.length;
		if (len != colNum) {
		//	System.out.println("\n���þ����row�� The data is not normal");
			return;
		}
		for (int i = 0; i < colNum; i++)
			matrixData[row - 1][i] = data[i];
	}

	
	/**
	 * //���þ����col��
	 *  @param data double[] Ҫ���óɵ�������������
	 *  @param col int Ҫ�������õ�����(��1��ʼ��û��0��)
	 */
	public void setCol(double[] data, int col) {
		if ((col < 1) || (col > colNum)) {
		//	System.out.println("\n���þ����col�� parameter error");
			return;
		}
		int len = data.length;
		if (len != rowNum) {
		//	System.out.println("\n���þ����col�� The data is not normal");
			return;
		}
		for (int i = 0; i < rowNum; i++)
			matrixData[i][col - 1] = data[i];
	}

	
	/**
	 * //���󽻻�����
	 * @param chgfrom int ������ԭλ������(��1��ʼ��ģ�û��0��)
	 * @param chgto int ��������λ������(��1��ʼ��ģ�û��0��)
	 */
	public void chgRow(int chgfrom, int chgto) {
		//double[] temp = null;
		if ((chgfrom > rowNum) || (chgto > rowNum) || (chgfrom < 1)
				|| (chgto < 1)) {
		//	System.out.println("\n���󽻻����� parameter error");
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
	 * ���󽻻�����
	 * @param chgfrom int ������ԭλ������(��1��ʼ��ģ�û��0��)
	 * @param chgto int ��������λ������(��1��ʼ��ģ�û��0��)
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
	 * //�����������,null:ʧ�ܣ�����ɹ�
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
	 * ��ӡ��������,��������Զ�������λС��
	 * @param matrix
	 * @return
	 */
	public static void printMatrixData(Matrix matrix){
		
		if(matrix==null){
			System.out.println("����Ϊnull���������ݿɴ�ӡ��");
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
	 * ��ӡ��������,���ݱ���ָ��С��λ��,���֧�ֱ���9λС�����������k����9λʱ�������κν�λ����ֱ�Ӵ�ӡȫ��
	 * @param matrix Ҫ��ӡ�ľ���
	 * @param k ��ӡ���Ҫ������С��λ��
	 * @return
	 */
	public static void printMatrixData(Matrix matrix,int k){
		
		if(matrix==null){
			System.out.println("����Ϊnull���������ݿɴ�ӡ��");
			return;
		}
		System.out.println("##############################################");
		for (int m = 0; m < matrix.rowNum; m++){
			for (int n = 0; n < matrix.colNum; n++){
				if(k>=0 && k<10){//����С��λ����֧�ַ�Χ���ڣ��ʹ�ӡָ���ı���С��λ�����
					if(k==0){//��ӡ������ʽ
						System.out.print("    "+(int)(doublecut(matrix.matrixData[m][n],k)));
					}else{
						System.out.print("    "+doublecut(matrix.matrixData[m][n],k));
					}
					
				}else{//����С��λ������֧�ַ�Χ��ֱ�Ӵ�ӡû�н�ȡ��ԭʼС������
					System.out.print("    "+matrix.matrixData[m][n]);
				}
				
			}
			System.out.println();
		}
		//System.out.println("##############################################");
		System.out.println();
	}

	//�������
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
	 * //�����������
	 * @param matrixSub1 ������
	 * @param matrixSub2 ����
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
	 * �������
	 * @param matrixAdd �����������matrixData
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
     * ����˳���
     * @param m Matrix ����
     * @param k double ����
     */
	public static Matrix multiConstants(Matrix m,double k) {
		Matrix resultMatrix=new Matrix(m.rowNum,m.colNum);
		//double[][] a=m.matrixData;//��ַ��ֵ���˶��߾���ͬһ�ڴ��ַ��������Ƕ��ѡ�����a��m.matrixData��������c���ַ���ݵĹ��ܣ���a��ֵ�仯ʱ��m.matrixData��ֵҲ����ű仯
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
	 * �����������
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
			System.out.println("\n����������� The compute is not legal��������1�������� ������� ������2 ��������");
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
	 * �����ת��
	 * */
	public static Matrix turn(Matrix matrix1) {
		Matrix resultTurn = null;
		int len = matrix1.getRowNum();
		int wid = matrix1.getColNum();
		if ((len <= 0) || (wid <= 0)) {
			//System.out.println("\n�����ת�� The matrix is not legal");
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
	 * �������棨���Ա任����
	 * */
	 public static Matrix rev(Matrix matrix){
		 int len = matrix.getRowNum();
		 int wid = matrix.getColNum();
		 if((len <= 0) || (wid <= 0) || (len != wid)){
			 System.out.println("\n�������棨���Ա任���� The matrix is not legal");
			 return null;
		 }
		 //����A|E����
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
		 
		 
		 //��ʼ��A|E���������Ա任
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
				 System.out.println("\n��������,���䲻����");
				 return null;
			 }
			 matrixA.chgRow(i,maxrow);
			 
			 //�Ե�i������һ������
			 for (int k=1; k<=wid*2; k++){
				 double temp = matrixA.getElement(i,k) / max;
				 matrixA.setElement(temp,i,k);
			 }
			 
			 //�������н��д���
			 //������������д���
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
			 
			 //������������д���
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
		 * ���ؽ��E|B����
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
		* С������λ����ȡ ����������,���֧�ֱ���9λС��������9λʱ�����κδ��� ֱ�ӷ���
		* @param d(double) Ҫ���н�λ��С��
		* @param n(int) ������С��λ��
		* @return ��λ��Ľ��
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
		     //ȥ��-0.0�������ֱ����ʾ0.0
		     if(d==0){
		    	 d=0;
		     }
		     return d;
	   }
	 
	 /**
	  * ���조���Ʒ�������H-P�˲�������ѧģ���йؼ����� F(n)[n*n�ķ���];ֻҪnȷ���� F��Ψһ������
	  * @param n F��ά�����������0
	  * @return F
	  */
	 public static Matrix constructF(int n){
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
	  * ����nά��λ����I
	  * @param n ��λ����ά��
	  * @return 
	  */
	 public static Matrix constructI(int n){
		 double[][] data=new double[n][n];
		 for(int i=0;i<n;i++){
			 for(int j=0;j<n;j++){
				 if(i==j){
					 data[i][j]=1;
				 }//����0Ԫ�ز������ã���Ϊdouble�������ʵ����ʱ��ʼֵȫΪ0
			 }
		 }
		 
		 Matrix I=new Matrix(data);
		 return I;
	 }
	 
	 /**
	  * ���쵥λ������Ix
	  * @param len ��λ�������ĳ���
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
	  * ��ָ��һά�������ݹ���������(����)X
	  * @param rowData ������������
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
	  * ���쵥λ������Iy
	  * @param len ��λ�������ĳ���
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
	  * ��ָ��һά�������ݹ���������(����)Y
	  * @param columeData ������������
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
	  * ��һ������A(�÷���һ��Ϊ�Գ���������)����Cholesky�ֽ�,�ֽ�Ϊһ�������Ǿ���L����ת�þ���(�����Ǿ���)�ĳ˻�����A=L*LT
	  * @param A
	  * @return Matrix L(�����Ǿ���)
	  */
	 public static Matrix cholesky(Matrix A){
		 double[][] a=A.matrixData;
		 
	   	 if(a.length!=a[0].length){
	   		 System.out.println("�ֽ�ľ����Ƿ���");
	   		 return null;
	   	 }
	   	 int n=a.length;
	   	 double[][] a1=new double[a.length][a.length];
	   	 double[] p=new double[a.length]; 
	   	 //��ʼ������a��ʹ�����Ϊһ����������ʽ��������
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
	   					 System.out.println("�þ�������ֽ�Ҫ��(������)��cholesky�ֽ�ʧ�ܣ�");
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
	   	  //Matrix A_up=turn(A_below);//ת��ʹ��Ϊ�����Ǿ���
	   	  //Matrix[] Result={A_below,A_up};
	   	  return A_below;
     }
	 
	 /**
	  * ������ģ��(���Ʒ�������H-P�˲���)
	  * @param k ����ϵ��
	  * @param F ��������F
	  * @param y ������
	  * @return
	  */
	 public static  Matrix computeModel(double k,Matrix F,Matrix y){
			if(F.colNum!=y.rowNum){
				System.out.println("      ����Ĳ�������F��y����һ���д���F������ "+F.colNum+" ������y������ "+y.rowNum);
				return null;
			}
			
			String STime=CommonFunctions.GetCurrentTime();
			Matrix Fk=Matrix.multiConstants(F,k);
			System.out.println("   k*F��ʱ "+CommonFunctions.GetCostTimeInSecond(STime, CommonFunctions.GetCurrentTime())+" ��");
			
			//����nά��λ����
			Matrix I=Matrix.constructI(F.rowNum);
			
			STime=CommonFunctions.GetCurrentTime();
			//��Ӻ�����
			Matrix revF=Matrix.rev(Matrix.add(Fk, I));
			System.out.println("   rev(Fk+I)��ʱ "+CommonFunctions.GetCostTimeInSecond(STime, CommonFunctions.GetCurrentTime())+" ��");
			
			
			//Matrix.printMatrixData(Matrix.rev(Matrix.add(Fk, I)));//��ӡ�����Ľ��
			STime=CommonFunctions.GetCurrentTime();
			//���
			Matrix g=Matrix.multiply(revF, y);
			System.out.println("   multiply(revF, y)��ʱ "+CommonFunctions.GetCostTimeInSecond(STime, CommonFunctions.GetCurrentTime())+" ��");
			return g;
			
		}
	 
	    /**
	     * ���þ���������ԭ�����Ax=bʽ�ķ�����
	     * @param a double[][] ������ϵ������Ķ�ά����ֵ
	     * @param b double[] �������Ҷ���������������ֵ
	     * @return x double[] ���������������ֵ
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
