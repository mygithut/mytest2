package com.dhcc.ftp.algorithmbasic.polynomial;

/**
 * 多项式函数
 * @author wang
 *
 */
public class PolynomialF {
	/**
	 * 多项式的次数(即最高次数)
	 */
	public int n;//多项式的次数(即最高次数)
	
	/**
	 * 多项式系数数组，a[0]为n次项(最高次项)系数,...,a[n-1]为1次项系数,a[n]为常数项。
	 */
	public double[] a;//多项式系数数组，a[0]为n次项(最高次项)系数,...,a[n-1]为1次项系数,a[n]为常数项。
	
	/**
	 * 构造函数，已知多项式次数n来构造一个多项式函数，此时系数数组a[]都初始为0
	 * @param n int 多项式次数
	 */
	public PolynomialF(int n){
		this.n=n;
		this.a=new double[n+1];
	}
	
	/**
	 * 构造函数，已知多项式系数数组a来构造一个多项式函数，此时多项式次数由多项式系数数组a的长度来决定(即a.length-1)
	 * @param a double[] 多项式系数数组； 【注意】多项式最高次数项系数a[0]不能为0
	 */
	public PolynomialF(double[] a){
		if(a.length==0){
			System.out.println("多项式系数数组a的长度为0，无法构造多项式函数！");
			this.n=a.length-1;
		}else{		
			this.a=a;
		    this.n=a.length-1;		
		}
		
	}
	
	/**
	 * 构造函数，已知多项式次数n和多项式系数数组a来构造一个多项式函数，此时多项式次数由多项式系数数组a的长度来决定(即a.length-1)
	 * @param a double[] 多项式系数数组；  【注意】多项式最高次数项系数a[0]不能为0
	 */
	public PolynomialF(int n,double[] a){
		if(a.length==(n+1)){			
			this.a=a;
		    this.n=a.length-1;					
		}else{
			System.out.println("多项式次数n与多项式系数数组a的长度不符！");
		}
		
	}
	
	/**
	 * 打印多项式，格式为4.0x^4+x^3+2.0x^2-x+3.0；其中^表示次方
	 * @param f 要打印的多项式
	 */
	public static void printPolynomialF(PolynomialF f){
		String str="";
		//从多项式的最高次幂开始打印 直到0次幂
		for(int i=f.n;i>=0;i--){
			if(i!=f.n){
				if(i==0){
				   if(f.a[f.n-i]>0){
					   str+="+"+f.a[f.n-i];
					   //System.out.print("+"+f.a[f.n-i]);
					}else if(f.a[f.n-i]==0){
						str+="";
						//System.out.print("");
					}else{
						str+=f.a[f.n-i];
						//System.out.print(f.a[f.n-i]);
					}
					
				}else if(i==1){
					if(f.a[f.n-i]>0){
						if(f.a[f.n-i]==1){
							str+="+x";
							//System.out.print("+x");
						}else{
							str+="+"+f.a[f.n-i]+"x";
							//System.out.print("+"+f.a[f.n-i]+"x");
						}
						
					}else if(f.a[f.n-i]==0){
						str+="";
						//System.out.print("");
					}else{
						if(f.a[f.n-i]==-1){
							str+="-x";
							//System.out.print("-x");
						}else{
							str+=f.a[f.n-i]+"x";
							//System.out.print(f.a[f.n-i]+"x");
						}
						
					}
				}else{
					if(f.a[f.n-i]>0){
						if(f.a[f.n-i]==1){
							str+="+x^"+i;
							//System.out.print("+x^"+i);
						}else{
							str+="+"+f.a[f.n-i]+"x^"+i;
							//System.out.print("+"+f.a[f.n-i]+"x^"+i);
						}
						
					}else if(f.a[f.n-i]==0){
						str+="";
						//System.out.print("");
					}else{
						if(f.a[f.n-i]==-1){
							str+="-x^"+i;
							//System.out.print("-x^"+i);
						}else{
							str+=f.a[f.n-i]+"x^"+i;
							//System.out.print(f.a[f.n-i]+"x^"+i);
						}
						
					}
				}
				
			}else if(f.n==0){
				str+=f.a[f.n-i];
				//System.out.print(f.a[f.n-i]);
			}else if(f.n==1){
				if(f.a[f.n-i]>0){
					if(f.a[f.n-i]==1){
						str+="x";
						//System.out.print("x");
					}else{
						str+=f.a[f.n-i]+"x";
						//System.out.print(f.a[f.n-i]+"x");
					}					
				}else if(f.a[f.n-i]==0){
					str+="";
					//System.out.print("");
				}else{
					if(f.a[f.n-i]==-1){
						str+="-x";
						//System.out.print("-x");
					}else{
						str+=f.a[f.n-i]+"x";
						//System.out.print(f.a[f.n-i]+"x");
					}
					
				}
			}else{
				if(f.a[f.n-i]>0){
					if(f.a[f.n-i]==1){
						str+="x^"+i;
						//System.out.print("x^"+i);
					}else{
						str+=f.a[f.n-i]+"x^"+i;
						//System.out.print(f.a[f.n-i]+"x^"+i);
					}
					
				}else if(f.a[f.n-i]==0){
					str+="";
					//System.out.print("");
				}else{
					if(f.a[f.n-i]==-1){
						str+="-x^"+i;
						//System.out.print("-x^"+i);
					}else{
						str+=f.a[f.n-i]+"x^"+i;
						//System.out.print(f.a[f.n-i]+"x^"+i);
					}
					
				}
			}
			
		}
		//System.out.println();
		if(str.startsWith("+")){
			str=str.substring(1);
		}
		System.out.println(str);
	}
	
