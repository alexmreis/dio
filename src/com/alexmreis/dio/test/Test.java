package com.alexmreis.dio.test;

import com.alexmreis.dio.DioController;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: alex
 * Date: Mar 25, 2011
 * Time: 1:09:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class Test implements DioController{
    public String get(Map<String, String> parameters) {
        System.out.println(Thread.currentThread().getName());
        return "<html><body><h1>Hello world!</h1></body></html>";
    }

    public String put(Map<String, String> parameters) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String delete(Map<String, String> parameters) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String post(Map<String, String> parameters) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
