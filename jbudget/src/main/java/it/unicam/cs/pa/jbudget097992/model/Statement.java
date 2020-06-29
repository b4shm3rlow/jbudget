package it.unicam.cs.pa.jbudget097992.model;

import it.unicam.cs.pa.jbudget097992.model.enumeration.ExpenseCategory;
import it.unicam.cs.pa.jbudget097992.model.enumeration.FamilyMembers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Statement {

    StringBuilder sb;
    String strYear;
    double sum;
    double expenseSum;
    double profitSum;

    public String[] expenseForCategory(Repository repository, List<ExpenseCategory> categories, int year, int month) {
        List<Transaction> expenseList = repository.getExpenseList();
        strYear = String.valueOf(year);
        double sumForCategory;
        String[] table = new String[(categories.size()+1)];
        table[0] = "| Anno | Mese | Categoria | Spesa Totale (Euro) ";
        int j = 1;
        for (ExpenseCategory category : categories) {
            sumForCategory = 0.0;
            for (Transaction expense : expenseList) {
                if (expense.getDate().getYear() == year && expense.getDate().getMonthValue() == month) {
                    if (expense.getExpenseCategory().equals(category)) {
                            sumForCategory += expense.getAmount();
                    }
                }
            }
            sb = new StringBuilder("|").append(String.format("%-5s", strYear)).append(" |")
                    .append(String.format("%-6s", month)).append("|").append(String.format("%-9s", category))
                    .append(" |").append(String.format("%8s", sumForCategory));
            table[j] = sb.toString();
            j++;
        }
        return table;
    }

    public String[] expensesForMonth(Repository repository, int year) {
        List<Transaction> expenseList = repository.getExpenseList();
        strYear = String.valueOf(year);
        String[] table = new String[13]; //12 mesi + titolo
        table[0] = "| Anno | Mese | Spesa Totale (Euro) ";
        int j = 1;
        for (int i=1; i<=12; i++) {
            sum = 0.0;
            for (Transaction expense : expenseList) {
                if (expense.getDate().getYear() == year && expense.getDate().getMonthValue() == i) {
                    sum += expense.getAmount();
                }
            }
            sb = new StringBuilder("|").append(String.format("%5s", strYear)).append(" |")
                    .append(String.format("%4s", i)).append("  |").append(String.format("%8s", sum));
            table[j] = sb.toString();
            j++;
        }
        return table;
    }

    public String[] expensesForMember(Repository repository, List<FamilyMembers> familyMembers, int year, int month) {
        List<Transaction> expenseList = repository.getExpenseList();
        strYear = String.valueOf(year);
        String[] table = new String[(familyMembers.size()+1)];
        table[0] = "| Anno | Mese | Eseguite da | Spesa Totale (Euro) ";
        int j = 1;
        for (FamilyMembers member : familyMembers) {
            sum = 0.0;
            for (Transaction expense : expenseList) {
                if (expense.getDate().getYear() == year && expense.getDate().getMonthValue() == month) {
                    if (expense.getMember().equals(member)) {
                        sum += expense.getAmount();
                    }
                }
            }
            sb = new StringBuilder("|").append(String.format("%-5s", strYear)).append(" |")
                    .append(String.format("%-6s", month)).append("|").append(String.format("%-9s", member))
                    .append(" |").append(String.format("%8s", sum));
            table[j] = sb.toString();
            j++;
        }
        return table;
    }

    public String[] profitForMonth(Repository repository, int year) {
        List<Transaction> profitList = repository.getProfitList();
        strYear = String.valueOf(year);
        String[] table = new String[13]; //12 mesi + titolo
        table[0] = "| Anno | Mese | Guadagno Totale (Euro) ";
        int j = 1;
        for (int i=1; i<=12; i++) {
            sum = 0.0;
            for (Transaction profit : profitList) {
                if (profit.getDate().getYear() == year && profit.getDate().getMonthValue() == i) {
                    sum += profit.getAmount();
                }
            }
            sb = new StringBuilder("|").append(String.format("%5s", strYear)).append(" |")
                    .append(String.format("%4s", i)).append("  |").append(String.format("%8s", sum));
            table[j] = sb.toString();
            j++;
        }
        return table;
    }

    public String [] monthlyMovement(Repository repository, int year) {
        strYear = String.valueOf(year);
        double savings;
        String[] table = new String[13];
        table[0] = "| Anno | Mese | Spesa | Guadagni | Risparmi ";
        String[] profitForMonth = profitForMonth(repository, year);
        String[] expenseForMonth = expensesForMonth(repository, year);
        List<String[]> profitAfterSplit = new ArrayList<>();
        List<String[]> expensesAfterSplit = new ArrayList<>();
        for (int i=1; i<=12; i++) {
            expensesAfterSplit.add(expenseForMonth[i].split("[|]"));
            profitAfterSplit.add(profitForMonth[i].split("[|]"));
        }
        int j = 1;
        for (int i=1; i<=12; i++) {
            expenseSum = Double.valueOf(expensesAfterSplit.get(i-1)[3].replace(",","."));
            profitSum = Double.valueOf(profitAfterSplit.get(i-1)[3].replace(",","."));
            savings = profitSum - expenseSum;
            sb = new StringBuilder("|").append(String.format("%-5s", year)).append("|")
                    .append(String.format("%-6s", i)).append("|")
                    .append(String.format("%8.2f", expenseSum)).append("|")
                    .append(String.format("%8.2f", profitSum)).append("|")
                    .append(String.format("%9.2f", savings));
            table[j] = sb.toString();
            j++;
        }
        return table;
    }

    public List<Transaction> transactionForRange(Repository repository, LocalDate firstDate, LocalDate secondDate) {
        List<Transaction> transactionList = repository.getTransactionList().stream()
                .filter(t -> t.getDate().isAfter(firstDate) && t.getDate().isBefore(secondDate)).collect(Collectors.toList());
        return transactionList;
    }
}
