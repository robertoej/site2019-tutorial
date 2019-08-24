package br.com.site2019.reviewtutorial.repository;

import br.com.site2019.reviewtutorial.model.Review;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Repository
public class ReviewRepositoryImpl implements ReviewRepository {
    public static final String PREFIX = "REVIEW:";

    private RedisTemplate<String, Review> reviewDB;

    public ReviewRepositoryImpl(RedisTemplate<String, Review> reviewDB) {
        this.reviewDB = reviewDB;
    }

    @Override
    public void create(Review review) {
        reviewDB.opsForValue().set(review.getOrderId().toString(), review, 90, TimeUnit.DAYS);
    }

    @Override
    public Review findByOrderId(UUID orderId) {
        return reviewDB.opsForValue().get(orderId.toString());
    }

    @Override
    public void update(Review review) {
        reviewDB.opsForValue().set(PREFIX + review.getOrderId().toString(), review);
    }

    @Override
    public List<Review> listAll() {
        final Set<String> keys = reviewDB.keys(PREFIX + "*");

        return reviewDB.opsForValue().multiGet(keys);
    }
}
