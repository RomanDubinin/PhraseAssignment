package webservice.controllers;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import webservice.contracts.outputs.Project;
import webservice.services.ProjectService;

import java.util.List;

@RestController
@RequestMapping("/project")
public class ProjectController {
    private final ProjectService projectService;

    private ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping(value = "/allProjects")
    public List<Project> getProjects(
            @RequestParam Long userId,
            @RequestParam Integer pageSize,
            @RequestParam Integer pageNumber)
            throws Exception {
        if (pageSize <= 0 || pageSize > 50)
            throw new IllegalArgumentException("Page size should be more than 0 and less than 50");

        if (pageNumber < 0)
            throw new IllegalArgumentException("Page number should be more or equal than 0");

        return projectService.getProjects(userId, pageSize, pageNumber);
    }
}