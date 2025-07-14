package spring.boot.deasfioAPI.model;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Team {
    private String name;
    private boolean leader;
    private List<Project> project;

}
