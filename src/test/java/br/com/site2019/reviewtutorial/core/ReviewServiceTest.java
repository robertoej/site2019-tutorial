package br.com.site2019.reviewtutorial.core;

import br.com.site2019.reviewtutorial.model.Review;
import br.com.site2019.reviewtutorial.repository.ReviewRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ReviewServiceTest {

    @Autowired
    private ReviewService reviewService;

    @MockBean
    private ReviewRepository reviewRepository;

    @MockBean
    private SummaryService summaryService;

    @Captor
    private ArgumentCaptor<Review> reviewArgumentCaptor;

    @Test
    public void testCreateReview() {
        Review.ReviewStatus status = Review.ReviewStatus.ACTIVE;
        UUID restaurantId = UUID.randomUUID();
        UUID orderId = UUID.randomUUID();
        Integer score = 5;

        Review expectedReview = Review.builder()
                .restaurantId(restaurantId)
                .orderId(orderId)
                .score(score)
                .status(status)
                .build();

        final Review returnedReview = reviewService.createReview(expectedReview);

        Mockito.verify(reviewRepository).create(expectedReview);
        Mockito.verify(summaryService).addReview(expectedReview);

        Assert.assertEquals(score, returnedReview.getScore());
        Assert.assertEquals(restaurantId, returnedReview.getRestaurantId());
        Assert.assertEquals(orderId, returnedReview.getOrderId());
        Assert.assertEquals(Review.ReviewStatus.ACTIVE, returnedReview.getStatus());
    }

    @Test
    public void testCancelReview() {
        Review.ReviewStatus status = Review.ReviewStatus.ACTIVE;
        UUID restaurantId = UUID.randomUUID();
        UUID orderId = UUID.randomUUID();
        Integer score = 5;

        Review expectedReview = Review.builder()
                .restaurantId(restaurantId)
                .orderId(orderId)
                .score(score)
                .status(status)
                .build();

        Mockito.when(reviewRepository.findByOrderId(orderId)).thenReturn(expectedReview);

        reviewService.cancelReview(orderId);

        Mockito.verify(reviewRepository).update(reviewArgumentCaptor.capture());
        Mockito.verify(summaryService).cancelReview(reviewArgumentCaptor.capture());

        Review capturedReview1 = reviewArgumentCaptor.getAllValues().get(0);
        Review capturedReview2 = reviewArgumentCaptor.getAllValues().get(1);

        Assert.assertEquals(score, capturedReview1.getScore());
        Assert.assertEquals(restaurantId, capturedReview1.getRestaurantId());
        Assert.assertEquals(orderId, capturedReview1.getOrderId());
        Assert.assertEquals(Review.ReviewStatus.CANCELLED, capturedReview1.getStatus());

        Assert.assertEquals(score, capturedReview2.getScore());
        Assert.assertEquals(restaurantId, capturedReview2.getRestaurantId());
        Assert.assertEquals(orderId, capturedReview2.getOrderId());
        Assert.assertEquals(Review.ReviewStatus.CANCELLED, capturedReview2.getStatus());
    }
}