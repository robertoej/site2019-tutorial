package br.com.site2019.reviewtutorial.api;

import br.com.site2019.reviewtutorial.model.Review;
import br.com.site2019.reviewtutorial.model.Summary;
import br.com.site2019.reviewtutorial.repository.ReviewRepository;
import br.com.site2019.reviewtutorial.repository.SummaryRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import lombok.extern.java.Log;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static java.lang.Long.valueOf;
import static java.util.UUID.randomUUID;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    private MvcResult answer;

    @Given("there are no reviews created")
    public void there_are_no_reviews_created() {
        assertTrue(reviewRepository.listAll().isEmpty());
    }

    @When("a review is created")
    public void a_review_is_created() throws Exception {
        newReview = Review.builder()
                .score(5)
                .orderId(randomUUID())
                .restaurantId(randomUUID()).build();

        mockMvc.perform(post("/review/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(newReview)))
                .andExpect(status().isOk());
    }

    @Then("summary is updated")
    public void summary_is_updated() {
        Summary summary = summaryRepository.findByRestaurantId(newReview.getRestaurantId());

        assertEquals(valueOf(5), summary.getTotalScore());
        assertEquals(valueOf(1), summary.getReviewCount());
        assertEquals(newReview.getRestaurantId(), summary.getRestaurantId());
    }

    @When("an invalid review is created")
    public void an_invalid_review_is_created() throws Exception {
        newReview = Review.builder()
                .score(-1)
                .orderId(randomUUID())
                .restaurantId(randomUUID()).build();

        answer = mockMvc.perform(post("/review")
                .contentType(APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(newReview)))
                .andReturn();
    }

    @Then("no summary is updated")
    public void no_summary_is_updated() {
        assertNull(summaryRepository.findByRestaurantId(newReview.getRestaurantId()));
    }

    @Then("warn user")
    public void warn_user() {
        assertEquals(400, answer.getResponse().getStatus());
    }
}
