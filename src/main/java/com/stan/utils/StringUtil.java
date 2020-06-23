package com.stan.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StringUtil {

    /**
     * 格式化字符串
     */
    public static String stringNull(String str){
        return null==str ? "" : str.trim();
    }

    /**
     * 时间格式化
     */
    public static String stringDate(Date date,String format){
        String cdate = new SimpleDateFormat(format).format(date);
        return cdate;
    }

    /**
     * 字符串转int
     * @param str (字符串)
     * @param number 默认值
     */
    public static int stringInt(String str,int number){
        System.out.println(null==str||str==""+"------"+str+"------");
        return (null==str||str=="") ? number : Integer.parseInt(str.trim());
    }

    /**
     * 字符串转int
     * @param str (字符串)
     */
    public static int stringInt(String str){
            return Integer.parseInt(str.trim());
    }

    /**
     * * 判断一个对象是否为空
     *
     * @param object Object
     * @return true：为空 false：非空
     */
    public static boolean isNull(Object object)
    {
        return object == null;
    }

}