	/**
	 * 打印多项式的系数列向量(从最高次到0次幂)，格式为{2,0,2,0}；则此表示多项式2x^3+2.0x
	 * @param f 要打印的多项式
	 */
	public static void printA(PolynomialF f){		
		String str="";
		for(int i=f.n;i>=0;i--){
			if(i==f.n){
				str+="{"+f.a[f.n-i]+",";
			}else{
				str+=f.a[f.n-i]+",";
			}		
		}
		str=str.substring(0,str.length()-1);
		str+="}";
		System.out.println(str);
	}
	
	/**
	 * 将多项式函数转换为通俗字符串表示形式，格式为4.0x^4+x^3+2.0x^2-x+3.0；其中^表示次方
	 */
	public String toString(){
		String str="";
		for(int i=n;i>=0;i--){
			if(i!=n){
				if(i==0){
				   if(a[n-i]>0){
					   str+="+"+a[n-i];
					}else if(a[n-i]==0){
						str+="";
					}else{
						str+=a[n-i];
					}
					
				}else if(i==1){
					if(a[n-i]>0){
						if(a[n-i]==1){
							str+="+x";							
						}else{
							str+="+"+a[n-i]+"x";
						}
						
					}else if(a[n-i]==0){
						str+="";						
					}else{
						if(a[n-i]==-1){
							str+="-x";							
						}else{
							str+=a[n-i]+"x";							
						}
						
					}
				}else{
					if(a[n-i]>0){
						if(a[n-i]==1){
							str+="+x^"+i;							
						}else{
							str+="+"+a[n-i]+"x^"+i;							
						}
						
					}else if(a[n-i]==0){
						str+="";						
					}else{
						if(a[n-i]==-1){
							str+="-x^"+i;							
						}else{
							str+=a[n-i]+"x^"+i;							
						}
						
					}
				}
				
			}else if(n==0){
				str+=a[n-i];				
			}else if(n==1){
				if(a[n-i]>0){
					if(a[n-i]==1){
						str+="x";						
					}else{
						str+=a[n-i]+"x";						
					}					
				}else if(a[n-i]==0){
					str+="";					
				}else{
					if(a[n-i]==-1){
						str+="-x";						
					}else{
						str+=a[n-i]+"x";						
					}
					
				}
			}else{
				if(a[n-i]>0){
					if(a[n-i]==1){
						str+="x^"+i;						
					}else{
						str+=a[n-i]+"x^"+i;						
					}
					
				}else if(a[n-i]==0){
					str+="";					
				}else{
					if(a[n-i]==-1){
						str+="-x^"+i;						
					}else{
						str+=a[n-i]+"x^"+i;						
					}
					
				}
			}
			
		}
		//去掉最前面可能的“+”
		if(str.startsWith("+")){
			str=str.substring(1);
		}
		return str;
	}
	
