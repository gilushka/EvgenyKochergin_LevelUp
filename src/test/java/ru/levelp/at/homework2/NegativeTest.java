package ru.levelp.at.homework2;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

public class NegativeTest {

    @ParameterizedTest
    @NullAndEmptySource
    public void checkNumberIsEmptyTest(String number) {
        AnswerCodes answer = CheckLuckyTicket.checkTicket(number);
        assertEquals(answer.getCode(), 2, "Номер билета - коректный");
        assertEquals(answer.getText(), "Не передан никакой номер билета", "Текст ошибки не совпадает с ожидаемым");
    }

    @Test
    public void checkNonEvenNumberTest() {
        AnswerCodes answer = CheckLuckyTicket.checkTicket("1234567");
        assertEquals(answer.getCode(), 2, "Номер билета - коректный");
        assertEquals(answer.getText(), "В переданном номере нечетное число цифр", "Ошибка не совпадает с ожидаемой");
    }

    @Test
    public void checkMinusInNumberTest() {
        AnswerCodes answer = CheckLuckyTicket.checkTicket("-0123456");
        assertEquals(answer.getCode(), 2, "Номер билета - коректный");
        assertEquals(answer.getText(), "В переданном номере не все символы - цифры", "Ошибка не совпадает с ожидаемой");
    }

    @Test
    public void checkPlusInNumberTest() {
        AnswerCodes answer = CheckLuckyTicket.checkTicket("+0123456");
        assertEquals(answer.getCode(), 2, "Номер билета - коректный");
        assertEquals(answer.getText(), "В переданном номере не все символы - цифры", "Ошибка не совпадает с ожидаемой");
    }

    @Test
    public void checkNonDecimalSymbolInNumberTest() {
        AnswerCodes answer = CheckLuckyTicket.checkTicket("123b4g56");
        assertEquals(answer.getCode(), 2, "Номер билета - коректный");
        assertEquals(answer.getText(), "В переданном номере не все символы - цифры", "Ошибка не совпадает с ожидаемой");
    }
}
