package com.egecanakincioglu.database;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

import com.egecanakincioglu.utils.system.Logger;
import org.sqlite.JDBC;

public class SQLiteDatabaseSetup {

    static {
        try {
            DriverManager.registerDriver(new JDBC());
        } catch (SQLException e) {
            throw new RuntimeException("Failed to register JDBC driver", e);
        }
    }

    public static void setupDatabase() {
        String url = "jdbc:sqlite:auth.db";
        String[] sqlFiles = {
                "sql/001_create_users_table.sql",
                "sql/002_create_roles_table.sql",
                "sql/003_create_user_roles_table.sql",
                "sql/004_create_moderation_actions_table.sql",
                "sql/005_create_logs_table.sql",
                "sql/006_create_commands_table.sql",
                "sql/007_create_settings_table.sql",
                "sql/008_create_guilds_table.sql",
                "sql/009_create_guild_settings_table.sql",
                "sql/010_create_channels_table.sql"
        };

        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                Logger.database("Veritabanı bağlantısı başarılı!");

                for (String sqlFile : sqlFiles) {
                    executeSqlFromFile(conn, sqlFile);
                }
            }
        } catch (SQLException e) {
            Logger.database(e.getMessage());
        }
    }

    private static void executeSqlFromFile(Connection conn, String filePath) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                Objects.requireNonNull(SQLiteDatabaseSetup.class.getClassLoader().getResourceAsStream(filePath))))) {
            StringBuilder sql = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sql.append(line).append("\n");
            }
            try (Statement stmt = conn.createStatement()) {
                stmt.execute(sql.toString());
                Logger.database(filePath + " başarıyla çalıştırıldı.");
            } catch (SQLException e) {
                Logger.database(filePath + " çalıştırılırken hata oluştu: " + e.getMessage());
            }
        } catch (IOException e) {
            Logger.database("SQL dosyası okunurken hata oluştu: " + e.getMessage());
        } catch (NullPointerException e) {
            Logger.database("SQL dosyası bulunamadı: " + filePath);
        }
    }
}