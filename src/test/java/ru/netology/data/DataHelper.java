package ru.netology.data;

import com.github.javafaker.Faker;
import com.google.gson.Gson;
import lombok.Value;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;

public class DataHelper {

    private DataHelper() {}

    public static CardInfo[] cards;

    private static final Faker faker = new Faker();

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

    public static String generateValidCardNumberNotFromArray() {
        String cardNumber;
        do {
            cardNumber = "4" + faker.number().digits(15);
        } while (Arrays.stream(cards).map(card -> card.number)
                .collect(Collectors.toCollection(ArrayList::new))
                .contains(cardNumber)
        );

        return cardNumber;
    }

    public static LocalDate generateFutureDate() {
        Random random = new Random();
        LocalDate currentDate = LocalDate.now();
        int randomDays = random.nextInt(365) + 1;

        return currentDate.plusDays(randomDays);
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
