package it.unicam.cs.pa.jbudget097992;

import it.unicam.cs.pa.jbudget097992.controller.Controller;
import it.unicam.cs.pa.jbudget097992.model.*;
import it.unicam.cs.pa.jbudget097992.view.CLIView;
import it.unicam.cs.pa.jbudget097992.view.View;

import java.util.ArrayList;
import java.util.List;

public class App {

    public static void main(String[] args) {
        List<Transaction> transactions = new ArrayList<>();
        Account test = new FamilyAccount("MyFamily",5000.00);
        Repository repository = new DataStorage(transactions, test);
        View view = new CLIView();
        Statement statement = new Statement();

        Controller controller = new Controller(view, repository, statement, test);
        controller.start();
    }
}
