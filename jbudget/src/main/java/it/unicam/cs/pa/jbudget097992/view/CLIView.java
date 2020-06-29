package it.unicam.cs.pa.jbudget097992.view;

import it.unicam.cs.pa.jbudget097992.model.enumeration.Commands;
import java.time.LocalDate;
import java.util.*;

public class CLIView implements View, PrintItems {

    private Scanner input = new Scanner(System.in);
    private int pick;

    @Override
    public void Start() {
        PrintItems.printWelcome();
    }

    @Override
    public void Exit() {
        printExit();
    }

    @Override
    public String addNote() {
        String note;
        do {
            System.out.println("Aggiungi una descrizione o causale...");
            note = null;
            try {
                note = input.next();
            } catch (NoSuchElementException e) {
                System.out.println("ERRORE! Digitare qualcosa...");
                input.next();
            }
        } while (note.length() <= 1);
        System.out.println("Descrizione: "+note);
        return note;
    }

    @Override
    public double addAmount() {
        double amount;
        do {
            System.out.println("Aggiungi un importo...");
            amount = 0.00;
            try {
                amount = input.nextDouble();
            } catch (InputMismatchException e) {
                System.out.println("ERRORE! Digitare un importo corretto... (es: 30.00)");
                input.next();
            }
        } while (amount < 0.01);
        System.out.println("L'importo è di:"+ amount + " Euro");
        return amount;
    }

    @Override
    public Commands MainMenu() {
        do {
            pick = -1;
            printMenu();
            validAction();
        } while (pick > 14 || pick < 1);
        System.out.println("Hai scelto [" + pick + "]");
        switch (pick) {
            case 1:
                return Commands.GET_BALANCE;
            case 2:
                return Commands.ADD_EXPENSE;
            case 3:
                return Commands.ADD_PROFIT;
            case 4:
                return Commands.EDIT_EXPENSE;
            case 5:
                return Commands.EDIT_PROFIT;
            case 6:
                return Commands.DELETE_EXPENSE;
            case 7:
                return Commands.DELETE_PROFIT;
            case 8:
                return Commands.PLAN_EXPENSE;
            case 9:
                return Commands.PLAN_PROFIT;
            case 10:
                return Commands.OPEN_STATS;
            case 11:
                return Commands.SAVE_TO_FILE;
            case 12:
                return Commands.LOAD_FROM_FILE;
            case 13:
                return Commands.INFO_ACCOUNT;
            case 14:
                return  Commands.EXIT;
        }
        return null;
    }

    @Override
    public Commands MenuStats() {
        do {
            pick = -1;
            printMenuStats();
            validAction();
        } while (pick > 9 || pick < 1);
        switch (pick) {
            case 1:
                return Commands.SHOW_ALL_EXPENSES;
            case 2:
                return Commands.SHOW_ALL_PROFIT;
            case 3:
                return Commands.SHOW_RANGE_TRANSACTION;
            case 4:
                return Commands.SHOW_EXPENSE_FOR_MONTH;
            case 5:
                return Commands.SHOW_PROFIT_FOR_MONTH;
            case 6:
                return Commands.SHOW_EXPENSE_FOR_CATEGORY;
            case 7:
                return Commands.SHOW_EXPENSE_FOR_MEMBER;
            case 8:
                return Commands.SHOW_MONTHLY_MOVEMENT;
            case 9:
                return Commands.BACK;
        }
        return null;
    }

    @Override
    public  <T> T selectObj(List<T> list) {
        T selectedObj;
        selectedObj = objectList(list);
        System.out.println("Hai selezionato: "+ selectedObj);
        return selectedObj;
    }

    @Override
    public LocalDate setDate() {
        int year; int month; int day;
        LocalDate date = null;
        boolean errorDate;

        do {
            errorDate = false;
            year = setYear();
            month = setMonth();
            day = setDay();

            try {
                date = LocalDate.of(year, month, day);
            } catch (java.time.DateTimeException e) {
                System.out.println("ERRORE! INSERIRE UNA DATA CORRETTA");
                errorDate = true;
            }
        } while (errorDate);
        System.out.println("La data selezionata è: "+ date);
        return date;
    }

    @Override
    public int setDay() {
        do {
            pick = -1;
            System.out.println("Seleziona un giorno [1-31]");
            validAction();
        } while (pick > 31 || pick < 1);
            return pick;
    }

    @Override
    public int setMonth() {
        do {
            pick = -1;
            System.out.println("Seleziona un mese [1-12]");
            validAction();
        } while (pick > 31 || pick < 1);
            return pick;
    }

    @Override
    public int setYear() {
        do {
            pick = -1;
            System.out.println("Seleziona un anno (es: 2010)");
            validAction();
        } while (pick < 1);
        return pick;
    }

    @Override
    public int setFixedTime() {
        do {
            pick = -1;
            System.out.println("Per quanti mesi? ");
            validAction();
        } while (pick < 2);
        return pick;
    }

    private void validAction() {
        try {
            pick = input.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("ERRORE! Digita un numero corrispondente ad un comando.");
            input.next();
        }
    }

    private <T> T objectList(List<T> list) {
        T selectObj;
        int size = list.size();
        do {
            pick = -1;
            for (int i = 1; i <= size; i++) {
                System.out.println("["+i+"]"+list.get(i - 1));
            }
            validAction();
        }while (pick > size || pick < 1);
        selectObj = list.get(pick - 1);
        return selectObj;
    }
}