package io.github.bartlomiejmilosz.rxwriter.drug;

import io.github.bartlomiejmilosz.rxwriter.drug.database.DrugRecord;
import io.github.bartlomiejmilosz.rxwriter.drug.database.DrugSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

class DrugServiceTest implements DrugSource {
    private static final Logger LOGGER = Logger.getLogger(DrugServiceTest.class.getSimpleName());
    private DrugService drugService;

    @BeforeEach
    void setup() {
        drugService = new DrugService(this);
    }

    @Test
    @Tag("database")
    void drugsAreReturnedSorted() {
        List<DispensableDrug> foundDrugs = drugService.findDrugsStartingWith("as");
        assertNotNull(foundDrugs);
        assertEquals(2, foundDrugs.size());
        assertEquals("asmanex", foundDrugs.get(0).drugName());
        assertEquals("aspirin", foundDrugs.get(1).drugName());
    }

    @Test
    void throwsExceptionOnEmptyStartsWith() {
        Exception thrown = assertThrows(IllegalArgumentException.class,
                () -> drugService.findDrugsStartingWith("   "));
        LOGGER.log(Level.SEVERE, thrown.getMessage());
    }

    @Test
    @Tag("database")
    void setDrugPropertiesCorrectly() {
        List<DispensableDrug> foundDrugs = drugService.findDrugsStartingWith("aspirin");
        DrugClassification[] expectedClassifications = new DrugClassification[]{
                DrugClassification.ANALGESIC, DrugClassification.PLATELET_AGGREGATION_INHIBITORS
        };
        DispensableDrug drug = foundDrugs.get(0);
        assertAll("DispensableDrug properties",
                () -> assertEquals("aspirin", drug.drugName()),
                () -> assertFalse(drug.isControlled()),
                () -> assertEquals(2, drug.drugClassifications().length),
                () -> assertArrayEquals(expectedClassifications, drug.drugClassifications())
        );
    }

    @Override
    public List<DrugRecord> findDrugsStartingWith(String startingString) {
        List<DrugRecord> records = new ArrayList<>();
        if (startingString.equals("as")) {
            records.add(new DrugRecord("aspirin", new int[] {3645, 3530}, 0));
            records.add(new DrugRecord("asmanex", new int[] {301}, 0));
        } else if (startingString.equals("aspirin")) {
            records.add(new DrugRecord("aspirin", new int[] {3645, 3530}, 0));
        }
        return records;
    }
}