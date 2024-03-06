package ru.netology.sql;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqlHelper {

    private SqlHelper() {}

    private static final Logger logger = LogManager.getLogger(SqlHelper.class);

    private static final String DB_URL = System.getProperty("datasource.url");
    private static final String DB_USER = System.getProperty("datasource.username");
    private static final String DB_PASS = System.getProperty("datasource.password");

    private static final QueryRunner runner = new QueryRunner();
    private static Connection conn;

    public static long countApprovedTransactions() {
        try {
            return runner.query(conn, "SELECT COUNT(*) FROM payment_entity WHERE status = 'APPROVED'", new ScalarHandler<>());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static long countApprovedCreditRequests() {
        try {
            return runner.query(conn, "SELECT COUNT(*) FROM credit_request_entity WHERE status = 'APPROVED'", new ScalarHandler<>());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static long countDeclinedTransactions() {
        try {
            return runner.query(conn, "SELECT COUNT(*) FROM payment_entity WHERE status = 'DECLINED'", new ScalarHandler<>());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static long countDeclinedCreditRequests() {
        try {
            return runner.query(conn, "SELECT COUNT(*) FROM credit_request_entity WHERE status = 'DECLINED'", new ScalarHandler<>());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void openConnection() {
        try {
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            logger.info("The connection to the DB is established [DB_URL = {}, DB_USER = {}, DB_PASS = {}]", DB_URL, DB_USER, DB_PASS);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void closeConnection() {
        try {
            conn.close();
            logger.info("The connection to the DB is closed");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
