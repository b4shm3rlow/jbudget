package it.unicam.cs.pa.jbudget097992;

import it.unicam.cs.pa.jbudget097992.model.DataStorage;
import it.unicam.cs.pa.jbudget097992.model.FamilyAccount;
import it.unicam.cs.pa.jbudget097992.model.QuickTransaction;
import it.unicam.cs.pa.jbudget097992.model.Transaction;
import it.unicam.cs.pa.jbudget097992.model.enumeration.ExpenseCategory;
import it.unicam.cs.pa.jbudget097992.model.enumeration.FamilyMembers;
import it.unicam.cs.pa.jbudget097992.model.enumeration.TransactionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RepositoryTest {

    DataStorage storage;
    FamilyAccount account;
    List<Transaction> transactions = new ArrayList<>();

    @BeforeEach
    public void setStorage() {
        account = new FamilyAccount("TestFamily",200.50);
        QuickTransaction transaction = new QuickTransaction("id",20.5, FamilyMembers.FATHER, TransactionType.EXPENSE
                , ExpenseCategory.EDUCATION, LocalDate.now(), "test");
        transactions.add(transaction);
        storage = new DataStorage(transactions, account);
    }

    @Test
    public void testReadFile() {
        String fileName = "data.csv";
        storage.importData(fileName);
        File file = new File(fileName);
        assertTrue(file.exists());
    }
}
