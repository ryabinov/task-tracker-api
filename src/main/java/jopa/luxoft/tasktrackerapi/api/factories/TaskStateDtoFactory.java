package jopa.luxoft.tasktrackerapi.api.factories;

import jopa.luxoft.tasktrackerapi.api.dto.TaskStateDto;
import jopa.luxoft.tasktrackerapi.store.entities.TaskStateEntity;
import org.springframework.stereotype.Component;

@Component
public class TaskStateDtoFactory {

    public TaskStateDto makeTaskStateDto(TaskStateEntity entity){
        return TaskStateDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .createAt(entity.getCreateAt())
                .ordinal(entity.getOrdinal())
                .build();
    }
}
