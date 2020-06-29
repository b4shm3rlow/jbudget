package it.unicam.cs.pa.jbudget097992.model;

import java.util.UUID;

public class FamilyAccount implements Account {

    private String id;
    private String name;
    private double budget;

    public FamilyAccount(String name, double budget) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.budget = budget;
    }

    public FamilyAccount(String id, String name, double budget) {
        this.id = id;
        this.name = name;
        this.budget = budget;
    }

    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    @Override
    public String toString() {
        return "Nome: "+name+"\n"+"Id Account: "+id+"\n";
    }
}
