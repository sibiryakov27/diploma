package ru.netology.page;

import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class TourOfTheDayPage {

    // Кнопки
    private final SelenideElement buyButton = $x("//span[text()=\"Купить\"]/../..");
    private final SelenideElement buyInCreditButton = $x("//span[text()=\"Купить в кредит\"]/../..");

    // Форма
    private final SelenideElement cardNumberInput = $x("//span[text()=\"Номер карты\"]/..//input");
    private final SelenideElement monthInput = $x("//span[text()=\"Месяц\"]/..//input");
    private final SelenideElement yearInput = $x("//span[text()=\"Год\"]/..//input");
    private final SelenideElement cardHolderInput = $x("//span[text()=\"Владелец\"]/..//input");
    private final SelenideElement cvvInput = $x("//span[text()=\"CVC/CVV\"]/..//input");
    private final SelenideElement continueButton = $x("//span[text()=\"Продолжить\"]/../..");

    // Уведомление
    private final SelenideElement notificationTitle = $(".notification__title");
    private final SelenideElement notificationContent = $(".notification__content");

    public void buyTour() {
        buyButton.click();
    }

    public void buyTourInCredit() {
        buyInCreditButton.click();
    }

    public void fillTheForm(String cardNumber, String month, String year, String cardHolder, String cvv) {
        cardNumberInput.setValue(cardNumber);
        monthInput.setValue(month);
        yearInput.setValue(year);
        cardHolderInput.setValue(cardHolder);
        cvvInput.setValue(cvv);

        continueButton.click();
    }

    public void findNotificationTitle(String expectedText) {
        notificationTitle.shouldHave(text(expectedText), Duration.ofSeconds(15)).shouldBe(visible);
    }

    public void findNotificationContent(String expectedText) {
        notificationContent.shouldHave(text(expectedText), Duration.ofSeconds(15)).shouldBe(visible);
    }

}
