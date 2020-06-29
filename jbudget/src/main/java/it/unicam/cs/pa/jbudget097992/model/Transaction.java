package it.unicam.cs.pa.jbudget097992.model;

import it.unicam.cs.pa.jbudget097992.model.enumeration.FamilyMembers;
import it.unicam.cs.pa.jbudget097992.model.enumeration.TransactionType;
import it.unicam.cs.pa.jbudget097992.model.enumeration.ExpenseCategory;
import it.unicam.cs.pa.jbudget097992.model.enumeration.ProfitCategory;

import java.time.LocalDate;

public interface Transaction {

    String getId();
    FamilyMembers getMember();
    double getAmount();
    LocalDate getDate();
    TransactionType getType();
    void isScheduled(boolean scheduled);
    ExpenseCategory getExpenseCategory();
    ProfitCategory getProfitCategory();
    String getNote();

}
