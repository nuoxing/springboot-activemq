/*
 * 文件名：StringUtils.java
 * 版权：Copyright by www.chinauip.com
 * 描述：
 * 修改人：Administrator
 * 修改时间：2017年7月24日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */
package com.work.util;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * 字符串工具类
 * 〈功能详细描述〉
 * @author suwy
 * @version 2017年7月25日
 * @see StringUtils
 * @since
 */
public class StringUtils
{

    public StringUtils()
    {
        // TODO Auto-generated constructor stub
    }
    
    /**
     * 
     * 描述:判断字符串是否为null或者为空 <br>
     * 
     * @param str 判断的字段串
     * @return  true 为null或空   false 不为
     * @see
     */
    public static boolean checkStringEmpty(String str)
    {
        if (str == null || "".equals(str.trim()))
        {
            return true;
        }
        return false;
    }

    
    /**
     * 
     * 描述:将null字符串转换成""
     * 
     * @param str 判断的字段串
     * @return  true 为null或空   false 不为
     * @see
     */
    public static String checkNull (String str)
    {
        if (str == null )
        {
            return "";
        }
        return str;
    }
    
    /**
     * 描述:字符串去空格
     * @param str
     * @return 
     */
    public static String replaceBlank(String str) {
		String dest = "";
		if (str!=null) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}
}
