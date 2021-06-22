package banking;

import java.util.List;
import java.util.ArrayList;

public class Bank {

    private List<Account> accounts;

    Bank() {
        accounts = new ArrayList<Account>();
    }

    public Account createAccount() {
        Account account = new Account();
        accounts.add(account);
        return account;
    }

    public Account getAccountByNumber(String accountNumber) {
        for (Account account : accounts) {
            if (account.getCardNumber().equals(accountNumber)) {
                return account;
            }
        }
        return null;
    }
}
