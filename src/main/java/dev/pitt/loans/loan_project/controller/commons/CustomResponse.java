package dev.pitt.loans.loan_project.controller.commons;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomResponse {

    // Metadata
    @JsonProperty("metadata")
    private ResponseMetadata metadata;

    // Data
    @JsonProperty("data")
    private Optional data;

}