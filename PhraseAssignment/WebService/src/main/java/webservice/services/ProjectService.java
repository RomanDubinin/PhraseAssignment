package webservice.services;

import org.springframework.stereotype.Service;
import webApiClient.ProjectClient;
import webservice.contracts.outputs.Project;
import webservice.converters.ProjectConverter;

import java.util.List;

@Service
public class ProjectService {
    private final ProjectClient projectClient;

    public ProjectService(){
        projectClient = new ProjectClient();
    }

    public List<Project> getProjects(Long userId, Integer pageSize, Integer pageNumber) throws Exception {
        var pagedContent = projectClient.getProjects(userId, pageSize, pageNumber);
        return pagedContent.getContent().stream().map(ProjectConverter::convert).toList();
    }
}
