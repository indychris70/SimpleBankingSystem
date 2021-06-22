package banking;

public class Account {
    private final String MII = "4";
    private final String BIN = "00000";
    private static int initialAccountNumber = 100000000;
    private final String accountNumber = generateAccountNumber();
    private final String checkSum = calculateChecksum();
    private final String cardNumber = MII + BIN + accountNumber + checkSum;
    private String PIN = generatePIN();
    private long balance = 0;

    Account() {
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getPIN() {
        return PIN;
    }

    public long getBalance() {
        return balance;
    }

    public boolean isValidPIN(String PIN) {
        return this.PIN.equals(PIN);
    }

    private String generateAccountNumber() {
        return Integer.toString(initialAccountNumber++);
    }

    private String calculateChecksum() {
        return "0";
    }

    private String generatePIN() {
        return "7777";
    }
}
