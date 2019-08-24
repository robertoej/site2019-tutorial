package br.com.site2019.reviewtutorial.api;

import br.com.site2019.reviewtutorial.core.ReviewService;
import br.com.site2019.reviewtutorial.model.Review;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static org.springframework.http.ResponseEntity.badRequest;

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

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid entry")
    public ResponseEntity handleIllegalArgumentException() {
        return badRequest().build();
    }
}
