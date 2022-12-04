package webservice.services;

import org.springframework.stereotype.Service;
import webApiClient.ProjectClient;
import webservice.contracts.outputs.Project;
import webservice.converters.ProjectConverter;

import java.util.Arrays;

@Service
public class ProjectService {
    private ProjectClient projectClient;

    public ProjectService(){
        projectClient = new ProjectClient();
    }

    public Project[] getProjects(Long userId, Integer pageSize, Integer pageNumber) throws Exception {
        var pagedContent = projectClient.getProjects(userId, pageSize, pageNumber);
        return Arrays.stream(pagedContent.getContent()).map(ProjectConverter::convert).toArray(Project[]::new);
    }
}
