package br.com.site2019.reviewtutorial.core;

import br.com.site2019.reviewtutorial.model.Review;
import br.com.site2019.reviewtutorial.model.Summary;
import br.com.site2019.reviewtutorial.repository.SummaryRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SummaryServiceImpl implements SummaryService {

    private SummaryRepository summaryRepository;

    public SummaryServiceImpl(SummaryRepository summaryRepository) {
        this.summaryRepository = summaryRepository;
    }

    @Override
    public Summary getSummaryByRestaurantId(UUID restaurantId) {
        return summaryRepository.findByRestaurantId(restaurantId);
    }

    @Override
    public void addReview(Review review) {
        Summary summary = summaryRepository.findByRestaurantId(review.getRestaurantId());

        if (summary == null) {

            summary = new Summary(review.getRestaurantId(), 0l, 0l);
        }

        summary.setReviewCount(summary.getReviewCount() + 1);
        summary.setTotalScore(summary.getTotalScore() + review.getScore());

        summaryRepository.update(summary);
    }

    @Override
    public void cancelReview(Review review) {
        Summary summary = summaryRepository.findByRestaurantId(review.getRestaurantId());
        summary.setReviewCount(summary.getReviewCount() - 1);
        summary.setTotalScore(summary.getTotalScore() - review.getScore());

        summaryRepository.update(summary);
    }
}
