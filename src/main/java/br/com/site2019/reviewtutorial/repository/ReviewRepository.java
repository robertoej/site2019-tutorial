package br.com.site2019.reviewtutorial.repository;

import br.com.site2019.reviewtutorial.model.Review;

import java.util.List;
import java.util.UUID;

public interface ReviewRepository {

    void create(Review review);

    Review findByOrderId(UUID orderId);

    void update(Review review);

    List<Review> listAll();
}
