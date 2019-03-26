package com.dhcc.ftp.algorithmbasic.bzztfbb;

/**
 * ����ѧ��ʽ�ı�׼��̬�ֲ�����(�������ֺ������ܶȺ���)��ʵ�־��ȶ���0.0001�� ������߾��� ֻҪ�ʵ�������ֵ�����(-3.89��3.89)
      �Լ���С���ڻ��ֵĵ�λ�ָ����䳤�ȣ�0.0001���Ϳ�����
 */
public class BzztfbFunction {

	 /**
     * ���ݷָ���ַ�����û���ֵ�������׼��̬�ֲ��Ļ��ֺ���N(x)
     * -3.89��3.89������Ļ������deltaX С�� 0.0001��
     * ����ȷ����Ч�Ļ�������Ϊ-3.89��3.89
     * ��ʵ�ַָ��ʱ�򾫶ȶ�Ϊ0.0001���õ��Ľ���Ͳ��õ��Ľ�������-0.0002��+0.0002֮�䣨�Ѿ����飩
     * 
     * @param x      ��������
     * @return       ����ֵ
     */
    public static double N(double x){
    	double ret  = 0;
        double deltaX=0.0001;
        if(x < -3.89){
            return 0;
        }
        else if(x > 3.89){
            return 1;
        }

        double temp = -3.89f;
        while(temp <= x){
            ret += deltaX * fx(temp);

            temp += deltaX;
        }

        return ret;
    }
    
    /**
     * ��N(x)����ͬԭ����N(x)��������ֵ������֪���ֺ������ֵN(x)���������x��
     * @param y double ���ֺ������ֵ��0~1֮��ȡֵ
     */
    public static double N_(double y){
    	double nx  = 0;
        double deltaX=0.0001;
        if(y < 0 || y > 1){
            return Double.NaN;
        }else if(y == 0){
            return Double.NEGATIVE_INFINITY;
        }else if(y == 1){
        	 return Double.POSITIVE_INFINITY;
        }

        double tempX = -5;//��-5��ʼ���֣���Ϊ-3.89��ǰ�Ļ���ֵ���Ѿ�С��0.0001��ȡ-5��Ϊ���ڲ�����̫��ѭ�����������ǰ�����ʵ���߾��ȡ�
        while(nx < y){
        	nx += deltaX * fx(tempX);

        	tempX += deltaX;
        }

        return tempX;
    }

    /**
     * ���׼��̫�ֲ�(�ܶ�)��������ĺ���ֵ    (1/(2 * PI)^(0.5))e^(-t^2/2)
     * @param x      ����x
     * @return       ����ֵ
     */
    public static double fx(double x){
    	double ret = 0;

        double a = 1.0 / Math.sqrt(Math.PI * 2);

        a  = a * Math.pow(Math.E, -0.5 * Math.pow(x, 2));

        ret = a;

        return ret;
    }

    public static void main(String[] args) {
    	System.out.println((BzztfbFunction.N_(0.99)==Double.NaN)?"����Ƿ���":BzztfbFunction.N_(0.99));
    }

}
