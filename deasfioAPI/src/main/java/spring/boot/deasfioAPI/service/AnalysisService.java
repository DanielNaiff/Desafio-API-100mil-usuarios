package spring.boot.deasfioAPI.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.boot.deasfioAPI.model.TeamInsight;
import spring.boot.deasfioAPI.model.User;
import spring.boot.deasfioAPI.repository.UserRepository;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnalysisService {
    private final UserRepository userRepository;

    public List<User> superUsers(){
        return userRepository.findAll().parallelStream().filter(user -> user.getScore()>=900 && user.isActive()).toList();
    }

    public List<Map<String, Object>> topCountrys(){
        return superUsers()
                .parallelStream()
                .collect(Collectors.groupingBy(User::getCountry, Collectors.counting()))
                .entrySet().stream()
                .sorted(Comparator.comparingLong(Map.Entry<String, Long>::getValue).reversed())
                .map(e->Map.<String, Object>of("country", e.getKey(), "total", e.getValue()))
                .toList();
    }

    public List<TeamInsight> teamInsights(){
        return userRepository.findAll().parallelStream()
                .collect(Collectors.groupingBy(u -> u.getTeam().getName()))
                .entrySet().stream()
                .map(e ->{
                    var users = e.getValue();
                    int members = users.size();
                    int leaders = (int) users.stream().filter(u -> u.getTeam().isLeader()).count();
                    int completed = users.stream().flatMap(u -> u.getTeam().getProject().stream())
                            .mapToInt(p -> p.isCompleted() ? 1: 0).sum();
                    int active = (int)users.stream().filter(User::isActive).count();
                    double percente = members == 0 ? 0 : active * 100.0/members;

                    return new TeamInsight(e.getKey(), members, leaders, completed, percente);
                }).toList();
    }

    public Map<LocalDate, Long> logPerDay(int min){
        return userRepository.findAll().parallelStream()
                .flatMap(u -> u.getLogs().stream())
                .filter(l -> "login".equalsIgnoreCase(l.getAction()))
                .collect(Collectors.groupingBy(AcessLog::getDate, Collectors.counting()))
                .entrySet().stream()
                .filter(e -> e.getValue() >= min)
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> a, LinkedHashMap::new));
    }
}
