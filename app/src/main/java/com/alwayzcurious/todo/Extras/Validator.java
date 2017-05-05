package com.alwayzcurious.todo.Extras;

/**
 * Created by vivek on 5/5/2017.
 */
public class Validator {



   public static boolean validateLength(String s,int length)
    {
        if(s.length() <length)
            return true;
        return false;
    }

    public static boolean validateSpecialChar(String s)
    {
       return s.matches("^[a-zA-Z]{4,}$");
    }


}
