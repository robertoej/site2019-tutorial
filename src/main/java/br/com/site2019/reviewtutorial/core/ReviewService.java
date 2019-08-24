package br.com.site2019.reviewtutorial.core;

import br.com.site2019.reviewtutorial.model.Review;

import java.util.UUID;

public interface ReviewService {

    Review createReview(Review review);

    void cancelReview(UUID orderUuid);
}
