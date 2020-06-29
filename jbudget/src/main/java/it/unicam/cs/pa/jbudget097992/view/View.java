package it.unicam.cs.pa.jbudget097992.view;

import it.unicam.cs.pa.jbudget097992.model.enumeration.Commands;

import java.time.LocalDate;
import java.util.List;

public interface View {

    void Start();
    void Exit();
    String addNote();
    double addAmount();
    LocalDate setDate();
    <T> T selectObj(List<T> list);
    int setYear();
    int setMonth();
    int setDay();
    int setFixedTime();
    Commands MainMenu();
    Commands MenuStats();
}
