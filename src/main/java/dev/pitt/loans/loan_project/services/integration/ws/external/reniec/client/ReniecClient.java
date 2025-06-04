package dev.pitt.loans.loan_project.services.integration.ws.external.reniec.client;

import dev.pitt.loans.loan_project.services.integration.ws.external.reniec.model.ReniecClientModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "reniec-client", url = "https://api.apis.net.pe/v2/reniec/")
public interface ReniecClient {

    @GetMapping("/dni")
    ReniecClientModel getPersonReniec(@RequestParam("numero") String numero,
                                      @RequestHeader("Authorization") String authorization);

}
