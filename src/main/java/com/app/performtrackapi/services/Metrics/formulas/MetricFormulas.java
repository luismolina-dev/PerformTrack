package com.app.performtrackapi.services.Metrics.formulas;

import com.app.performtrackapi.entities.Answer;
import com.app.performtrackapi.entities.Record;
import com.app.performtrackapi.entities.Category;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class MetricFormulas implements MetricStrategy {

    @Override
    public Double calculateCategoryScore(Record record, UUID categoryId) {

        Integer weight = record.getEvaluation().getCategories().stream()
                .filter(cat -> cat.getId().equals(categoryId))
                .map(Category::getWeight)
                .findFirst()
                .orElse(0);

        if (record.getAnswers() == null || record.getAnswers().isEmpty()) {
            return 0.0;
        }

        List<Answer> categoryAnswers = record.getAnswers().stream()
                .filter(a -> a.getQuestionId().getCategory().getId().equals(categoryId))
                .toList();

        if (categoryAnswers.isEmpty()) {
            return 0.0;
        }

        double sum = categoryAnswers.stream()
                .mapToDouble(Answer::getValue)
                .sum();

        double score = (sum / (categoryAnswers.size() * 5)) * 100;

        return (score * weight) / 150;
    }

}
