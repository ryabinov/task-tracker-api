package jopa.luxoft.tasktrackerapi.api.factories;

import jopa.luxoft.tasktrackerapi.api.dto.ProjectDto;
import jopa.luxoft.tasktrackerapi.store.entities.ProjectEntity;
import org.springframework.stereotype.Component;

@Component
public class ProjectDtoFactory {

    public ProjectDto makeProjectDto(ProjectEntity entity){
        return ProjectDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .createAt(entity.getCreateAt())
                .build();
    }
}
