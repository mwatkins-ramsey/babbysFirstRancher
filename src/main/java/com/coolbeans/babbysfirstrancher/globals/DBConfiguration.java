package com.coolbeans.babbysfirstrancher.globals;


import org.springframework.beans.factory.annotation.Value;

public class DBConfiguration {
    @Value("${MYSQL_HOST}")
    private String host;
    @Value("${MYSQL_USER}")
    private String user;
    @Value("${MYSQL_PASSWORD}")
    private String pass;
    @Value("${MYSQL_DB}")
    private String dbName;

    private static final DBConfiguration INSTANCE = new DBConfiguration();

    private DBConfiguration() {}

    public static DBConfiguration getInstance() {
        return INSTANCE;
    }

    /*
    public String getHost() {
        return host;
    }

    public String getUser() {
        return user;
    }

    public String getPass() {
        return pass;
    }

    public String getDbName() {
        return dbName;
    }*/
}
