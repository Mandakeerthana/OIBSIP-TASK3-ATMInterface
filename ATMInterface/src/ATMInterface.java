import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ATMInterface {
    private static Map<Integer, Integer> users = new HashMap<>();
    private static Map<Integer, Integer> accountBalances = new HashMap<>();
    private static int currentUserId;
    private static Scanner scanner = new Scanner(System.in);
    private static Map<Integer, StringBuilder> transactionsHistory = new HashMap<>();

    public static void main(String[] args) {
        // Adding some example users with their PIN and account balances (Replace with real database handling)
        users.put(123456, 1111);
        accountBalances.put(123456, 10000);
        users.put(654321, 2222);
        accountBalances.put(654321, 5000);
        users.put(240410, 4444);
        accountBalances.put(240410, 20000);
        showWelcomeScreen();
    }

    private static void showWelcomeScreen() {
        System.out.println("===== Welcome to the ATM Interface =====");
        System.out.print("Enter your user ID: ");
        int userId = scanner.nextInt();
        System.out.print("Enter your PIN: ");
        int pin = scanner.nextInt();

        if (authenticateUser(userId, pin)) {
            System.out.println("Login successful!");
            showMainMenu();
        } else {
            System.out.println("Invalid user ID or PIN. Please try again.");
            showWelcomeScreen();
        }
    }

    private static boolean authenticateUser(int userId, int pin) {
        Integer storedPin = users.get(userId);
        if (storedPin != null && storedPin == pin) {
            currentUserId = userId;
            return true;
        }
        return false;
    }

    private static void showMainMenu() {
        System.out.println("\n===== Main Menu =====");
        System.out.println("1. Transactions History");
        System.out.println("2. Withdraw");
        System.out.println("3. Deposit");
        System.out.println("4. Transfer");
        System.out.println("5. Logout");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                showTransactionsHistory();
                break;
            case 2:
                performWithdrawal();
                break;
            case 3:
                performDeposit();
                break;
            case 4:
                performTransfer();
                break;
            case 5:
                System.out.println("Logged out. Thank you!");
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                showMainMenu();
        }
    }

    private static void showTransactionsHistory() {
        System.out.println("===== Transactions History =====");
        StringBuilder history = transactionsHistory.getOrDefault(currentUserId, new StringBuilder("No transactions found."));
        System.out.println(history);
        showMainMenu();
    }

    private static void performWithdrawal() {
        System.out.print("Enter the amount to withdraw: ");
        int amount = scanner.nextInt();

        if (amount <= accountBalances.get(currentUserId)) {
            accountBalances.put(currentUserId, accountBalances.get(currentUserId) - amount);
            System.out.println("Withdrawal successful. Remaining balance: " + accountBalances.get(currentUserId));
            updateTransactionsHistory("Withdrawal: -" + amount);
        } else {
            System.out.println("Insufficient balance. Please try again.");
        }

        showMainMenu();
    }

    private static void performDeposit() {
        System.out.print("Enter the amount to deposit: ");
        int amount = scanner.nextInt();

        accountBalances.put(currentUserId, accountBalances.get(currentUserId) + amount);
        System.out.println("Deposit successful. Updated balance: " + accountBalances.get(currentUserId));
        updateTransactionsHistory("Deposit: +" + amount);

        showMainMenu();
    }

    private static void performTransfer() {
        System.out.print("Enter the recipient's user ID: ");
        int recipientUserId = scanner.nextInt();

        System.out.print("Enter the amount to transfer: ");
        int amount = scanner.nextInt();

        if (amount <= accountBalances.get(currentUserId)) {
            accountBalances.put(currentUserId, accountBalances.get(currentUserId) - amount);
            accountBalances.put(recipientUserId, accountBalances.get(recipientUserId) + amount);
            System.out.println("Transfer successful. Updated balance: " + accountBalances.get(currentUserId));
            updateTransactionsHistory("Transfer to " + recipientUserId + ": -" + amount);
        } else {
            System.out.println("Insufficient balance. Please try again.");
        }

        showMainMenu();
    }

    private static void updateTransactionsHistory(String transaction) {
        StringBuilder history = transactionsHistory.getOrDefault(currentUserId, new StringBuilder());
        history.append("\n").append(transaction);
        transactionsHistory.put(currentUserId, history);
    }
}
