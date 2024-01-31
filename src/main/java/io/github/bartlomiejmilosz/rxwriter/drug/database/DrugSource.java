package io.github.bartlomiejmilosz.rxwriter.drug.database;

import java.util.List;

public interface DrugSource {
    List<DrugRecord> findDrugsStartingWith(String startingString);
}