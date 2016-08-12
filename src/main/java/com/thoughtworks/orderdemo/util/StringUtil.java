package com.thoughtworks.orderdemo.util;

/**
 * Created by rlxie on 8/12/16.
 */
public class StringUtil {

    private static StringUtil stringUtil;

    private StringUtil(){}

    public static synchronized StringUtil newInstance(){
        if( null == stringUtil ){
            stringUtil = new StringUtil();
        }
        return stringUtil;
    }

    public boolean isEmpty(String s){
        if( null == s || s.length() == 0 ){
            return true;
        }
        return false;
    }
}
