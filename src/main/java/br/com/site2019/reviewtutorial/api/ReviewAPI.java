package br.com.site2019.reviewtutorial.api;

import br.com.site2019.reviewtutorial.core.ReviewService;
import br.com.site2019.reviewtutorial.model.Review;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("review")
public class ReviewAPI {

    private ReviewService reviewService;

    public ReviewAPI(ReviewService reviewService) {

        this.reviewService = reviewService;
    }

    @PostMapping
    public Review createReview(@RequestBody Review review) {

        return reviewService.createReview(review);
    }

    @DeleteMapping("{orderId}")
    public void cancelReview(@PathVariable("orderId") UUID orderId) {

        reviewService.cancelReview(orderId);
    }
}
