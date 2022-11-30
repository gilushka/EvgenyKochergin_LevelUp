package ru.levelp.at.homework2;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class PositiveTest {


    @ParameterizedTest
    @ValueSource(strings = {"123123", "12344321", "1221", "1324561133", "33442147"})
    public void checkTicketLuckyTest() {
        AnswerCodes answer = CheckLuckyTicket.checkTicket("123321");
        assertEquals(answer.getCode(), 1, "Передан номер не являющийся счастливым");
    }

    @ParameterizedTest
    @ValueSource(strings = {"123456", "12344322", "1122", "1324565432"})
    public void checkTicketUnLuckyTest() {
        AnswerCodes answer = CheckLuckyTicket.checkTicket("123456");
        assertEquals(answer.getCode(), 0, "Передан номер не являющийся не счастливым");
    }
}
