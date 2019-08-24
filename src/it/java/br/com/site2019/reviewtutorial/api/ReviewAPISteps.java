package br.com.site2019.reviewtutorial.api;

import br.com.site2019.reviewtutorial.model.Review;
import br.com.site2019.reviewtutorial.model.Summary;
import br.com.site2019.reviewtutorial.repository.ReviewRepository;
import br.com.site2019.reviewtutorial.repository.SummaryRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import lombok.extern.java.Log;
import org.junit.Assert;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.UUID;

@Log
@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class ReviewAPISteps {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private SummaryRepository summaryRepository;

    private Review newReview;

    @Before
    @Given("there are no reviews created")
    public void there_are_no_reviews_created() {
        Assert.assertTrue(reviewRepository.listAll().isEmpty());
    }

    @When("a review is created")
    public void a_review_is_created() throws Exception {
        newReview = Review.builder()
                .score(5)
                .orderId(UUID.randomUUID())
                .restaurantId(UUID.randomUUID()).build();

        mockMvc.perform(MockMvcRequestBuilders.post("/review/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(newReview)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Then("summary is updated")
    public void summary_is_updated() {
        Summary summary = summaryRepository.findByRestaurantId(newReview.getRestaurantId());

        Assert.assertEquals(Long.valueOf(5), summary.getTotalScore());
        Assert.assertEquals(Long.valueOf(1), summary.getReviewCount());
        Assert.assertEquals(newReview.getRestaurantId(), summary.getRestaurantId());
    }
}
