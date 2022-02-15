package jopa.luxoft.tasktrackerapi.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NonNull;
import lombok.*;
import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {


    private Long id;


    private String name;


    private String description;


    @JsonProperty("created_at")
    Instant createAt;
}
