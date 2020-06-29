package it.unicam.cs.pa.jbudget097992.model;

import it.unicam.cs.pa.jbudget097992.model.enumeration.FamilyMembers;
import it.unicam.cs.pa.jbudget097992.model.enumeration.TransactionType;
import it.unicam.cs.pa.jbudget097992.model.enumeration.ExpenseCategory;
import it.unicam.cs.pa.jbudget097992.model.enumeration.ProfitCategory;

import java.time.LocalDate;

public class QuickTransaction implements Transaction {

    private String id;
    private double amount;
    private FamilyMembers member;
    private TransactionType type;
    private ExpenseCategory expenseCategory;
    private ProfitCategory profitCategory;
    private LocalDate date;
    private String note;
    private boolean scheduled;
    private static long idcount = 0;


    public QuickTransaction(FamilyMembers member, TransactionType type, LocalDate date, ExpenseCategory category, String note, double amount) {
        this.id = "idtransaction097992-"+newId();
        this.member = member;
        this.type = type;
        this.date = date;
        this.expenseCategory = category;
        this.note = note;
        this.amount = amount;
    }

    public QuickTransaction(FamilyMembers member, TransactionType type, LocalDate date, ProfitCategory category, String note, double amount) {
        this.id = "idtransaction097992-"+newId();
        this.member = member;
        this.type = type;
        this.date = date;
        this.profitCategory = category;
        this.note = note;
        this.amount = amount;
    }

    public QuickTransaction(String id, double amount, FamilyMembers member, TransactionType type, ExpenseCategory expenseCategory, LocalDate date, String note) {
        this.id = "idtransaction097992-"+newId();
        this.amount = amount;
        this.member = member;
        this.type = type;
        this.expenseCategory = expenseCategory;
        this.date = date;
        this.note = note;
    }

    public QuickTransaction(String id, double amount, FamilyMembers member, TransactionType type, ProfitCategory profitCategory, LocalDate date, String note) {
        this.id = "idtransaction097992-"+newId();
        this.amount = amount;
        this.member = member;
        this.type = type;
        this.profitCategory = profitCategory;
        this.date = date;
        this.note = note;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public FamilyMembers getMember() {
        return member;
    }

    @Override
    public double getAmount() {
        return amount;
    }

    @Override
    public LocalDate getDate() {
        return date;
    }

    @Override
    public TransactionType getType() {
        return type;
    }

    @Override
    public void isScheduled(boolean scheduled) {
        this.scheduled = scheduled;
    }

    @Override
    public ExpenseCategory getExpenseCategory() {
        return expenseCategory;
    }

    @Override
    public ProfitCategory getProfitCategory() {
        return profitCategory;
    }

    @Override
    public String getNote() {
        return note;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("TRANSAZIONE:");
        sb.append("Id= '").append(id).append('\'');
        sb.append(", Importo= ").append(amount);
        sb.append(", Eseguito da= '").append(member).append('\'');
        sb.append(", Tipo= ").append(type);
        sb.append(", Data= ").append(date);
        sb.append(", Note= '").append(note).append('\'');
        sb.append('|');
        return sb.toString();
    }

    private long newId() {
        return idcount++;
    }
}
