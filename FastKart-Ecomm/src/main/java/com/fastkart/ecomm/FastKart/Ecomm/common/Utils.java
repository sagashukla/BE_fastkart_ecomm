package com.fastkart.ecomm.FastKart.Ecomm.common;

public class Utils {
    public static boolean validateString(String str){
        if(str == null || str == ""){
            return true;
        }
        else{
            return false;
        }
    }

    public static boolean validatePassword(String str){
        if(str.length() < 5){
            return true;
        }
        else{
            return false;
        }
    }

}
