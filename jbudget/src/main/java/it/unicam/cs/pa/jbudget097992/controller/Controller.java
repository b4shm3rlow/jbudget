package it.unicam.cs.pa.jbudget097992.controller;

import it.unicam.cs.pa.jbudget097992.model.*;
import it.unicam.cs.pa.jbudget097992.model.enumeration.*;
import it.unicam.cs.pa.jbudget097992.view.PrintItems;
import it.unicam.cs.pa.jbudget097992.view.View;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;


public class Controller implements Utils {

    private View cliView;
    private Repository repository;
    private Statement statement;
    private Account account;
    private List<FamilyMembers> familyMembers = Arrays.asList(FamilyMembers.values());
    private List<ExpenseCategory> expenseCategories = Arrays.asList(ExpenseCategory.values());
    private List<ProfitCategory> profitCategories = Arrays.asList(ProfitCategory.values());
    private static Logger logger = Logger.getLogger("it.unicam.cs.pa.jbudget097992");


    public Controller(View cliView, Repository repository, Statement statement, Account account) {
        this.account = account;
        this.cliView = cliView;
        this.repository = repository;
        this.statement = statement;
    }

    @Override
    public Transaction chooseExpense(List<Transaction> expenseList) {
        return cliView.selectObj(expenseList);
    }

    @Override
    public Transaction chooseProfit(List<Transaction> profitList) {
        return cliView.selectObj(profitList);
    }

    @Override
    public FamilyMembers chooseMember(List<FamilyMembers> familyMembersList) {
        return cliView.selectObj(familyMembersList);
    }

    @Override
    public ExpenseCategory chooseExpCategory(List<ExpenseCategory> expenseCategoryList) {
        return cliView.selectObj(expenseCategoryList);
    }

    @Override
    public ProfitCategory choosePrfCategory(List<ProfitCategory> profitCategoryList) {
        return cliView.selectObj(profitCategoryList);
    }

    public void start() {
        logger.info("Avvio applicazione corretto");
        cliView.Start();
        MenuAction();
    }
    private void backToMenu() {
        MenuAction();
    }

    private void exit() {
        cliView.Exit();
    }

    private void MenuAction() {
        Commands commands = cliView.MainMenu();
        switch (commands) {
            case GET_BALANCE:
                getBalance();
                break;
            case ADD_EXPENSE:
                addExpense();
                break;
            case ADD_PROFIT:
                addProfit();
                break;
            case DELETE_EXPENSE:
                deleteExpense();
                break;
            case DELETE_PROFIT:
                deleteProfit();
                break;
            case PLAN_EXPENSE:
                planExpense();
                break;
            case PLAN_PROFIT:
                planProfit();
                break;
            case EDIT_EXPENSE:
                editExpense();
                break;
            case EDIT_PROFIT:
                editProfit();
                break;
            case OPEN_STATS:
                showStats();
                break;
            case SAVE_TO_FILE:
                saveToFile();
                break;
            case LOAD_FROM_FILE:
                loadFromFile();
                break;
            case INFO_ACCOUNT:
                getInfoAccount();
                break;
            case EXIT:
                exit();
                break;
        }
    }

    private void getInfoAccount() {
        System.out.println("Informazioni Account: \n"+ account);
        MenuAction();
    }

    private void getBalance() {
        System.out.println("Budget attuale:"+account.getBudget()+" Euro");
        MenuAction();
    }

    private void addExpense() {
        TransactionType type = TransactionType.EXPENSE; //TODO: check if enum is EXPENSE
        LocalDate localDate = LocalDate.now();
        System.out.println("Effettuata da: ");
        FamilyMembers member = chooseMember(familyMembers);
        System.out.println("Categoria di Spesa: ");
        ExpenseCategory expenseCat = chooseExpCategory(expenseCategories);
        String note = cliView.addNote();
        double amount = cliView.addAmount();

        if (Double.compare(account.getBudget(), amount) < 0) {
            System.out.println("ERRORE! La spesa è troppo elevata per il budget a disposizione!");
            MenuAction();
        } else {
            Transaction expense = new QuickTransaction(member, type, localDate, expenseCat, note, amount);
            expense.isScheduled(false);
            repository.getTransactionList().add(expense);
            repository.sortTransactions();
            double newBalance = account.getBudget() - amount;
            account.setBudget(newBalance);
            MenuAction();
        }
    }

    private void addProfit() {
        TransactionType type = TransactionType.PROFIT;
        LocalDate localDate = LocalDate.now();
        System.out.println("Effettuata da: ");
        FamilyMembers member = chooseMember(familyMembers);
        System.out.println("Categorie di Guadagno:");
        ProfitCategory profitCat = choosePrfCategory(profitCategories);
        String note = cliView.addNote();
        double amount = cliView.addAmount();

        Transaction profit = new QuickTransaction(member, type, localDate, profitCat, note, amount);
        profit.isScheduled(false);
        repository.getTransactionList().add(profit);
        repository.sortTransactions();
        double newBalance = account.getBudget() + amount;
        account.setBudget(newBalance);
        MenuAction();
    }

    private void deleteExpense() {
        if (repository.getExpenseList().isEmpty()) {
            System.out.println("Nessuna transazione di spesa trovata...");
            MenuAction();
        } else {
            Transaction choosenExpense = chooseExpense(repository.getExpenseList());
            double amount = choosenExpense.getAmount();
            double updateBalance = account.getBudget() + amount;
            account.setBudget(updateBalance);
            repository.getTransactionList().remove(choosenExpense);
            MenuAction();
        }
    }

