package ru.netology.test;

import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.sql.SqlHelper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AqaShopPurchaseOnCreditTest extends AqaShopBaseTest {

    @Test
    void shouldSuccessfullyBuyTourOnCreditWithApprovedStatusCard() {
        long countBeforeTest = SqlHelper.countApprovedCreditRequests();
        logger.info("Approved credit requests before test - {}", countBeforeTest);
        tourOfTheDayPage.buyTourOnCredit();
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
        long countAfterTest = SqlHelper.countApprovedCreditRequests();
        logger.info("Approved credit requests after test - {}", countAfterTest);
        assertEquals(countBeforeTest + 1, countAfterTest);
    }

    @Test
    void shouldFailWhenBuyTourOnCreditWithDeclinedStatusCard() {
        long countBeforeTest = SqlHelper.countDeclinedCreditRequests();
        logger.info("Declined credit requests before test - {}", countBeforeTest);
        tourOfTheDayPage.buyTourOnCredit();
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
        long countAfterTest = SqlHelper.countDeclinedCreditRequests();
        logger.info("Declined credit requests after test - {}", countAfterTest);
        assertEquals(countBeforeTest + 1, countAfterTest);
    }

    @Test
    void shouldFailWhenBuyTourOnCreditInCreditWithCardNotFromList() {
        tourOfTheDayPage.buyTourOnCredit();
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
    void shouldFailWhenBuyTourOnCreditWithEmptyCardNumber() {
        tourOfTheDayPage.buyTourOnCredit();
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
    void shouldFailWhenBuyTourOnCreditWithEmptyMonth() {
        tourOfTheDayPage.buyTourOnCredit();
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
    void shouldFailWhenBuyTourOnCreditWithEmptyYear() {
        tourOfTheDayPage.buyTourOnCredit();
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
    void shouldFailWhenBuyTourOnCreditWithEmptyCardHolder() {
        tourOfTheDayPage.buyTourOnCredit();
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
    void shouldFailWhenBuyTourOnCreditWithEmptyCVV() {
        tourOfTheDayPage.buyTourOnCredit();
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
    void shouldFailWhenBuyTourOnCreditWithInvalidCardNumber() {
        tourOfTheDayPage.buyTourOnCredit();
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
    void shouldFailWhenBuyTourOnCreditWithInvalidMonth() {
        tourOfTheDayPage.buyTourOnCredit();
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
    void shouldFailWhenBuyTourOnCreditWithInvalidYear() {
        tourOfTheDayPage.buyTourOnCredit();
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
    void shouldFailWhenBuyTourOnCreditWithInvalidDate() {
        tourOfTheDayPage.buyTourOnCredit();
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
    void shouldFailWhenBuyTourOnCreditWithInvalidCardHolder() {
        tourOfTheDayPage.buyTourOnCredit();
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
    void shouldFailWhenBuyTourOnCreditWithInvalidCVV() {
        tourOfTheDayPage.buyTourOnCredit();
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
