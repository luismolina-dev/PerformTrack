package com.app.performtrackapi.services.Metrics.formulas;

import com.app.performtrackapi.entities.Record;
import java.util.UUID;

public interface MetricStrategy {

    Double calculateCategoryScore(Record record, UUID categoryId);

}
