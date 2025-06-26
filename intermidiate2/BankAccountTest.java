package test;

import main.BankAccount;
import main.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BankAccountTest {
    private BankAccount account;

    @BeforeEach
    public void setup() {
        account = new BankAccount("123456", "John Doe", 1000.0);
    }

    @Test
    public void testInitialBalance() {
        assertEquals(1000.0, account.getBalance());
    }

    @Test
    public void testDeposit() {
        account.deposit(500.0);
        assertEquals(1500.0, account.getBalance());
    }

    @Test
    public void testWithdraw() {
        account.withdraw(300.0);
        assertEquals(700.0, account.getBalance());
    }

    @Test
    public void testWithdrawInsufficientFunds() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            account.withdraw(2000.0);
        });
        assertEquals("Insufficient balance.", exception.getMessage());
    }

    @Test
    public void testDepositNegativeAmount() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            account.deposit(-100.0);
        });
        assertEquals("Amount must be positive.", exception.getMessage());
    }

    @Test
    public void testTransactionHistory() {
        account.deposit(200.0);
        account.withdraw(100.0);
        List<Transaction> history = account.getTransactionHistory();
        assertEquals(3, history.size()); // Initial + Deposit + Withdraw
        assertEquals("Deposit", history.get(1).getType());
        assertEquals(200.0, history.get(1).getAmount());
        assertEquals("Withdrawal", history.get(2).getType());
    }
}

