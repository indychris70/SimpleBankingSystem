package banking;

abstract class LuhnHelper {

    public static boolean isValid(String value) {
        long number = Long.parseLong(value);
        return calculateControlNumber(number) % 10 == 0;
    }

    public static String calculateCheckDigit(String value) {
        long number = Long.parseLong(value);
        long controlNumber = calculateControlNumber(number);
        if (controlNumber % 10L == 0L) {
            return "0";
        } else {
            return Long.toString(10L - controlNumber % 10L);
        }
    }

    private static long calculateControlNumber(long num) {
        long controlNumber = 0L;
        long currentDigit;
        String value = Long.toString(num);
        for (int i = 0; i < value.length(); i++) {
            currentDigit = Long.parseLong(String.valueOf(value.charAt(i)));
            if ((i + 1) % 2 != 0) {
                currentDigit *= 2;
            }
            if (currentDigit > 9) {
                currentDigit -= 9;
            }
            controlNumber += currentDigit;
        }
        return controlNumber;
    }
}
