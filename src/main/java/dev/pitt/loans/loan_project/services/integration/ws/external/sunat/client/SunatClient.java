package dev.pitt.loans.loan_project.services.integration.ws.external.sunat.client;

import dev.pitt.loans.loan_project.services.integration.ws.external.sunat.model.SunatClientModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "sunat-client", url = "https://api.apis.net.pe/v2/sunat/")
public interface SunatClient {

    @GetMapping("/ruc/full")
    SunatClientModel getCompanySunat(@RequestParam("numero") String numero,
                                     @RequestHeader("Authorization") String authorization);

}
