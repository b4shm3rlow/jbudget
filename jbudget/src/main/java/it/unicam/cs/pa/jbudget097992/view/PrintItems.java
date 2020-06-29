package it.unicam.cs.pa.jbudget097992.view;

import java.util.List;

public interface PrintItems {

    /**
     * Interfaccia che implementa i metodi di stampa utili per la vista console
     */
    static void printWelcome() {
        System.out.println("*************************************");
        System.out.println("***   Benvenuto in jBudget App!   ***");
        System.out.println("*************************************");
        System.out.println("                                     ");
    }

    default void printExit() {
        System.out.println("*************************************");
        System.out.println("*****        Arrivederci!      ******"); //TODO: add user's name to welcome print
        System.out.println("*************************************");
        System.out.println("                                     ");
    }

    default void printMenu() {
        System.out.println("                                     ");
        System.out.println("*************************************");
        System.out.println("*********  MENU PRINCIPALE  *********");
        System.out.println("                                     ");
        System.out.println("[1] Mostra budget attuale;");
        System.out.println("[2] Aggiungi una spesa;");
        System.out.println("[3] Aggiungi un guadagno;");
        System.out.println("[4] Modifica una spesa;");
        System.out.println("[5] Modifica un guadagno;");
        System.out.println("[6] Elimina una spesa;");
        System.out.println("[7] Elimina un guadagno;");
        System.out.println("[8] Pianifica una spesa;");
        System.out.println("[9] Pianifica un guadagno;");
        System.out.println("[10] Statistiche;");
        System.out.println("[11] Salva le transazioni su un file CSV;");
        System.out.println("[12] Carica le transazioni da un file CSV;");
        System.out.println("                                     ");
        System.out.println("[13] Account;");
        System.out.println("[14] Esci dall'applicazione.");
        System.out.println("*************************************");
    }

    default void printMenuStats() {
        System.out.println("                                     ");
        System.out.println("*********  STATISTICHE  *********");
        System.out.println("                                     ");
        System.out.println("[1] Mostra tutte le spese;");
        System.out.println("[2] Mostra tutti i guadagni;");
        System.out.println("[3] Mostra le transazioni effettuate fra due date;");
        System.out.println("[4] Mostra le spese mensili totali;");
        System.out.println("[5] Mostra i guadagni mensili totali.");
        System.out.println("[6] Mostra le spese mensili per categoria;");
        System.out.println("[7] Mostra le spese per membro della famiglia;");
        System.out.println("[8] Mostra i bilanci e risparmi mensili;");
        System.out.println("                                     ");
        System.out.println("[9] Torna al menu principale.");
    }

    static void printStats(String[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.println(array[i]);
        }
    }

    static <T> void printList(List<T> list) {
        System.out.println("********** LISTA TRANSAZIONI *********");
        for (T element : list) {
            System.out.println(element);
        }
        System.out.println();
    }
}
