package br.com.site2019.reviewtutorial.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Review implements Serializable {

    public enum ReviewStatus {
        ACTIVE,
        CANCELLED;
    }

    private UUID restaurantId;
    private UUID orderId;
    private Integer score;
    private ReviewStatus status;
}
