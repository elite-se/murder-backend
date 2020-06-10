package de.marvinbrieger.toothbrushgame.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbCleaningHelper {

    public static void truncateAllTables(Connection conn) throws SQLException {
        conn.createStatement().executeUpdate("SET FOREIGN_KEY_CHECKS=0");
        ResultSet rs = conn.createStatement().
                executeQuery("SELECT table_name FROM information_schema.tables WHERE table_schema = SCHEMA()");

        while (rs.next()) {
            String tableName = rs.getString(1);
            conn.createStatement().executeUpdate("TRUNCATE TABLE " + tableName);
        }
        conn.createStatement().executeUpdate("SET FOREIGN_KEY_CHECKS=1");
    }

}
