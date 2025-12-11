package com.brasilburger.config.database;

import java.sql.Connection;

public class TestDB {
    public static void main(String[] args) throws Exception {
        System.out.println("Test connexion BD...");
        Connection c = Database.getDataSource().getConnection();
        System.out.println("Connexion OK : " + (c != null));
        c.close();
    }
}
