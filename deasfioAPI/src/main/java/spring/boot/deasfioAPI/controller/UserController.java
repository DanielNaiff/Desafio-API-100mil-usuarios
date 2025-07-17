package spring.boot.deasfioAPI.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.boot.deasfioAPI.model.User;
import spring.boot.deasfioAPI.repository.UserRepository;
import spring.boot.deasfioAPI.service.AnalysisService;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final AnalysisService analysisService;

    private <T> Map<String, Object> timed(Supplier<T> supplier){
        long start = System.currentTimeMillis();
        T data = supplier.get();
        long exec = System.currentTimeMillis() - start;
        return Map.of("timestamp", Instant.now(),
                "execution_time_ms", exec,
                "data", data);
    }
    @PostMapping("/users")
    public Map<String, Object> load(@RequestBody List<User> users){

        return timed(() -> {
            userRepository.saveAll(users);
            return Map.of(
                    "mensagem", "Arquivo recebido com sucesso",
                    "total_usuario", userRepository.findAll().size()
            );
                }

        );

    }

    @GetMapping("/superusers")
    public  Map<String, Object>  superUsers(){
        return timed(analysisService::superUsers);
    }

    @GetMapping("/top-countries")
    public  Map<String, Object>  topCountries(){
        return timed(analysisService::topCountrys);
    }

    @GetMapping("/team-insights")
    public  Map<String, Object>  teamInsight(){
        return timed(analysisService::teamInsights);
    }

    @GetMapping("/active-users-per-day")
    public Map<String, Object> logins(@RequestParam(defaultValue = "0") int min){
        return timed(() -> analysisService.logPerDay(min));
    }

}
