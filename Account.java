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

    public boolean isValidPIN(String pin) {
        return this.pin.equals(pin);
    }
}
