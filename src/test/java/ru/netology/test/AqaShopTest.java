package ru.netology.test;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.TourOfTheDayPage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.time.LocalDate;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AqaShopTest {

    private TourOfTheDayPage tourOfTheDayPage;

    private final Logger logger = LogManager.getLogger(AqaShopTest.class);

    private final String DB_URL = "jdbc:mysql://localhost:3306/app";
    private final String DB_USER = "app";
    private final String DB_PASS = "pass";

    private final String COUNT_APPROVED_TRANSACTIONS = "SELECT COUNT(*) FROM payment_entity WHERE status = 'APPROVED'";
    private final String COUNT_DECLINED_TRANSACTIONS = "SELECT COUNT(*) FROM payment_entity WHERE status = 'DECLINED'";

    @BeforeEach
    void setUp() {
        tourOfTheDayPage = open("http://localhost:8080/", TourOfTheDayPage.class);
    }

    @Test
    @SneakyThrows
    void shouldSuccessfullyBuyTourWithApprovedStatusCard() {
        QueryRunner runner = new QueryRunner();
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);) {
            long countBeforeTest = runner.query(conn, COUNT_APPROVED_TRANSACTIONS, new ScalarHandler<>());
            logger.info("Approved transactions before test - {}", countBeforeTest);
            tourOfTheDayPage.buyTour();
            LocalDate date = DataHelper.generateFutureDate();
            tourOfTheDayPage.fillTheForm(
                    DataHelper.cards[0].getNumber(),
                    String.format("%02d", date.getMonthValue()),
                    String.valueOf(date.getYear() % 100),
                    DataHelper.generateValidCardHolderName(),
                    DataHelper.generateValidCVV()
            );
            tourOfTheDayPage.findNotificationTitle("Успешно");
            tourOfTheDayPage.findNotificationContent("Операция одобрена Банком.");
            long countAfterTest = runner.query(conn, COUNT_APPROVED_TRANSACTIONS, new ScalarHandler<>());
            logger.info("Approved transactions after test - {}", countAfterTest);
            assertEquals(countBeforeTest + 1, countAfterTest);
        }
    }

    @Test
    @SneakyThrows
    void shouldFailWhenBuyTourWithDeclinedStatusCard() {
        QueryRunner runner = new QueryRunner();
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);) {
            long countBeforeTest = runner.query(conn, COUNT_DECLINED_TRANSACTIONS, new ScalarHandler<>());
            logger.info("Declined transactions before test - {}", countBeforeTest);
            tourOfTheDayPage.buyTour();
            LocalDate date = DataHelper.generateFutureDate();
            tourOfTheDayPage.fillTheForm(
                    DataHelper.cards[1].getNumber(),
                    String.format("%02d", date.getMonthValue()),
                    String.valueOf(date.getYear() % 100),
                    DataHelper.generateValidCardHolderName(),
                    DataHelper.generateValidCVV()
            );
            tourOfTheDayPage.findNotificationTitle("Ошибка");
            tourOfTheDayPage.findNotificationContent("Ошибка! Банк отказал в проведении операции.");
            long countAfterTest = runner.query(conn, COUNT_DECLINED_TRANSACTIONS, new ScalarHandler<>());
            logger.info("Declined transactions after test - {}", countAfterTest);
            assertEquals(countBeforeTest + 1, countAfterTest);
        }
    }

}
