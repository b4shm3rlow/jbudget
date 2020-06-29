package it.unicam.cs.pa.jbudget097992.model;

import java.io.FileNotFoundException;
import java.util.List;

public interface Repository {

    List<Transaction> getTransactionList();
    List<Transaction> getExpenseList();
    List<Transaction> getProfitList();
    void exportData(List<Transaction> transactionList) throws FileNotFoundException;
    void importData(String fileName);
    void sortTransactions();

}
