package com.zlj.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpResultParseUtil {
    public static String parseByRegx(String result,String regx){
        Pattern pattern = Pattern.compile(regx);
        Matcher matcher = pattern.matcher(result);
        String str="";
        if (matcher.find()) {
            str= matcher.group(1);
        }
        return str;
    }
}
