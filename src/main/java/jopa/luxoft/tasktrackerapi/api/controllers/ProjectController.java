package jopa.luxoft.tasktrackerapi.api.controllers;

import jopa.luxoft.tasktrackerapi.api.dto.AskDto;
import jopa.luxoft.tasktrackerapi.api.dto.ProjectDto;
import jopa.luxoft.tasktrackerapi.api.exceptions.BadRequestException;
import jopa.luxoft.tasktrackerapi.api.exceptions.NotFoundException;
import jopa.luxoft.tasktrackerapi.api.factories.ProjectDtoFactory;
import jopa.luxoft.tasktrackerapi.store.entities.ProjectEntity;
import jopa.luxoft.tasktrackerapi.store.repositories.ProjectRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional
@RestController
public class ProjectController {

    ProjectDtoFactory projectDtoFactory;

    ProjectRepository projectRepository;

    public static final String FETCH_PROJECT = "/api/projects";
    public static final String CREATE_PROJECT = "/api/projects";
    public static final String EDIT_PROJECT = "/api/projects/{projects.id}";
    public static final String DELETE_PROJECT = "/api/projects/{projects.id}";

    @GetMapping(FETCH_PROJECT)
    public List<ProjectDto> fetcheProject(
            @RequestParam(value = "prefix_name", required = false) Optional<String> optionalPrefixName) {

        optionalPrefixName = optionalPrefixName.filter(prefixName -> !prefixName.trim().isEmpty());

        Stream<ProjectEntity> projectStream = optionalPrefixName
                .map(projectRepository::streamAllByNameStartsWithIgnoreCase)
                .orElseGet(() -> projectRepository.streamAll());

        return projectStream
                .map(projectDtoFactory::makeProjectDto)
                .collect(Collectors.toList());
    }

    @PostMapping(CREATE_PROJECT)
    public ProjectDto createProject(@RequestParam String name) {

        if (name.trim().isEmpty()) {
            throw new BadRequestException("name can't be empty");
        }
        projectRepository.findByName(name)
                .ifPresent(project -> {
                    throw new BadRequestException(String.format("Project \"%s\" already exists.", name));
                });
        ProjectEntity project = projectRepository.saveAndFlush(
                ProjectEntity.builder()
                        .name(name)
                        .build()
        );
        return projectDtoFactory.makeProjectDto(project);
    }

    @PatchMapping(EDIT_PROJECT)
    public ProjectDto editProject(@PathVariable("project_id") Long projectId,
                                  @RequestParam String name) {
        if (name.trim().isEmpty()) {
            throw new BadRequestException("name can't be empty");
        }
        ProjectEntity project1 = getProjectOrThrowRxception(projectId);

        projectRepository
                .findByName(name)
                .filter(anotherProject -> !Objects.equals(anotherProject.getId(), projectId))
                .ifPresent(anotherProject -> {
                    throw new BadRequestException(String.format("Project \"%s\" already exists.", name));
                });
        project1.setName(name);
        project1 = projectRepository.saveAndFlush(project1);
        return projectDtoFactory.makeProjectDto(project1);
    }

    @DeleteMapping(DELETE_PROJECT)
    public AskDto deleteProject(@PathVariable("project_id") Long projectId) {
        ProjectEntity project = getProjectOrThrowRxception(projectId);
        projectRepository.deleteById(projectId);
        return AskDto.makeDefault(true);
    }

    private ProjectEntity getProjectOrThrowRxception(@PathVariable("project_id") Long projectId) {
        return projectRepository
                .findById(projectId)
                .orElseThrow(() -> new NotFoundException(String.format("Project \"%s\" doesn't exist", projectId)));
    }
}
