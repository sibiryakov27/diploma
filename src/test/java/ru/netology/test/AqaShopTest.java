package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import com.epam.reportportal.junit5.ReportPortalExtension;
import io.qameta.allure.selenide.AllureSelenide;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.netology.page.TourOfTheDayPage;
import ru.netology.sql.SqlHelper;

import static com.codeborne.selenide.Selenide.open;

@ExtendWith(ReportPortalExtension.class)
public class AqaShopTest {

    TourOfTheDayPage tourOfTheDayPage;

    final Logger logger = LogManager.getLogger(AqaShopTest.class);

    @BeforeEach
    void setUp() {
        tourOfTheDayPage = open("http://localhost:8080/", TourOfTheDayPage.class);
    }

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
        SqlHelper.openConnection();
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
        SqlHelper.closeConnection();
    }

}
