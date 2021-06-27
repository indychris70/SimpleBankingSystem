package banking;

import java.util.Scanner;

public class BankingSystem {
    private final Bank bank;
    private Account loggedInAccount;
    private String inputtedPin;
    private boolean exitSystem = false;
    private static String db;

    BankingSystem() {
        this.bank = new Bank();
    }

    public static String getDb() {
        return db;
    }

    public static void setDb(String db) {
        BankingSystem.db = db;
    }

    public void welcomeMenu() {
        String option;
        do {
            Messages.WELCOME_MENU_OPTIONS.print();
            option = getInput();
            switch (option) {
                case "1":
                    createAccount();
                    break;
                case "2":
                    logIn();
                    break;
                case "0":
                    exit();
                    break;
                default:
                    Messages.INVALID_OPTION.print();
            }
        } while (!exitSystem);
    }

    private static String getInput() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    private void createAccount() {
        Account account = bank.createAccount();
        if (account != null) {
            Messages.CARD_CREATED_SUCCESS.print(account.getNumber(), account.getPIN());
        }
    }

    private void logIn() {
        Messages.PROMPT_ENTER_CARD_NUMBER.print();
        String inputtedAccountNumber = getInput();
        Messages.PROMPT_ENTER_PIN.print();
        inputtedPin = getInput();
        loggedInAccount = bank.getAccountByNumber(inputtedAccountNumber);
        if (isValidLoggedInAccount()) {
            Messages.LOGIN_SUCCESS.print();
            loggedInMenu();
        } else {
            Messages.WRONG_NUMBER_OR_PIN.print();
        }
    }

    private boolean isValidLoggedInAccount() {
        return loggedInAccount != null && loggedInAccount.isValidPIN(inputtedPin);
    }

    private void loggedInMenu() {
        String option;
        optionLoop:
        do {
            Messages.LOGGED_IN_MENU_OPTIONS.print();
            option = getInput();
            switch (option) {
                case "1":
                    getBalance();
                    break;
                case "2":
                    addIncome();
                    break;
                case "3":
                    doTransfer();
                    break;
                case "4":
                    closeAccount();
                    break optionLoop;
                case "5":
                    logOut();
                    break optionLoop;
                case "0":
                    exit();
                    break;
                default:
                    Messages.INVALID_OPTION.print();
            }
        } while (!exitSystem);
    }

    private void getBalance() {
        long balance = loggedInAccount.getBalance();
        Messages.BALANCE_OPTION.print(Long.toString(balance));
    }

    private void addIncome() {
        Messages.ADD_INCOME.print();
        String income = getInput();
        loggedInAccount.addToBalance(Long.parseLong(income));
        Messages.CONFIRM_INCOME_ADDED.print();
    }

    private void doTransfer() {
        Messages.PROMPT_ENTER_TRANSFER_CARD_NUMBER.print();
        String transferAccountNumber = getInput();
        if (bank.isValidTransfer(loggedInAccount, transferAccountNumber)) {
            Messages.PROMPT_ENTER_TRANSFER_AMOUNT.print();
            String transferAmount = getInput();
            if (Long.parseLong(transferAmount) > loggedInAccount.getBalance()) {
                Messages.INSUFFICIENT_FUNDS.print();
            } else {
                bank.transferFunds(loggedInAccount, transferAccountNumber, Long.parseLong(transferAmount));
                Messages.CONFIRM_TRANSFER.print("Successful");
            }
        }
    }

    private void closeAccount() {
        bank.closeAccount(loggedInAccount);
        Messages.CONFIRM_ACCOUNT_CLOSED.print();
        loggedInAccount = null;
    }

    private void logOut() {
        loggedInAccount = null;
        Messages.LOGGED_OUT_SUCCESS.print();
    }

    private void exit() {
        Messages.EXIT_OPTION.print();
        exitSystem = true;
    }
}
