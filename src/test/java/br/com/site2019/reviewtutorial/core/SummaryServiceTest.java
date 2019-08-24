package br.com.site2019.reviewtutorial.core;

import br.com.site2019.reviewtutorial.model.Review;
import br.com.site2019.reviewtutorial.model.Summary;
import br.com.site2019.reviewtutorial.repository.SummaryRepository;
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
public class SummaryServiceTest {

    @Autowired
    private SummaryService summaryService;

    @MockBean
    private SummaryRepository summaryRepository;

    @Captor
    private ArgumentCaptor<Summary> summaryArgumentCaptor;

    @Test
    public void testAddReview() {
        Review.ReviewStatus status = Review.ReviewStatus.ACTIVE;
        UUID restaurantId = UUID.randomUUID();
        UUID orderId = UUID.randomUUID();
        Integer score = 5;

        Summary currentSummary = Summary.builder()
                .restaurantId(restaurantId)
                .reviewCount(2l)
                .totalScore(10l)
                .build();

        Review review = Review.builder()
                .restaurantId(restaurantId)
                .orderId(orderId)
                .score(score)
                .status(status)
                .build();

        Mockito.when(summaryRepository.findByRestaurantId(restaurantId)).thenReturn(currentSummary);

        summaryService.addReview(review);

        Mockito.verify(summaryRepository).update(summaryArgumentCaptor.capture());

        Summary capturedSummary = summaryArgumentCaptor.getValue();

        Assert.assertEquals(Long.valueOf(3l), capturedSummary.getReviewCount());
        Assert.assertEquals(Long.valueOf(15l), capturedSummary.getTotalScore());
        Assert.assertEquals(restaurantId, capturedSummary.getRestaurantId());
    }

    @Test
    public void testCancelReview() {
        Review.ReviewStatus status = Review.ReviewStatus.ACTIVE;
        UUID restaurantId = UUID.randomUUID();
        UUID orderId = UUID.randomUUID();
        Integer score = 5;

        Summary currentSummary = Summary.builder()
                .restaurantId(restaurantId)
                .reviewCount(2l)
                .totalScore(10l)
                .build();

        Review review = Review.builder()
                .restaurantId(restaurantId)
                .orderId(orderId)
                .score(score)
                .status(status)
                .build();

        Mockito.when(summaryRepository.findByRestaurantId(restaurantId)).thenReturn(currentSummary);

        summaryService.cancelReview(review);

        Mockito.verify(summaryRepository).update(summaryArgumentCaptor.capture());

        Summary capturedSummary = summaryArgumentCaptor.getValue();

        Assert.assertEquals(Long.valueOf(1l), capturedSummary.getReviewCount());
        Assert.assertEquals(Long.valueOf(5l), capturedSummary.getTotalScore());
        Assert.assertEquals(restaurantId, capturedSummary.getRestaurantId());
    }
}