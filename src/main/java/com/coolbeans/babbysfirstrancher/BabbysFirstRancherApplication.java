package com.coolbeans.babbysfirstrancher;

import com.coolbeans.babbysfirstrancher.MTools.MDiagnostics;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BabbysFirstRancherApplication {

    public static void main(String[] args) {


        MDiagnostics.envDump();
        SpringApplication.run(BabbysFirstRancherApplication.class, args);
    }

}
