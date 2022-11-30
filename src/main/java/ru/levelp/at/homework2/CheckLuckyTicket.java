package ru.levelp.at.homework2;

public class CheckLuckyTicket {

    private static final String EMPTY_NUMBER = "Не передан никакой номер билета";
    private static final String ODD_NUMBER_LENGTH = "В переданном номере нечетное число цифр";
    private static final String NOT_NUMERIC_SYMBOL = "В переданном номере не все символы - цифры";
    private static final String SUCCESS = "Билетик счастливый";
    private static final String UNSUCCESS = "Билетик не счастливый";

    public static AnswerCodes checkTicket(String number) {
        //Проверяем, что номер не пустой
        if ("".equals(number) || number == null) {
            return new AnswerCodes(2, EMPTY_NUMBER);
        }

        //Проверяем что количество символов в номере - четное.
        int length = number.length();
        if (length % 2 != 0) {
            return new AnswerCodes(2, ODD_NUMBER_LENGTH);
        }

        //Проверяем, что все символы в номере - цифры
        if (number.contains("+") || number.contains("-")) {
            return new AnswerCodes(2, NOT_NUMERIC_SYMBOL);
        }
        try {
            Integer.parseInt(number);
        } catch (NumberFormatException e) {
            return new AnswerCodes(2, NOT_NUMERIC_SYMBOL);
        }

        //Проверяем билетик на счастливость
        String leftNum = number.substring(0, (length / 2));
        String rightNum = number.substring(length / 2);
        if (getSumFromStringNumber(leftNum) == getSumFromStringNumber(rightNum)) {
            return new AnswerCodes(1, SUCCESS);
        }
        return new AnswerCodes(0, UNSUCCESS);
    }

    private static int getSumFromStringNumber(String number) {
        int sum = 0;
        for (int i = 0; i < number.length(); i++) {
            sum = sum + Integer.parseInt(String.valueOf(number.charAt(i)));
        }
        return sum;
    }
}
