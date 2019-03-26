package com.dhcc.ftp.util;



//package org.jeecgframework.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)   
@Target(ElementType.FIELD)
public @interface Excel {
//����ʱ����Ӧ���ݿ���ֶ� ��Ҫ���û�����ÿ���ֶΣ�������annocation������
//����ʱ������   ���������������annotation���ֶε�˳���й�  
public String exportName();
//����ʱ��excel��ÿ���еĿ�  ��λΪ�ַ���һ������=2���ַ� 
//�� �������������нϺ��ʵĳ���   ����������6 ������һ�������֡�  �Ա���4����Ůռ1�������б����������֡�
//����1-255
public int exportFieldWidth();
//����ʱ�Ƿ�����ֶ�ת��   ���� �Ա���int�洢������ʱ����ת��Ϊ�У�Ů
//����signΪ1,����Ҫ��pojo�м���һ������ get�ֶ���Convert()
//���磬�ֶ�sex ����Ҫ���� public String getSexConvert()  ����ֵΪstring
//����signΪ0,�򲻱ع�
public int exportConvertSign();
//���������Ƿ���Ҫת��      �� �����е�excel���Ƿ���Ҫ���ֶ�תΪ��Ӧ������
//����signΪ1,����Ҫ��pojo�м���   void set�ֶ���Convert(String text)
public int importConvertSign();
}
