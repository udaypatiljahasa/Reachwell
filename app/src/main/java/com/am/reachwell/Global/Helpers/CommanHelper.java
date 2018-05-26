package com.am.reachwell.Global.Helpers;

import java.util.UUID;

public class CommanHelper {

    public static String generateUUID(){
        return UUID.randomUUID().toString().replace("-", "");
    }
}
