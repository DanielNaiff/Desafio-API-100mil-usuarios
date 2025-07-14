package spring.boot.deasfioAPI.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Project {
    private String name;
    private boolean completed;
}
