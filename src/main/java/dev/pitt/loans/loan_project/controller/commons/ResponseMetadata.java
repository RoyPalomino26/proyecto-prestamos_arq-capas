package dev.pitt.loans.loan_project.controller.commons;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseMetadata {
    @JsonProperty("message")
    private String message;

    @JsonProperty("httpCode")
    private String httpCode;

    @JsonProperty("uri")
    private String uri;

    @JsonProperty("dataTime")
    private String dataTime;
}
