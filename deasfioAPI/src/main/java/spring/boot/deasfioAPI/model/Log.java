package spring.boot.deasfioAPI.model;

import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Log {
    private LocalDate date;
    private String action;
}
