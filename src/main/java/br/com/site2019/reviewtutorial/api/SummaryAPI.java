package br.com.site2019.reviewtutorial.api;

import br.com.site2019.reviewtutorial.core.SummaryService;
import br.com.site2019.reviewtutorial.model.Summary;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("summary")
public class SummaryAPI {

    private SummaryService summaryService;

    public SummaryAPI(SummaryService summaryService) {

        this.summaryService = summaryService;
    }

    @GetMapping("{restaurantId}")
    public Summary getByRestaurantId(@PathVariable("restaurantId") UUID restaurantId) {

        return summaryService.getSummaryByRestaurantId(restaurantId);
    }
}
