package com.coolbeans.babbysfirstrancher.controller;

import com.coolbeans.babbysfirstrancher.globals.DBConfiguration;
import com.coolbeans.babbysfirstrancher.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloWorldController {

    DBConfiguration myConfig;

    @Autowired
    public void setMyConfig(DBConfiguration myConfig) {
        System.out.println("Autowiring of setMyConfig ran!");
        this.myConfig = myConfig;
    }

    @GetMapping
    public Message helloWorld() {
        return new Message(0, "Hello World");
    }

    @GetMapping("/env")
    public DBConfiguration getEnv(){
        return myConfig;
    }

}
