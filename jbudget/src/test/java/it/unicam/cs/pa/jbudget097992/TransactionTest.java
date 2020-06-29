package it.unicam.cs.pa.jbudget097992;


import it.unicam.cs.pa.jbudget097992.model.*;
import it.unicam.cs.pa.jbudget097992.model.enumeration.ExpenseCategory;
import it.unicam.cs.pa.jbudget097992.model.enumeration.FamilyMembers;
import it.unicam.cs.pa.jbudget097992.model.enumeration.TransactionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionTest {

    QuickTransaction transaction = new QuickTransaction(FamilyMembers.FATHER, TransactionType.EXPENSE, LocalDate.now(), ExpenseCategory.FOOD, "test", 5.90);
    List<Transaction> transactionList;
    FamilyAccount account;
    DataStorage storage;

    @BeforeEach
    public void setUp() {
        account = new FamilyAccount("familytest",100.50);
        transactionList = new ArrayList<>();
        transactionList.add(transaction);
        storage = new DataStorage(transactionList, account);
    }

    @Test
    public void checkExpense() {
        double diff = account.getBudget() - transaction.getAmount();
        assertNotEquals(account.getBudget(), diff);
    }

    @Test
    public void addExpense() {
        assertTrue(storage.getTransactionList().add(transaction), "nuova transazione aggiunta");
    }

    @Test
    public void deleteExpense() {
        storage.getTransactionList().add(transaction);
        assertTrue(storage.getTransactionList().remove(transaction), "transazione rimossa");
    }
}
