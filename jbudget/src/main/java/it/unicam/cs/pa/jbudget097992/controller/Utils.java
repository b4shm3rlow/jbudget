package it.unicam.cs.pa.jbudget097992.controller;

import it.unicam.cs.pa.jbudget097992.model.Transaction;
import it.unicam.cs.pa.jbudget097992.model.enumeration.ExpenseCategory;
import it.unicam.cs.pa.jbudget097992.model.enumeration.FamilyMembers;
import it.unicam.cs.pa.jbudget097992.model.enumeration.ProfitCategory;

import java.util.List;

public interface Utils {

    Transaction chooseExpense(List<Transaction> expenseList);
    Transaction chooseProfit(List<Transaction> profitList);
    FamilyMembers chooseMember(List<FamilyMembers> familyMembersList);
    ExpenseCategory chooseExpCategory(List<ExpenseCategory> expenseCategoryList);
    ProfitCategory choosePrfCategory(List<ProfitCategory> profitCategoryList);

}
