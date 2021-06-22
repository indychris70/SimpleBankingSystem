package banking;

import java.util.Scanner;

public class Main {
    private enum Messages {
        NOT_LOGGED_IN_OPTIONS("1. Create an account\n" +
                "2. Log into account\n" +
                "0. Exit\n"),
        LOGGED_IN_OPTIONS("1. Create an account\n" +
                "2. Log into account\n" +
                "0. Exit"),
        CARD_CREATED_SUCCESS("Your card has been created\n" +
                "Your card number:\n" +
                "%s\n" +
                "Your card PIN:\n" +
                "%s\n"),
        LOGIN_SUCCESS("You have successfully logged in!"),
        LOGGED_OUT_SUCCESS("You have successfully logged out!"),
        EXIT("Bye!"),
        ENTER_CARD_NUMBER("Enter your card number:"),
        ENTER_PIN("Enter your PIN:"),
        INVALID_OPTION("The option you entered is not valid"),
        BALANCE("Balance: %s"),
        WRONG_NUMBER_OR_PIN("Wrong card number of PIN!");

        String text;

        Messages(String text) {
            this.text = text;
        }

        private void print(String... strings) {
            System.out.println(String.format(text, strings));
        }
    }

    public static void main(String[] args) {
        Bank bank = new Bank();
        Account account;
        String option;

        do {
            Messages.NOT_LOGGED_IN_OPTIONS.print();
            option = getInput();
            switch (option) {
                case "1":
                    account = bank.createAccount();
                    Messages.CARD_CREATED_SUCCESS.print(account.getCardNumber(), account.getPIN());
                    break;
                case "2":
                    Messages.ENTER_CARD_NUMBER.print();
                    String accountNumber = getInput();
                    Messages.ENTER_PIN.print();
                    String pin  = getInput();
                    account = bank.getAccountByNumber(accountNumber);
                    if (account == null || !account.isValidPIN(pin)) {
                        Messages.WRONG_NUMBER_OR_PIN.print();
                        break;
                    } else {
                        Messages.LOGIN_SUCCESS.print();
                        loggedInLoop:
                        do {
                            Messages.LOGGED_IN_OPTIONS.print();
                            option = getInput();
                            switch (option) {
                                case "1":
                                    Messages.BALANCE.print(Long.toString(account.getBalance()));
                                    break;
                                case "2":
                                    account = null;
                                    Messages.LOGGED_OUT_SUCCESS.print();
                                    break loggedInLoop;
                                case "0":
                                    Messages.EXIT.print();
                                    break;
                                default:
                                    Messages.INVALID_OPTION.print();

                            }
                        } while (!"0".equals(option));
                    }
                    break;
                case "0":
                    Messages.EXIT.print();
                    break;
                default:
                    Messages.INVALID_OPTION.print();
            }
        } while (!"0".equals(option));
    }

    private static String getInput() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}