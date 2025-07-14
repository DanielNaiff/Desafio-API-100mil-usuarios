package spring.boot.deasfioAPI.model;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {
    private UUID id;
    private String name;
    private int age;
    private int score;
    private boolean active;
    private String country;
    private Team team;
    private List<Log> logs;
}
