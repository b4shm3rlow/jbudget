package it.unicam.cs.pa.jbudget097992.model;

import it.unicam.cs.pa.jbudget097992.model.enumeration.ExpenseCategory;
import it.unicam.cs.pa.jbudget097992.model.enumeration.FamilyMembers;
import it.unicam.cs.pa.jbudget097992.model.enumeration.ProfitCategory;
import it.unicam.cs.pa.jbudget097992.model.enumeration.TransactionType;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class DataStorage implements Repository {

    private List<Transaction> transactions;
    private Account account;
    private static final String separatorCSV = " , ";

    public DataStorage(List<Transaction> transactions, Account account) {
        this.transactions = transactions;
        this.account = account;
    }

    Predicate<Transaction> expenseList = transaction -> transaction.getType() == TransactionType.EXPENSE;
    Predicate<Transaction> profitList = transaction -> transaction.getType() == TransactionType.PROFIT;

    @Override
    public List<Transaction> getTransactionList() {
        return transactions;
    }

    @Override
    public List<Transaction> getExpenseList() {
        return getTransactionList().stream().filter(expenseList).collect(Collectors.toList());
    }

    @Override
    public List<Transaction> getProfitList() {
        return getTransactionList().stream().filter(profitList).collect(Collectors.toList());
    }

    @Override
    public void exportData(List<Transaction> transactionList) {
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("data.csv"), StandardCharsets.UTF_8));
            StringBuffer firstline = new StringBuffer();
            firstline.append(account.getId());
            firstline.append(separatorCSV);
            firstline.append(account.getName());
            firstline.append(separatorCSV);
            firstline.append(account.getBudget());
            bw.write(firstline.toString());
            bw.newLine();
            for (Transaction transaction : transactionList) {
                StringBuffer line = new StringBuffer();
                line.append(transaction.getId().trim().length() == 0 ? "" : transaction.getId());
                line.append(separatorCSV);
                line.append(transaction.getMember() == null ? "" : transaction.getMember());
                line.append(separatorCSV);
                line.append(transaction.getAmount() < 0 ? "" : transaction.getAmount());
                line.append(separatorCSV);
                line.append(transaction.getDate() == null ? "" : transaction.getDate());
                line.append(separatorCSV);
                line.append(transaction.getType() == null ? "" : transaction.getType());
                line.append(separatorCSV);
                line.append(transaction.getNote().trim().length() == 0 ? "" : transaction.getNote());
                line.append(separatorCSV);
                line.append(transaction.getType() == TransactionType.EXPENSE ? transaction.getExpenseCategory() : transaction.getProfitCategory());

                bw.write(line.toString());
                bw.newLine();
            }
            bw.flush();
            bw.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void importData(String fileName) {
        Path pathToFile = Paths.get(fileName);
        try (BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.UTF_8)) {
            String line = br.readLine();
            loadAccount(line.split(separatorCSV));
            line = br.readLine();
            while (line != null) {
                String[] fields = line.split(separatorCSV);
                Transaction transaction = loadTransaction(fields);
                transactions.add(transaction);
                line = br.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Transaction loadTransaction(String[] fields) {
        String id = fields[0];
        FamilyMembers member = FamilyMembers.valueOf(fields[1]);
        double amount = Double.parseDouble(fields[2]);
        LocalDate date = LocalDate.parse(fields[3]);
        TransactionType type = TransactionType.valueOf(fields[4]);
        String note = fields[5];
        ExpenseCategory expenseCategory; ProfitCategory profitCategory;
        if (type == TransactionType.EXPENSE) {
            expenseCategory =  ExpenseCategory.valueOf(fields[6]);
            return new QuickTransaction(id, amount, member, type, expenseCategory, date, note);
        } else {
            profitCategory = ProfitCategory.valueOf(fields[6]);
            return new QuickTransaction(id, amount, member, type, profitCategory, date, note);
        }
    }

    private void loadAccount(String[] fields) {
        String id = fields[0];
        String name = fields[1];
        double budget = Double.parseDouble(fields[2]);
        account.setId(id);
        account.setName(name);
        account.setBudget(budget);
    }

    @Override
    public  void  sortTransactions() {
        transactions.sort(new Comparator<Transaction>() {
            @Override
            public int compare(Transaction first, Transaction second) {
                if (first.getDate().isAfter(second.getDate())) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });
    }
}
