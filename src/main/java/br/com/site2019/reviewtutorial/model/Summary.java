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
public class Summary implements Serializable {

    private UUID restaurantId;
    private Long totalScore;
    private Long reviewCount;
}
