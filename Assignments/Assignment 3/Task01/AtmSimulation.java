import java.io.*;
import java.util.*;

public class AtmSimulation {
    static class InsufficientFundsException extends Exception {
        InsufficientFundsException(String message) { super(message); }
    }
    static class InvalidAmountException extends RuntimeException {
        InvalidAmountException(String message) { super(message); }
    }
    static class Account {
        private double balance = 5000;
        void withdraw(double amount) throws InsufficientFundsException {
            if (amount <= 0) throw new InvalidAmountException("Amount must be positive");
            if (amount > balance) throw new InsufficientFundsException("Insufficient balance");
            balance -= amount;
            System.out.printf("Cash dispensed. Balance: %.2f%n", balance);
        }
    }
    public static void main(String[] args) {
        Account account = new Account();
        try (Scanner sc = new Scanner(System.in)) {
            System.out.print("Account number: ");
            String accountNo = sc.nextLine().trim();
            if (!accountNo.matches("\\d{6,16}")) throw new IllegalArgumentException("Invalid account number");
            System.out.print("Withdrawal amount: ");
            double amount = Double.parseDouble(sc.nextLine().trim());
            account.withdraw(amount);
        } catch (IllegalArgumentException e) {
            System.out.println("Input error: " + e.getMessage());
        } catch (InvalidAmountException e) {
            System.out.println("Invalid withdrawal: " + e.getMessage());
        } catch (InsufficientFundsException e) {
            System.out.println("Transaction declined: " + e.getMessage());
        } finally {
            System.out.println("ATM session closed.");
        }
    }
}