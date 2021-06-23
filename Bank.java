package banking;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class Bank {

    private final List<Account> accounts;
    private static int initialAccountNumber = 100000000;
    private static final LuhnHelper luhnHelper = new LuhnHelper();

    Bank() {
        accounts = new ArrayList<>();
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
            Account account = new Account(cardNumber, pin);
            accounts.add(account);
            return account;
        } catch (Exception e) {
            Messages.EXCEPTION.print(e.toString());
        }
        return null;
    }

    public Account getAccountByNumber(String accountNumber) {
        if (luhnHelper.isValid(accountNumber)) {
            for (Account account : accounts) {
                if (account.getNumber().equals(accountNumber)) {
                    return account;
                }
            }
        }
        return null;
    }

    private String generateAccountNumber() {
        return Integer.toString(initialAccountNumber++);
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
