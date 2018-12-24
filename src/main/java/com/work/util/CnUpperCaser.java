package com.work.util;


/*
 * 文件名：CnUpperCaser.java
 * 版权：Copyright by www.chinauip.com
 * 描述：
 * 修改人：Administrator
 * 修改时间：2017年9月4日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */


import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 将10亿以内的阿拉伯数字转成汉字大写形式
 * @author xizhenyin
 * 
 */
public class CnUpperCaser {
  // 整数部分
  private String integerPart;
  // 小数部分
  private String floatPart;
  
  // 将数字转化为汉字的数组,因为各个实例都要使用所以设为静态
  private static final char[] cnNumbers={'零','壹','贰','叁','肆','伍','陆','柒','捌','玖'};
  
  // 供分级转化的数组,因为各个实例都要使用所以设为静态
  private static final char[] series={'元','拾','百','仟','万','拾','百','仟','亿'};
  
  /**
   * 构造函数,通过它将阿拉伯数字形式的字符串传入
   * @param original
   */
  public CnUpperCaser(String original){ 
      original = original == null ? "" : original;
    // 成员变量初始化
    integerPart="";
    floatPart="";
    
    if(original.contains(".")){
      // 如果包含小数点
      int dotIndex=original.indexOf(".");
      integerPart=original.substring(0,dotIndex);
      floatPart=original.substring(dotIndex+1);
    }
    else{
      // 不包含小数点
      integerPart=original;
    }
  }
  
  /**
   * 取得大写形式的字符串
   * @return
   */
  public String getCnString(){
    // 因为是累加所以用StringBuffer
    StringBuffer sb=new StringBuffer();
    int len = integerPart.length();
    int len2 = len -1;
    // 整数部分处理
    for(int i=0;i<len;i++){
      int number=getNumber(integerPart.charAt(i));
     
     int number2 = 0;
     if (i<len-1)
     {
         number2 =   getNumber(integerPart.charAt(i+1));
     }
  
     
     if (number==0 && number == number2) continue;
      
      sb.append(cnNumbers[number]);
      if (i!=len2  && number!=0)
      {
          sb.append(series[integerPart.length()-1-i]);
      }
    
    }
    
    // 小数部分处理
    if(floatPart.length()>0){
      sb.append("点");
      for(int i=0;i<floatPart.length();i++){
        int number=getNumber(floatPart.charAt(i));
        
        sb.append(cnNumbers[number]);
      }
    }
    
    // 返回拼接好的字符串
    return sb.toString();
  }
  
  /**
   * 将字符形式的数字转化为整形数字
   * 因为所有实例都要用到所以用静态修饰
   * @param c
   * @return
   */
  private static int getNumber(char c){
    String str=String.valueOf(c);   
    return Integer.parseInt(str);
  }
  
  
  
  public boolean checkIsNumberAfter(String  str)
  {
      Pattern pattern = Pattern.compile(".*\\..*([1-9])+.*");
      Matcher isNum = pattern.matcher(str);
      if (isNum.matches()) {
          return true;
      } else {
          return false;
      }
  }
  
  /**
   * @param args
   */
  public static void main(String[] args) {
   /* System.out.println(new CnUpperCaser("123456789.12345").getCnString());
    System.out.println(new CnUpperCaser("123456789").getCnString());
    System.out.println(new CnUpperCaser(".123456789").getCnString());
    System.out.println(new CnUpperCaser("0.1234").getCnString());
    System.out.println(new CnUpperCaser("1").getCnString());
    System.out.println(new CnUpperCaser("12").getCnString());
    System.out.println(new CnUpperCaser("123").getCnString());
    System.out.println(new CnUpperCaser("1234").getCnString());
    System.out.println(new CnUpperCaser("12345").getCnString());
    System.out.println(new CnUpperCaser("123456").getCnString());
    System.out.println(new CnUpperCaser("1234567").getCnString());*/
    System.out.println(new CnUpperCaser("100002").getCnString());
    System.out.println(new CnUpperCaser("").getCnString());
    System.out.println(new CnUpperCaser("").checkIsNumberAfter("100.00000001"));
  }
}