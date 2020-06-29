package it.unicam.cs.pa.jbudget097992;

import it.unicam.cs.pa.jbudget097992.model.FamilyAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AccountTest {

    private FamilyAccount account;

    @BeforeEach
    public void setUp() {
        account = new FamilyAccount("MyFamilyTest",0.0);
    }

    @Test
    public void setBudget() {
        account.setBudget(3500.00);
        assertEquals(3500,account.getBudget(),0.0);
    }
}