    private void deleteProfit() {
        if (repository.getProfitList().isEmpty()) {
            System.out.println("Nessuna transazione di entrata trovata...");
            MenuAction();
        } else {
            Transaction choosenProfit = chooseProfit(repository.getProfitList());
            double amount = choosenProfit.getAmount();
            double updateBalance = account.getBudget() - amount;
            account.setBudget(updateBalance);
            repository.getTransactionList().remove(choosenProfit);
            MenuAction();
        }
    }

    private void editExpense() {
        if (repository.getExpenseList().isEmpty()) {
            System.out.println("Nessuna transazione di spesa trovata...");
            MenuAction();
        } else {
            Transaction choosenExpense = chooseExpense(repository.getExpenseList());
            repository.getTransactionList().remove(choosenExpense);
            addExpense();
            MenuAction();
        }
    }

    private void editProfit() {
        if (repository.getProfitList().isEmpty()) {
            System.out.println("Nessuna transazione di entrata trovata...");
            MenuAction();
        } else {
            Transaction choosenProfit = chooseProfit(repository.getProfitList());
            repository.getTransactionList().remove(choosenProfit);
            addProfit();
            MenuAction();
        }
    }

    private void planExpense() {
        TransactionType type = TransactionType.EXPENSE; //TODO: check if enum is EXPENSE
        LocalDate localDate = cliView.setDate();
        System.out.println("Effettuata da: ");
        FamilyMembers member = chooseMember(familyMembers);
        System.out.println("Categoria di Spesa: ");
        ExpenseCategory expenseCat = chooseExpCategory(expenseCategories);
        String note = cliView.addNote();
        double amount = cliView.addAmount();

        if (Double.compare(account.getBudget(), amount) < 0) {
            System.out.println("ERRORE! La spesa è troppo elevata per il budget a disposizione!");
            MenuAction();
        } else {
            Transaction expense = new QuickTransaction(member, type, localDate, expenseCat, note, amount);
            expense.isScheduled(true);
            repository.getTransactionList().add(expense);
            repository.sortTransactions();
            double newBalance = account.getBudget() - amount;
            account.setBudget(newBalance);
            MenuAction();
        }
    }

    private void planProfit() {
        TransactionType type = TransactionType.PROFIT;
        System.out.println("Effettuata da: ");
        FamilyMembers member = chooseMember(familyMembers);
        String note = cliView.addNote();
        double amount = cliView.addAmount();
        LocalDate localDate = cliView.setDate();
        System.out.println("Categorie di Guadagno:");
        ProfitCategory profitCat = choosePrfCategory(profitCategories);
        if (profitCat.equals(ProfitCategory.SALARY) || profitCat.equals(ProfitCategory.PENSION)) {
            int period =cliView.setFixedTime();
            for (int i=1; i <= period; i++) {
                Transaction profit = new QuickTransaction(member, type, localDate, profitCat, note, amount);
                profit.isScheduled(true);
                repository.getTransactionList().add(profit);
                repository.sortTransactions();
                double newBalance = account.getBudget() + amount;
                account.setBudget(newBalance);
                localDate = localDate.plusMonths(1);
            }
        } else {
            Transaction profit = new QuickTransaction(member, type, localDate, profitCat, note, amount);
            repository.getTransactionList().add(profit);
            repository.sortTransactions();
            double newBalance = account.getBudget() + amount;
            account.setBudget(newBalance);
        }
        MenuAction();
    }

    private void saveToFile() {
        List<Transaction> transactions = repository.getTransactionList();
        try {
            repository.exportData(transactions);
            logger.info("Dati salvati correttamente");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        MenuAction();
    }

    private void loadFromFile() {
        try {
            repository.importData("data.csv");
            logger.info("Dati importanti correttamente");
        } catch (Exception e) {
            e.printStackTrace();
        }
        MenuAction();
    }

    private void showStats() {
        int year;
        int month;
        Commands commands = cliView.MenuStats();
        switch (commands) {
            case SHOW_ALL_EXPENSES:
                PrintItems.printList(repository.getExpenseList());
                break;
            case SHOW_ALL_PROFIT:
                PrintItems.printList(repository.getProfitList());
                break;
            case SHOW_RANGE_TRANSACTION:
                System.out.println("Seleziona la prima data... ");
                LocalDate firstDate = cliView.setDate();
                System.out.println("Seleziona la seconda data... ");
                LocalDate secondDate = cliView.setDate();
                List<Transaction> filterTransaction = statement.transactionForRange(repository, firstDate, secondDate);
                PrintItems.printList(filterTransaction);
                break;
            case SHOW_EXPENSE_FOR_CATEGORY:
                year = cliView.setYear();
                month = cliView.setMonth();
                String[] expenseForCat = statement.expenseForCategory(repository, expenseCategories, year, month);
                PrintItems.printStats(expenseForCat);
                break;
            case SHOW_EXPENSE_FOR_MONTH:
                year = cliView.setYear();
                String[] expensesMonth = statement.expensesForMonth(repository, year);
                PrintItems.printStats(expensesMonth);
                break;
            case SHOW_EXPENSE_FOR_MEMBER:
                year = cliView.setYear();
                month = cliView.setMonth();
                String[] expenseForMember = statement.expensesForMember(repository, familyMembers, year, month);
                PrintItems.printStats(expenseForMember);
                break;
            case SHOW_PROFIT_FOR_MONTH:
                year = cliView.setYear();
                String[] profitsMonth = statement.profitForMonth(repository, year);
                PrintItems.printStats(profitsMonth);
                break;
            case SHOW_MONTHLY_MOVEMENT:
                year = cliView.setYear();
                String[] monthlyMovement = statement.monthlyMovement(repository, year);
                PrintItems.printStats(monthlyMovement);
                break;
            case BACK:
                backToMenu();
                break;
        }
        MenuAction();
    }
}