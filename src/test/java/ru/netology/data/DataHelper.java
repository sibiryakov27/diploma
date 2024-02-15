package ru.netology.data;

import com.github.javafaker.Faker;
import com.google.gson.Gson;
import lombok.Value;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.LocalDate;
import java.util.Random;

public class DataHelper {

    private DataHelper() {}

    public static CardInfo[] cards;

    private static Faker faker = new Faker();

    static {
        try {
            Gson gson = new Gson();
            cards = gson.fromJson(new FileReader("artifacts/gate-simulator/data.json"), CardInfo[].class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static String generateValidCardHolderName() {
        return faker.name().firstName() + " " + faker.name().lastName();
    }

    public static String generateValidCardNumber() {
        String issuerIdentificationNumber = "4"; // Префикс для Visa карт
        String accountNumber = faker.number().digits(15); // Генерация случайного числа длиной 15 цифр
        String cardNumber = issuerIdentificationNumber + accountNumber;

        return cardNumber;
    }

    public static LocalDate generateFutureDate() {
        Random random = new Random();
        LocalDate currentDate = LocalDate.now();
        int randomDays = random.nextInt(365) + 1;
        LocalDate futureDate = currentDate.plusDays(randomDays);

        return futureDate;
    }

    public static String generateValidCVV() {
        return faker.number().digits(3);
    }

    @Value
    public static class CardInfo {
        String number;
        String status;
    }

}
