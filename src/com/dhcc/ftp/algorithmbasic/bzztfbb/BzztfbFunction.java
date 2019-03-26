package com.dhcc.ftp.algorithmbasic.bzztfbb;

/**
 * 纯数学形式的标准正态分布函数(包括积分函数、密度函数)；实现精度定在0.0001； 若想提高精度 只要适当扩大积分的区间(-3.89～3.89)
      以及缩小用于积分的单位分割区间长度（0.0001）就可以了
 */
public class BzztfbFunction {

	 /**
     * 根据分割积分法来求得积分值，即求标准正态分布的积分函数N(x)
     * -3.89～3.89区间外的积分面积deltaX 小于 0.0001，
     * 所以确定有效的积分区间为-3.89～3.89
     * 在实现分割的时候精度定为0.0001，得到的结果和查表得到的结果误差在-0.0002～+0.0002之间（已经检验）
     * 
     * @param x      积分上限
     * @return       积分值
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
     * 用N(x)的相同原理求N(x)反函数的值，即已知积分函数结果值N(x)求积分上限x。
     * @param y double 积分函数结果值；0~1之间取值
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

        double tempX = -5;//从-5开始积分，因为-3.89以前的积分值都已经小于0.0001；取-5是为了在不增加太多循环计算次数的前提下适当提高精度。
        while(nx < y){
        	nx += deltaX * fx(tempX);

        	tempX += deltaX;
        }

        return tempX;
    }

    /**
     * 求标准正太分布(密度)函数自身的函数值    (1/(2 * PI)^(0.5))e^(-t^2/2)
     * @param x      变量x
     * @return       函数值
     */
    public static double fx(double x){
    	double ret = 0;

        double a = 1.0 / Math.sqrt(Math.PI * 2);

        a  = a * Math.pow(Math.E, -0.5 * Math.pow(x, 2));

        ret = a;

        return ret;
    }

    public static void main(String[] args) {
    	System.out.println((BzztfbFunction.N_(0.99)==Double.NaN)?"输入非法！":BzztfbFunction.N_(0.99));
    }

}