	/**
	 * 求多项式函数的函数值	
	 * @param x double 自变量x的取值
	 * @return double 函数值y
	 */
	public double getY(double x){
		double y=Double.NaN;
		for(int i=0;i<=n;i++){
			if(i==0){
				y=a[n-i];
			}else{
				y+=a[n-i]*Math.pow(x, i);
			}
			
		}
		return y;
	}
	
	/**
	 * 两个多项式相加
	 * @param f1
	 * @param f2
	 * @return
	 */
	public static PolynomialF add(PolynomialF f1,PolynomialF f2){
		int max_n=Math.max(f1.n, f2.n);
		int min_n=Math.min(f1.n, f2.n);
		PolynomialF F=new PolynomialF(max_n);
		for(int i=0;i<=max_n;i++){
			if(i<=min_n){
				F.a[F.n-i]=f1.a[f1.n-i]+f2.a[f2.n-i];
			}else{
				if(f1.n>f2.n){
					F.a[F.n-i]=f1.a[f1.n-i];
				}else{
					F.a[F.n-i]=f2.a[f2.n-i];
				}
				
			}			
		}
		return F;
	}
	
	/**
	 * 两个多项式相减
	 * @param f1
	 * @param f2
	 * @return
	 */
	public static PolynomialF subtract(PolynomialF f1,PolynomialF f2){
		int max_n=Math.max(f1.n, f2.n);
		int min_n=Math.min(f1.n, f2.n);
		PolynomialF F=new PolynomialF(max_n);
		for(int i=0;i<=max_n;i++){
			if(i<=min_n){
				F.a[F.n-i]=f1.a[f1.n-i]-f2.a[f2.n-i];
			}else{
				if(f1.n>f2.n){
					F.a[F.n-i]=f1.a[f1.n-i];
				}else{
					F.a[F.n-i]=-f2.a[f2.n-i];
				}
				
			}			
		}
		return F;
	}
	
	/**
	 * 多项式求导
	 * @param f
	 * @return
	 */
	public static PolynomialF derivation(PolynomialF f){
		
		PolynomialF F=new PolynomialF(f.n-1);
		for(int i=0;i<=F.n;i++){
			F.a[F.n-i]=f.a[f.n-i-1]*(i+1);
		}
		
		return F;
	}
	
	/**
	 * 多项式对系数求偏导
	 * @param f PolynomialF 要求偏导的多项式
	 * @param m int 要求偏导的那个系数对应项的次数
	 * @return 
	 */
	public static PolynomialF partialDerivation(PolynomialF f,int m){
		if(m>f.n){
			System.out.println("输入的目标偏导项次数"+m+"大于该多项式的最高次数"+f.n+",偏导结果直接为0");
			return new PolynomialF(0);
		}
		PolynomialF F=new PolynomialF(m);
		F.a[0]=1;	
		return F;
	}
	
	
	
   //单独测试程序
	public static void main(String[] args) {
		double[] a1={4,1,2,-1,3};//f(x)=2*x^2-x+3
		PolynomialF f1=new PolynomialF(a1);
		System.out.print("f1=");
		PolynomialF.printPolynomialF(f1);
		System.out.println(f1.getY(1));
		
		/*double[] a2={2,-1,3};//f(x)=2*x^2-x+3
		PolynomialF f2=new PolynomialF(a2);
		System.out.print("f2=");
		PolynomialF.printPolynomialF(f2);
		
		System.out.print("f1+f2=");
		PolynomialF f=PolynomialF.add(f1, f2);
		PolynomialF.printPolynomialF(f);
		
		System.out.print("f2-f1=");
		PolynomialF f_subtract=PolynomialF.subtract(f2, f1);
		PolynomialF.printPolynomialF(f_subtract);
		
		PolynomialF.printPolynomialF(PolynomialF.derivation(f2));
		
		PolynomialF.printPolynomialF(PolynomialF.partialDerivation(f1, 3));*/
		
	}
}
