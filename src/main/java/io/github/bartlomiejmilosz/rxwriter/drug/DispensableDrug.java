package io.github.bartlomiejmilosz.rxwriter.drug;

public record DispensableDrug(String drugName, DrugClassification[] drugClassifications, boolean isControlled) {
}
