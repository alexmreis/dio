package com.alexmreis.dio;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: alex
 * Date: Mar 25, 2011
 * Time: 12:37:22 PM
 * To change this template use File | Settings | File Templates.
 */
public interface DioController {
    String get(Map<String, String> parameters);
    String put(Map<String, String> parameters);
    String delete(Map<String, String> parameters);
    String post(Map<String, String> parameters);
}
