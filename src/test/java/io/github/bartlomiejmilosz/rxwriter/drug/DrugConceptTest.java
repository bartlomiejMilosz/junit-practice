package io.github.bartlomiejmilosz.rxwriter.drug;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DrugConceptTest {

    public final static DrugConcept TEST_CONCEPT_OPIATES = new DrugConcept(
            new DrugClassification[] {
                    DrugClassification.ANTIANXIETY,
                    DrugClassification.ANALGESICS_NARCOTIC,
                    DrugClassification.NARCOTIC_ANTHISTAMINE
            });

    @Test
    void drugBelongsInConceptIfOneClassMatches() {
        DispensableDrug drug = new DispensableDrug(
                "test-drug",
                new DrugClassification[] {
                        DrugClassification.ANTIANXIETY,
                        DrugClassification.ANTIBACTERIAL
                },
                false
        );
        assertTrue(TEST_CONCEPT_OPIATES.isDrugInConcept(drug));
    }
}