package banking;

import java.util.Random;

public class Bank {
    private static final int startingBalance = 0;
    private static final LuhnHelper luhnHelper = new LuhnHelper();
    private final DbHelper dbHelper;

    Bank(String db) {
        this.dbHelper = new DbHelper(db);
        this.dbHelper.update(QueryBuilder.CREATE_CARD_TABLE.build());
    }

    public Account createAccount() {
        try {
            String accountNumber = generateAccountNumber();
            String majorIndustryIdentifier = "4";
            String bankIdentificationNumber = majorIndustryIdentifier + "00000";
            String baseCardNumber = bankIdentificationNumber + accountNumber;
            String checkDigit = luhnHelper.calculateCheckDigit(baseCardNumber);
            String cardNumber = baseCardNumber + checkDigit;
            String pin = generatePIN();
            Account account = new Account(cardNumber, pin, startingBalance);
            this.dbHelper.update(QueryBuilder.INSERT_CARD.build(cardNumber, pin, Integer.toString(startingBalance)));
            return account;
        } catch (Exception e) {
            Messages.EXCEPTION.print(e.toString());
        }
        return null;
    }

    public Account getAccountByNumber(String accountNumber) {
        if (luhnHelper.isValid(accountNumber)) {
            return dbHelper.getAccount(QueryBuilder.SELECT_CARD_BY_NUMBER.build(accountNumber));
        }
        return null;
    }

    private String generateAccountNumber() {
        return Long.toString(System.nanoTime()).substring(7);
    }

    private String generatePIN() {
        final int PIN_LENGTH = 4;
        Random rand = new Random();
        StringBuilder pin = new StringBuilder(PIN_LENGTH);
        for (int i = 0; i < PIN_LENGTH; i++) {
            pin.append(rand.nextInt(10));
        }
        return pin.toString();
    }
}
