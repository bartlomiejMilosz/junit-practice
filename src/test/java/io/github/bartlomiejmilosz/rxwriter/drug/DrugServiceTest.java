package io.github.bartlomiejmilosz.rxwriter.drug;

import io.github.bartlomiejmilosz.rxwriter.drug.database.DrugRecord;
import io.github.bartlomiejmilosz.rxwriter.drug.database.DrugSource;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.LoggerFactory;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;

import static org.junit.jupiter.api.Assertions.*;

class DrugServiceTest implements DrugSource {
    private static final Logger LOGGER = Logger.getLogger(DrugServiceTest.class.getSimpleName());

    @Test
    void drugsAreReturnedSorted() {
        DrugService service = new DrugService(this);
        List<DispensableDrug> foundDrugs = service.findDrugsStartingWith("as");
        assertNotNull(foundDrugs);
        assertEquals(2, foundDrugs.size());
        assertEquals("asmanex", foundDrugs.get(0).drugName());
        assertEquals("aspirin", foundDrugs.get(1).drugName());
    }

    @Test
    void throwsExceptionOnEmptyStartsWith() {
        DrugService service = new DrugService(this);
        Exception thrown = assertThrows(IllegalArgumentException.class,
                () -> service.findDrugsStartingWith("   "));
        LOGGER.log(Level.SEVERE, thrown.getMessage());
    }

    @Test
    void setDrugPropertiesCorrectly() {
        DrugService service = new DrugService(this);
        List<DispensableDrug> foundDrugs = service.findDrugsStartingWith("aspirin");
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