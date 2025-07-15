package spring.boot.deasfioAPI.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.boot.deasfioAPI.model.User;
import spring.boot.deasfioAPI.repository.UserRepository;
import spring.boot.deasfioAPI.service.AnalysisService;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final AnalysisService analysisService;

    @PostMapping("/users")
    public ResponseEntity<?> load(@RequestBody List<User> users){
        userRepository.saveAll(users);

        return ResponseEntity.ok(Map.of("message", "Arquivo recebido com sucesso", "user_count", userRepository.findAll().size()));
    }

    @GetMapping("/superusers")
    public ResponseEntity<?> superUsers(){
        return ResponseEntity.ok(analysisService.superUsers());
    }

    @GetMapping("/top-countries")
    public ResponseEntity<?> topCountries(){
        return ResponseEntity.ok(analysisService.topCountrys());
    }

    @GetMapping("/team-insights")
    public ResponseEntity<?> teamInsight(){
        return ok(analysisService.teamInsights());
    }

    @GetMapping("/active-users-per-day")
    public ResponseEntity<?> logins(@RequestParam(defaultValue = "0") int min){
        return ok(analysisService.logPerDay(min));
    }

    public ResponseEntity<?> ok(Object dados){
        return ResponseEntity.ok(Map.of("data", dados));
    }
}
