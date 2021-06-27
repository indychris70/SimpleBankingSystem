package banking;

public class Account {

    private final String number;
    private final String pin;
    private long balance;

    Account(String cardNumber, String pin, long balance) {
        this.number = cardNumber;
        this.pin = pin;
        this.balance = balance;
    }

    public String getNumber() {
        return number;
    }

    public String getPIN() {
        return pin;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public void addToBalance(long amount) {
        int result = new DbHelper(BankingSystem.getDb()).updateBalance(this, balance + amount);
        if (result == 1) {
            balance += amount;
        } else {
            Messages.FAILED_TO_ADD_INCOME.print();
        }
    }

    public boolean isValidPIN(String pin) {
        return this.pin.equals(pin);
    }
}
