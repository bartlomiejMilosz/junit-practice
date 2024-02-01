package io.github.bartlomiejmilosz.rxwriter.drug;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class DrugConceptTest {

    public final static DrugConcept TEST_CONCEPT_OPIATES = new DrugConcept(
            new DrugClassification[]{
                    DrugClassification.ANTIANXIETY,
                    DrugClassification.ANALGESICS_NARCOTIC,
                    DrugClassification.NARCOTIC_ANTHISTAMINE
            });

    static Stream<Arguments> drugDataProvider() {
        return Stream.of(
                Arguments.of(
                        new DispensableDrug(
                                "test-drug-1",
                                new DrugClassification[]{
                                        DrugClassification.ANTIANXIETY,
                                        DrugClassification.ANTIBACTERIAL
                                },
                                false),
                        true // Assuming the drug is expected to match the concept
                ),
                Arguments.of(
                        new DispensableDrug(
                                "test-drug-2",
                                new DrugClassification[]{
                                        DrugClassification.ANTIFUNGAL,
                                        DrugClassification.ANTIBACTERIAL},
                                false),
                        false // Assuming the drug is not expected to match the concept
                ),
                Arguments.of(
                        new DispensableDrug(
                                "test-drug-3",
                                new DrugClassification[]{
                                        DrugClassification.ANALGESIC,
                                        DrugClassification.NARCOTIC_ANTHISTAMINE},
                                false),
                        true // Assuming the drug is expected to match the concept
                )
        );
    }

    @ParameterizedTest
    @MethodSource("drugDataProvider")
    void drugBelongsInConceptIfOneClassMatches(DispensableDrug drug, boolean expected) {
        final DrugConcept testConcept = DrugConcept.OPIATES; // Assuming this is tested concept
        assertEquals(expected, testConcept.isDrugInConcept(drug));
    }

    @Test
    void drugBelongsInConceptIfOneClassMatches() {
        DispensableDrug drug = new DispensableDrug(
                "test-drug",
                new DrugClassification[]{
                        DrugClassification.ANTIANXIETY,
                        DrugClassification.ANTIBACTERIAL
                },
                false
        );
        assertTrue(TEST_CONCEPT_OPIATES.isDrugInConcept(drug));
    }

    @Test
    void drugNotInConceptIfOneClassMatches() {
        DispensableDrug drug = new DispensableDrug(
                "test-drug",
                new DrugClassification[]{
                        DrugClassification.ACE_INHIBITORS,
                        DrugClassification.ANTIBACTERIAL
                },
                false
        );
        assertFalse(TEST_CONCEPT_OPIATES.isDrugInConcept(drug));
    }
}