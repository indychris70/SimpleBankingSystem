package banking;

public class Main {
    public static void main(String[] args) {
        BankingSystem.setDb(getDbFromArgs(args));
        BankingSystem system = new BankingSystem();
        system.welcomeMenu();
    }

    private static String getDbFromArgs(String[] args) throws IllegalArgumentException {
        for (int i = 0; i < args.length; i++) {
            if (args[i] == "-fileName") {
                return args[i + 1];
            }
        }
        throw new IllegalArgumentException();
    }
}