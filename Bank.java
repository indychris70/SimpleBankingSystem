package banking;

import java.util.Random;

public class Bank {
    private static final int startingBalance = 0;
    private final DbHelper dbHelper;

    Bank() {
        this.dbHelper = new DbHelper(BankingSystem.getDb());
        this.dbHelper.createCardTable();
    }

    public Account createAccount() {
        try {
            String accountNumber = generateAccountNumber();
            String majorIndustryIdentifier = "4";
            String bankIdentificationNumber = majorIndustryIdentifier + "00000";
            String baseCardNumber = bankIdentificationNumber + accountNumber;
            String checkDigit = LuhnHelper.calculateCheckDigit(baseCardNumber);
            String cardNumber = baseCardNumber + checkDigit;
            String pin = generatePIN();
            Account account = new Account(cardNumber, pin, startingBalance);
            this.dbHelper.addCard(cardNumber, pin, startingBalance);
            return account;
        } catch (Exception e) {
            Messages.EXCEPTION.print(e.toString());
        }
        return null;
    }

    public void closeAccount(Account account) {
        this.dbHelper.closeAccount(account);
    }

    public boolean isValidTransfer(Account fromAccount, String toAccountNumber) {
        Account toAccount = getAccountByNumber(toAccountNumber);
        boolean isValid = true;
        if (fromAccount.getNumber().equals(toAccountNumber)) {
            Messages.ERROR_TRANSFER_TO_SAME_ACCOUNT.print();
            isValid = false;
        } else if (!LuhnHelper.isValid(toAccountNumber)) {
            Messages.ERROR_TRANSFER_CARD_NUMBER_NOT_VALID.print();
            isValid = false;
        } else if (toAccount == null) {
            Messages.ERROR_TRANSFER_CARD_NOT_FOUND.print();
            isValid = false;
        }
        return isValid;
    }

    public void transferFunds(Account fromAccount, String toAccountNumber, long amount) {
        Account toAccount = getAccountByNumber(toAccountNumber);
        boolean transferSuccess = dbHelper.transferFunds(fromAccount, toAccount, amount);
        if (transferSuccess) {
            fromAccount.setBalance(fromAccount.getBalance() - amount);
            toAccount.setBalance(toAccount.getBalance() + amount);
        }
        Messages.CONFIRM_TRANSFER.print(transferSuccess ? "Successful" : "Failed");
    }

    public Account getAccountByNumber(String accountNumber) {
        if (LuhnHelper.isValid(accountNumber)) {
            return dbHelper.selectCardByNumber(accountNumber);
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
