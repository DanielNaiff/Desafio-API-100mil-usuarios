package spring.boot.deasfioAPI.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TeamInsight {
    private String team;
    private int totalMembers;
    private int leaders;
    private int completedProjects;
    private double activePercentage;
}
