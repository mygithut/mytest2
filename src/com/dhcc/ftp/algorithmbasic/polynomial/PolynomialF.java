package com.dhcc.ftp.algorithmbasic.polynomial;

/**
 * ����ʽ����
 * @author wang
 *
 */
public class PolynomialF {
	/**
	 * ����ʽ�Ĵ���(����ߴ���)
	 */
	public int n;//����ʽ�Ĵ���(����ߴ���)
	
	/**
	 * ����ʽϵ�����飬a[0]Ϊn����(��ߴ���)ϵ��,...,a[n-1]Ϊ1����ϵ��,a[n]Ϊ�����
	 */
	public double[] a;//����ʽϵ�����飬a[0]Ϊn����(��ߴ���)ϵ��,...,a[n-1]Ϊ1����ϵ��,a[n]Ϊ�����
	
	/**
	 * ���캯������֪����ʽ����n������һ������ʽ��������ʱϵ������a[]����ʼΪ0
	 * @param n int ����ʽ����
	 */
	public PolynomialF(int n){
		this.n=n;
		this.a=new double[n+1];
	}
	
	/**
	 * ���캯������֪����ʽϵ������a������һ������ʽ��������ʱ����ʽ�����ɶ���ʽϵ������a�ĳ���������(��a.length-1)
	 * @param a double[] ����ʽϵ�����飻 ��ע�⡿����ʽ��ߴ�����ϵ��a[0]����Ϊ0
	 */
	public PolynomialF(double[] a){
		if(a.length==0){
			System.out.println("����ʽϵ������a�ĳ���Ϊ0���޷��������ʽ������");
			this.n=a.length-1;
		}else{		
			this.a=a;
		    this.n=a.length-1;		
		}
		
	}
	
	/**
	 * ���캯������֪����ʽ����n�Ͷ���ʽϵ������a������һ������ʽ��������ʱ����ʽ�����ɶ���ʽϵ������a�ĳ���������(��a.length-1)
	 * @param a double[] ����ʽϵ�����飻  ��ע�⡿����ʽ��ߴ�����ϵ��a[0]����Ϊ0
	 */
	public PolynomialF(int n,double[] a){
		if(a.length==(n+1)){			
			this.a=a;
		    this.n=a.length-1;					
		}else{
			System.out.println("����ʽ����n�����ʽϵ������a�ĳ��Ȳ�����");
		}
		
	}
	
	/**
	 * ��ӡ����ʽ����ʽΪ4.0x^4+x^3+2.0x^2-x+3.0������^��ʾ�η�
	 * @param f Ҫ��ӡ�Ķ���ʽ
	 */
	public static void printPolynomialF(PolynomialF f){
		String str="";
		//�Ӷ���ʽ����ߴ��ݿ�ʼ��ӡ ֱ��0����
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
	 * ��ӡ����ʽ��ϵ��������(����ߴε�0����)����ʽΪ{2,0,2,0}����˱�ʾ����ʽ2x^3+2.0x
	 * @param f Ҫ��ӡ�Ķ���ʽ
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
	 * ������ʽ����ת��Ϊͨ���ַ�����ʾ��ʽ����ʽΪ4.0x^4+x^3+2.0x^2-x+3.0������^��ʾ�η�
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
		//ȥ����ǰ����ܵġ�+��
		if(str.startsWith("+")){
			str=str.substring(1);
		}
		return str;
	}
	
	/**
	 * �����ʽ�����ĺ���ֵ	
	 * @param x double �Ա���x��ȡֵ
	 * @return double ����ֵy
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
	 * ��������ʽ���
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
	 * ��������ʽ���
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
	 * ����ʽ��
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
	 * ����ʽ��ϵ����ƫ��
	 * @param f PolynomialF Ҫ��ƫ���Ķ���ʽ
	 * @param m int Ҫ��ƫ�����Ǹ�ϵ����Ӧ��Ĵ���
	 * @return 
	 */
	public static PolynomialF partialDerivation(PolynomialF f,int m){
		if(m>f.n){
			System.out.println("�����Ŀ��ƫ�������"+m+"���ڸö���ʽ����ߴ���"+f.n+",ƫ�����ֱ��Ϊ0");
			return new PolynomialF(0);
		}
		PolynomialF F=new PolynomialF(m);
		F.a[0]=1;	
		return F;
	}
	
	
	
   //�������Գ���
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
