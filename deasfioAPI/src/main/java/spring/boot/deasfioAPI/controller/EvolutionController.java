package spring.boot.deasfioAPI.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class EvolutionController {
    private final WebClient client = WebClient.create("http://localhost:8080");

    @GetMapping("/evaluation")
    public Map<String, Object> evaluate(){

        List<String> endpoints = List.of("/superusers", "top-countries","/team-insights","/active-users-per-day");

        Map<String, Object> results = new LinkedHashMap<>();
        endpoints.forEach(ep -> {
            long start = System.currentTimeMillis();
                    ResponseEntity<String> resp = client.get().uri(ep).retrieve().toEntity(String.class).block();
                    long time = System.currentTimeMillis() - start;
                    results.put(ep, Map.of("status", resp==null ? 0:resp.getStatusCode().value(),
                            "time_ms", time,
                            "valid_response", true));
        }
        );
        return Map.of("tested_endpoints", results);
    }
}
