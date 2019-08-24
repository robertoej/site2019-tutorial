package br.com.site2019.reviewtutorial.core;

import br.com.site2019.reviewtutorial.model.Review;
import br.com.site2019.reviewtutorial.model.Summary;

import java.util.UUID;

public interface SummaryService {

    Summary getSummaryByRestaurantId(UUID restaurantId);
    void addReview(Review review);
    void cancelReview(Review review);
}
