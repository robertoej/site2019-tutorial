package br.com.site2019.reviewtutorial.repository;

import br.com.site2019.reviewtutorial.model.Summary;

import java.util.UUID;

public interface SummaryRepository {

    Summary findByRestaurantId(UUID restaurantId);

    void update(Summary summary);
}
