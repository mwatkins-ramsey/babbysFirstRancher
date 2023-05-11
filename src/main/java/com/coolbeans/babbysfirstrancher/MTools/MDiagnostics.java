package com.coolbeans.babbysfirstrancher.MTools;

import java.util.Map;
import java.util.TreeMap;

public class MDiagnostics {

    public static void envDump(){
        System.out.println("Dumping ENVs");
        Map<String, String> envMap = System.getenv();
        Map<String, String> treeMap = new TreeMap<String, String>(envMap);

        for (String envName : treeMap.keySet()) {
            System.out.format("%s = %s%n", envName, envMap.get(envName));
        }
    }

    public static void applicationPropertiesDump(){
        //TODO
    }
}
