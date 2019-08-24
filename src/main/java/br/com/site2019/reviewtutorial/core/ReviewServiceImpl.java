package br.com.site2019.reviewtutorial.core;

import br.com.site2019.reviewtutorial.model.Review;
import br.com.site2019.reviewtutorial.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ReviewServiceImpl implements ReviewService {

    private ReviewRepository reviewRepository;

    private SummaryService summaryService;

    public ReviewServiceImpl(ReviewRepository reviewRepository, SummaryService summaryService) {
        this.reviewRepository = reviewRepository;
        this.summaryService = summaryService;
    }

    @Override
    public Review createReview(Review newReview) {
        if (newReview.getScore() < 1 || newReview.getScore() > 5) {
            throw new IllegalArgumentException();
        }

        newReview.setStatus(Review.ReviewStatus.ACTIVE);

        reviewRepository.create(newReview);
        summaryService.addReview(newReview);

        return newReview;
    }

    @Override
    public void cancelReview(UUID orderId) {
        Review review = reviewRepository.findByOrderId(orderId);
        review.setStatus(Review.ReviewStatus.CANCELLED);
        summaryService.cancelReview(review);
        reviewRepository.update(review);
    }
}
