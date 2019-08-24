package br.com.site2019.reviewtutorial.repository;

import br.com.site2019.reviewtutorial.model.Summary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Repository
public class SummaryRepositoryImpl implements SummaryRepository {

    private RedisTemplate<String, Summary> summaryDB;

    public SummaryRepositoryImpl(RedisTemplate<String, Summary> summaryDB) {

        this.summaryDB = summaryDB;
    }

    @Override
    public Summary findByRestaurantId(UUID restaurantId) {

        return summaryDB.opsForValue().get(restaurantId.toString());
    }

    @Override
    public void update(Summary summary) {

        summaryDB.opsForValue().set(summary.getRestaurantId().toString(), summary, 90, TimeUnit.DAYS);
    }
}
