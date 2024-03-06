package ru.netology.test;

import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.sql.SqlHelper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AqaShopOrdinaryPurchaseTest extends AqaShopBaseTest {

    @Test
    void shouldSuccessfullyBuyTourWithApprovedStatusCard() {
        long countBeforeTest = SqlHelper.countApprovedTransactions();
        logger.info("Approved transactions before test - {}", countBeforeTest);
        tourOfTheDayPage.buyTour();
        LocalDate date = DataHelper.generateFutureDate();
        tourOfTheDayPage.fillTheForm(
                DataHelper.getApprovedCard().getNumber(),
                date.format(DateTimeFormatter.ofPattern("MM")),
                date.format(DateTimeFormatter.ofPattern("yy")),
                DataHelper.generateValidCardHolderName(),
                DataHelper.generateValidCVV()
        );
        tourOfTheDayPage.findNotificationTitle("Успешно");
        tourOfTheDayPage.findNotificationContent("Операция одобрена Банком.");
        long countAfterTest = SqlHelper.countApprovedTransactions();
        logger.info("Approved transactions after test - {}", countAfterTest);
        assertEquals(countBeforeTest + 1, countAfterTest);
    }

    @Test
    void shouldFailWhenBuyTourWithDeclinedStatusCard() {
        long countBeforeTest = SqlHelper.countDeclinedTransactions();
        logger.info("Declined transactions before test - {}", countBeforeTest);
        tourOfTheDayPage.buyTour();
        LocalDate date = DataHelper.generateFutureDate();
        tourOfTheDayPage.fillTheForm(
                DataHelper.getDeclinedCard().getNumber(),
                date.format(DateTimeFormatter.ofPattern("MM")),
                date.format(DateTimeFormatter.ofPattern("yy")),
                DataHelper.generateValidCardHolderName(),
                DataHelper.generateValidCVV()
        );
        tourOfTheDayPage.findNotificationTitle("Ошибка");
        tourOfTheDayPage.findNotificationContent("Ошибка! Банк отказал в проведении операции.");
        long countAfterTest = SqlHelper.countDeclinedTransactions();
        logger.info("Declined transactions after test - {}", countAfterTest);
        assertEquals(countBeforeTest + 1, countAfterTest);
    }

    @Test
    void shouldFailWhenBuyTourWithCardNotFromList() {
        tourOfTheDayPage.buyTour();
        LocalDate date = DataHelper.generateFutureDate();
        tourOfTheDayPage.fillTheForm(
                DataHelper.generateValidCardNumberNotFromArray(),
                date.format(DateTimeFormatter.ofPattern("MM")),
                date.format(DateTimeFormatter.ofPattern("yy")),
                DataHelper.generateValidCardHolderName(),
                DataHelper.generateValidCVV()
        );
        tourOfTheDayPage.findNotificationTitle("Ошибка");
        tourOfTheDayPage.findNotificationContent("Ошибка! Банк отказал в проведении операции.");
    }

    @Test
    void shouldFailWhenBuyTourWithEmptyCardNumber() {
        tourOfTheDayPage.buyTour();
        LocalDate date = DataHelper.generateFutureDate();
        tourOfTheDayPage.fillTheForm(
                "",
                date.format(DateTimeFormatter.ofPattern("MM")),
                date.format(DateTimeFormatter.ofPattern("yy")),
                DataHelper.generateValidCardHolderName(),
                DataHelper.generateValidCVV()
        );
        tourOfTheDayPage.findCardNumberInputSub("Поле обязательно для заполнения");
    }

    @Test
    void shouldFailWhenBuyTourWithEmptyMonth() {
        tourOfTheDayPage.buyTour();
        LocalDate date = DataHelper.generateFutureDate();
        tourOfTheDayPage.fillTheForm(
                DataHelper.getApprovedCard().getNumber(),
                "",
                date.format(DateTimeFormatter.ofPattern("yy")),
                DataHelper.generateValidCardHolderName(),
                DataHelper.generateValidCVV()
        );
        tourOfTheDayPage.findCardMonthInputSub("Поле обязательно для заполнения");
    }

    @Test
    void shouldFailWhenBuyTourWithEmptyYear() {
        tourOfTheDayPage.buyTour();
        LocalDate date = DataHelper.generateFutureDate();
        tourOfTheDayPage.fillTheForm(
                DataHelper.getApprovedCard().getNumber(),
                date.format(DateTimeFormatter.ofPattern("MM")),
                "",
                DataHelper.generateValidCardHolderName(),
                DataHelper.generateValidCVV()
        );
        tourOfTheDayPage.findCardYearInputSub("Поле обязательно для заполнения");
    }

    @Test
    void shouldFailWhenBuyTourWithEmptyCardHolder() {
        tourOfTheDayPage.buyTour();
        LocalDate date = DataHelper.generateFutureDate();
        tourOfTheDayPage.fillTheForm(
                DataHelper.getApprovedCard().getNumber(),
                date.format(DateTimeFormatter.ofPattern("MM")),
                date.format(DateTimeFormatter.ofPattern("yy")),
                "",
                DataHelper.generateValidCVV()
        );
        tourOfTheDayPage.findCardHolderInputInputSub("Поле обязательно для заполнения");
    }

    @Test
    void shouldFailWhenBuyTourWithEmptyCVV() {
        tourOfTheDayPage.buyTour();
        LocalDate date = DataHelper.generateFutureDate();
        tourOfTheDayPage.fillTheForm(
                DataHelper.getApprovedCard().getNumber(),
                date.format(DateTimeFormatter.ofPattern("MM")),
                date.format(DateTimeFormatter.ofPattern("yy")),
                DataHelper.generateValidCardHolderName(),
                ""
        );
        tourOfTheDayPage.findCvvInputInputSub("Поле обязательно для заполнения");
    }

    @Test
    void shouldFailWhenBuyTourWithInvalidCardNumber() {
        tourOfTheDayPage.buyTour();
        LocalDate date = DataHelper.generateFutureDate();
        tourOfTheDayPage.fillTheForm(
                "4444444",
                date.format(DateTimeFormatter.ofPattern("MM")),
                date.format(DateTimeFormatter.ofPattern("yy")),
                DataHelper.generateValidCardHolderName(),
                DataHelper.generateValidCVV()
        );
        tourOfTheDayPage.findCardNumberInputSub("Неверный формат");
    }

    @Test
    void shouldFailWhenBuyTourWithInvalidMonth() {
        tourOfTheDayPage.buyTour();
        LocalDate date = DataHelper.generateFutureDate();
        tourOfTheDayPage.fillTheForm(
                DataHelper.getApprovedCard().getNumber(),
                "1",
                date.format(DateTimeFormatter.ofPattern("yy")),
                DataHelper.generateValidCardHolderName(),
                DataHelper.generateValidCVV()
        );
        tourOfTheDayPage.findCardMonthInputSub("Неверный формат");
    }

    @Test
    void shouldFailWhenBuyTourWithInvalidYear() {
        tourOfTheDayPage.buyTour();
        LocalDate date = DataHelper.generateFutureDate();
        tourOfTheDayPage.fillTheForm(
                DataHelper.getApprovedCard().getNumber(),
                date.format(DateTimeFormatter.ofPattern("MM")),
                "1",
                DataHelper.generateValidCardHolderName(),
                DataHelper.generateValidCVV()
        );
        tourOfTheDayPage.findCardYearInputSub("Неверный формат");
    }

    @Test
    void shouldFailWhenBuyTourWithInvalidDate() {
        tourOfTheDayPage.buyTour();
        LocalDate date = DataHelper.generateFutureDate();
        tourOfTheDayPage.fillTheForm(
                DataHelper.getApprovedCard().getNumber(),
                "02",
                "22",
                DataHelper.generateValidCardHolderName(),
                DataHelper.generateValidCVV()
        );
        tourOfTheDayPage.findCardYearInputSub("Истёк срок действия карты");
    }

    @Test
    void shouldFailWhenBuyTourWithInvalidCardHolder() {
        tourOfTheDayPage.buyTour();
        LocalDate date = DataHelper.generateFutureDate();
        tourOfTheDayPage.fillTheForm(
                DataHelper.getApprovedCard().getNumber(),
                date.format(DateTimeFormatter.ofPattern("MM")),
                date.format(DateTimeFormatter.ofPattern("yy")),
                "123@#",
                DataHelper.generateValidCVV()
        );
        tourOfTheDayPage.findCardHolderInputInputSub("Неверный формат");
    }

    @Test
    void shouldFailWhenBuyTourWithInvalidCVV() {
        tourOfTheDayPage.buyTour();
        LocalDate date = DataHelper.generateFutureDate();
        tourOfTheDayPage.fillTheForm(
                DataHelper.getApprovedCard().getNumber(),
                date.format(DateTimeFormatter.ofPattern("MM")),
                date.format(DateTimeFormatter.ofPattern("yy")),
                DataHelper.generateValidCardHolderName(),
                "12"
        );
        tourOfTheDayPage.findCvvInputInputSub("Неверный формат");
    }

}
