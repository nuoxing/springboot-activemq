/*
 * 文件名：DateConvertor.java
 * 版权：Copyright by www.chinauip.com
 * 描述：
 * 修改人：Administrator
 * 修改时间：2017年8月19日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */
package com.work.util;


import java.text.SimpleDateFormat;
import java.util.Date;

public class ConvertorUtil
{

    public ConvertorUtil()
    {
        // TODO Auto-generated constructor stub
    }

    /**  
    * 字符串日期转换成中文格式日期  
    * @param date  字符串日期 yyyy-MM-dd  
    * @return  yyyy年MM月dd日  
    * @throws Exception  
    */  
    public static  String dateToCnDate(String date) {   
        String result = "";   
        String[]  cnDate = new String[]{"〇","一","二","三","四","五","六","七","八","九"};   
        String ten = "十";   
        String[] dateStr = date.split("-");   
        for (int i=0; i<dateStr.length; i++) {   
            for (int j=0; j<dateStr[i].length(); j++) {  
                String charStr = dateStr[i];   
                String str = String.valueOf(charStr.charAt(j));   
                if (charStr.length() == 2) {   
                    if (charStr.equals("10")) {   
                        result += ten;   
                        break;   
                    } else {   
                        if (j == 0) {   
                            if (charStr.charAt(j) == '1')    
                                result += ten;   
                            else if (charStr.charAt(j) == '0')   
                                result += "";   
                            else  
                                result += cnDate[Integer.parseInt(str)] + ten;   
                        }   
                        if (j == 1) {   
                            if (charStr.charAt(j) == '0')   
                                result += "";   
                             else  
                                result += cnDate[Integer.parseInt(str)];   
                        }   
                    }   
                } else {   
                    result += cnDate[Integer.parseInt(str)];   
                }   
            }   
            if (i == 0) {   
                result += "年";   
                continue;   
            }   
            if (i == 1) {   
                result += "月";   
                continue;   
            }   
            if (i == 2) {   
                result += "日";   
                continue;   
            }   
        }   
        return result;   
    }  
    /**  
    * 字符串日期转换成中文格式日期  
    * @param date  字符串日期 yyyy-MM-dd  
    * @return  yyyy年MM月dd日  
    * @throws Exception  
    */  
    public  static String dateToCnDateSubYear(String date) {   
        String result = "";   
        String[]  cnDate = new String[]{"〇","一","二","三","四","五","六","七","八","九"};   
        String ten = "十";   
        String[] dateStr = date.split("-");   
        for (int i=0; i<dateStr.length; i++) {   
            for (int j=0; j<dateStr[i].length(); j++) {  
                String charStr = dateStr[i];   
                String str = String.valueOf(charStr.charAt(j));   
                if (charStr.length() == 2) {   
                    if (charStr.equals("10")) {   
                        result += ten;   
                        break;   
                    } else {   
                        if (j == 0) {   
                            if (charStr.charAt(j) == '1')    
                                result += ten;   
                            else if (charStr.charAt(j) == '0')   
                                result += "";   
                            else  
                                result += cnDate[Integer.parseInt(str)] + ten;   
                        }   
                        if (j == 1) {   
                            if (charStr.charAt(j) == '0')   
                                result += "";   
                             else  
                                result += cnDate[Integer.parseInt(str)];   
                        }   
                    }   
                } else {   
                    result += cnDate[Integer.parseInt(str)];   
                }   
            }   
//          if (i == 0) {   
//              result += "年";   
//              continue;   
//          }   
//          if (i == 1) {   
//              result += "月";   
//              continue;   
//          }   
//          if (i == 2) {   
//              result += "日";   
//              continue;   
//          }   
        }   
        result=result.substring(0,4);
        return result;   
    }  
    public static void main(String[] args) {
        String year = dateToCnDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        System.out.println(year);
    }
}
