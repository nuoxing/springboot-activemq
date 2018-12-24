package com.work.util;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateUtil {
	/**
	 * 验证身份证
	 * 
	 * @param IDStr
	 * @return "YES" 代表合法的身份证 ,其他值代表错误信息
	 * @throws ParseException
	 */
	public static String IDCardValidate(String IDStr) {
		String tipInfo = "yes";// 记录错误信息
		String Ai = "";

		if (null == IDStr || IDStr.trim().isEmpty())
			return "身份证号码长度应该为15位或18位。";

		// 判断号码的长度 15位或18位
		if (IDStr.length() != 15 && IDStr.length() != 18) {
			tipInfo = "身份证号码长度应该为15位或18位。";
			return tipInfo;
		}
		// 18位身份证前17位位数字，如果是15位的身份证则所有号码都为数字
		if (IDStr.length() == 18) {
			Ai = IDStr.substring(0, 17);
		} else if (IDStr.length() == 15) {
			Ai = IDStr.substring(0, 6) + "19" + IDStr.substring(6, 15);
		}
		if (isNumeric(Ai) == false) {
			tipInfo = "身份证15位号码都应为数字 ; 18位号码除最后一位外，都应为数字。";
			return tipInfo;
		}
		// 判断出生年月是否有效
		String strYear = Ai.substring(6, 10);// 年份
		String strMonth = Ai.substring(10, 12);// 月份
		String strDay = Ai.substring(12, 14);// 日期
		if (isDate(strYear + "-" + strMonth + "-" + strDay) == false) {
			tipInfo = "身份证出生日期无效。";
			return tipInfo;
		}
		GregorianCalendar gc = new GregorianCalendar();
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
		try {
			if ((gc.get(Calendar.YEAR) - Integer.parseInt(strYear)) > 150
					|| (gc.getTime().getTime() - s.parse(strYear + "-" + strMonth + "-" + strDay).getTime()) < 0) {
				tipInfo = "身份证生日不在有效范围。";
				return tipInfo;
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}
		if (Integer.parseInt(strMonth) > 12 || Integer.parseInt(strMonth) == 0) {
			tipInfo = "身份证月份无效";
			return tipInfo;
		}
		if (Integer.parseInt(strDay) > 31 || Integer.parseInt(strDay) == 0) {
			tipInfo = "身份证日期无效";
			return tipInfo;
		}
		// 判断地区码是否有效
		Hashtable<String,String> areacode = GetAreaCode();
		// 如果身份证前两位的地区码不在Hashtable，则地区码有误
		if (areacode.get(Ai.substring(0, 2)) == null) {
			tipInfo = "身份证地区编码错误。";
			return tipInfo;
		}
		if (isVarifyCode(Ai, IDStr) == false) {
			tipInfo = "身份证校验码无效，不是合法的身份证号码";
			return tipInfo;
		}
		return tipInfo;
	}

	/*
	 * 判断第18位校验码是否正确 第18位校验码的计算方式： 1. 对前17位数字本体码加权求和 公式为：S = Sum(Ai * Wi), i = 0,
	 * ... , 16 其中Ai表示第i个位置上的身份证号码数字值，Wi表示第i位置上的加权因子，其各位对应的值依次为： 7 9 10 5 8 4 2 1 6
	 * 3 7 9 10 5 8 4 2 2. 用11对计算结果取模 Y = mod(S, 11) 3. 根据模的值得到对应的校验码 对应关系为： Y值： 0 1
	 * 2 3 4 5 6 7 8 9 10 校验码： 1 0 X 9 8 7 6 5 4 3 2
	 */
	private static boolean isVarifyCode(String Ai, String IDStr) {
		String[] VarifyCode = { "1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2" };
		String[] Wi = { "7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7", "9", "10", "5", "8", "4", "2" };
		int sum = 0;
		for (int i = 0; i < 17; i++) {
			sum = sum + Integer.parseInt(String.valueOf(Ai.charAt(i))) * Integer.parseInt(Wi[i]);
		}
		int modValue = sum % 11;
		String strVerifyCode = VarifyCode[modValue];
		Ai = Ai + strVerifyCode;
		if (IDStr.length() == 18) {
			if (Ai.equals(IDStr) == false) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 将所有地址编码保存在一个Hashtable中
	 * 
	 * @return Hashtable 对象
	 */
	private static Hashtable<String,String> GetAreaCode() {
		Hashtable<String,String> hashtable = new Hashtable<>();
		hashtable.put("11", "北京");
		hashtable.put("12", "天津");
		hashtable.put("13", "河北");
		hashtable.put("14", "山西");
		hashtable.put("15", "内蒙古");
		hashtable.put("21", "辽宁");
		hashtable.put("22", "吉林");
		hashtable.put("23", "黑龙江");
		hashtable.put("31", "上海");
		hashtable.put("32", "江苏");
		hashtable.put("33", "浙江");
		hashtable.put("34", "安徽");
		hashtable.put("35", "福建");
		hashtable.put("36", "江西");
		hashtable.put("37", "山东");
		hashtable.put("41", "河南");
		hashtable.put("42", "湖北");
		hashtable.put("43", "湖南");
		hashtable.put("44", "广东");
		hashtable.put("45", "广西");
		hashtable.put("46", "海南");
		hashtable.put("50", "重庆");
		hashtable.put("51", "四川");
		hashtable.put("52", "贵州");
		hashtable.put("53", "云南");
		hashtable.put("54", "西藏");
		hashtable.put("61", "陕西");
		hashtable.put("62", "甘肃");
		hashtable.put("63", "青海");
		hashtable.put("64", "宁夏");
		hashtable.put("65", "新疆");
		hashtable.put("71", "台湾");
		hashtable.put("81", "香港");
		hashtable.put("82", "澳门");
		hashtable.put("91", "国外");
		return hashtable;
	}

	/**
	 * 判断字符串是否为数字,0-9重复0次或者多次
	 * 
	 * @param strnum
	 * @return
	 */
	public  static boolean isNumeric(String strnum) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(strnum);
		if (isNum.matches()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 功能：判断字符串出生日期是否符合正则表达式：包括年月日，闰年、平年和每月31天、30天和闰月的28天或者29天
	 * 
	 * @param string
	 * @return
	 */
	public static boolean isDate(String strDate) {
		Pattern pattern = Pattern.compile(
				"^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))?$");
		Matcher m = pattern.matcher(strDate);
		if (m.matches()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 手机号验证
	 * 
	 * @param str
	 * @return 验证通过返回true
	 */
	public static String isMobile(String str) {
		String tipInfo = "yes";
		if (StringUtils.checkStringEmpty(str)) {
			return "请填写移动电话";
		} else {
			// String regEx = "^?[0-9]+$";
			String regEx = "^\\d+(\\d+)?$";
			if (matcherCheck(str, regEx)) {
				if (str.length() != 11) {
					return "移动电话格式错误,需要11位";
				}
				if (matcherCheck(str, "^[1][3,4,5,7,8][0-9]{9}$")) {
					return tipInfo;
				} else {
					return "移动电话输入的格式不对";
				}
			} else {
				return "电话号码只能为数字";
			}
		}
	}

	/**
	 * 固话校验
	 */
	public static String isFixedPhone(String fixedPhone) {
		String info = "yes";
		if (fixedPhone == null || fixedPhone.trim().isEmpty()) {
			info = "请填写固定电话";
			return info;
		}
		if (!matcherCheck(fixedPhone, "([\\d-\\(\\)])*")) {
			info = "固话不能含有数字、-、()之外的字符";
			return info;
		}
		String reg = "^(^0\\d{2}-?\\d{8}$)|(^0\\d{3}-?\\d{7}$)|(^\\(0\\d{2}\\)-?\\d{8}$)|(^\\(0\\d{3}\\)-?\\d{7}$)$";
		if (!matcherCheck(fixedPhone, reg)) {
			info = "固定电话的格式不对";
			return info;
		}
		return info;
	}

	/**
	 * 正则验证公共方法
	 * 
	 * @author ljj
	 * @param str
	 * @param p
	 * @return
	 */
	public static boolean matcherCheck(String str, String p) {
		Pattern pattern = Pattern.compile(p);
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}

	/**
	 * 注册资金的校验
	 * 
	 * @author ljj
	 * @return
	 */
	public static String RegisteredFundsValidate(String mstr) {
		String info = "yes";
		if (mstr == null || mstr.trim().isEmpty()) {
			info = "请填写注册资金";
			return info;
		}
		if (!matcherCheck(mstr, "^\\d+(\\.\\d{1,4})?$")) {
			info = "请输入正确的数字（最多取小数点后4位）";
			return info;
		}
		double d = Double.parseDouble(mstr);
		if (d < 0.0001) {
			info = "输入的注册资金过小，资金范围0.0001至9999999999";
			return info;
		} else if (d > 9999999999.0) {
			info = "输入的注册资金过大，资金范围0.0001至9999999999";
			return info;
		}
		return info;
	}

	/**
	 * 验证隶属企业名称
	 * 
	 * @author ljj
	 * @param name
	 * @return
	 */
	public static String LSEntNameValidate(String name) {
		String info = "yes";
		if (name == null || name.trim().isEmpty()) {
			info = "请填写隶属企业名称";
			return info;
		}
		if (!matcherCheck(name, "^([\\u4E00-\\u9FA5]|（|）)+$")) {
			info = "只能输入中文或中文括号";
			return info;
		}
		return info;
	}

	/**
	 * 
	 * @param 验证字号
	 * @author ljj
	 * @return
	 */
	public static String ZhValidate(String zh) {
		String info = "yes";
		if (zh == null || zh.trim().isEmpty()) {
			info = "请填写字号";
			return info;
		}
		if (!matcherCheck(zh, "^([\\u4E00-\\u9FA5]){2,5}$")) {
			info = "请输入2-5位中文";
			return info;
		}
		return info;
	}

	/**
	 * 验证行业表述
	 * 
	 * @author ljj
	 * @param str
	 * @return
	 */
	public static String AllChineseValidate(String str) {
		String info = "yes";
		if (str == null || str.trim().isEmpty()) {
			info = "请填写行业表述";
			return info;
		}
		if (!matcherCheck(str, "^([\\u4E00-\\u9FA5])+$")) {
			info = "只能输入中文";
			return info;
		}
		return info;
	}

	/**
	 * 验证是否含有特殊字符
	 * 
	 * @author ljj
	 * @param str
	 * @return
	 */
	public static String checkSpecialcharacter(String str) {
		String info = "yes";
		if (str == null || str.trim().isEmpty()) {
			info = "字符不能为空";
			return info;
		}
		String reg = "^[^'\"]*$";
		if (!matcherCheck(str, reg)) {
			info = "输入了特殊字符";
			return info;
		}
		return info;
	}

	public static boolean checkChinese(String str) {
		if (!matcherCheck(str, "^([\\u4E00-\\u9FA5])+$")) {
			return false;
		}
		return true;
	}

	/**
	 * 校验邮箱格式
	 * @param email
	 * @return
	 */
	public static String isEmail(String email) {
		String info ="yes";
		String str = "^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(email);
		if (!m.matches()) {
			info = "输入的邮箱格式不正确！";
		}
		return info;
	}

	public static void main(String[] str) {
		String info = checkSpecialcharacter("23 5");
		System.out.println(info);
	}
}