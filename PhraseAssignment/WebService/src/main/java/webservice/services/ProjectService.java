package webservice.services;

import org.springframework.stereotype.Service;
import webApiClient.ProjectClient;
import webservice.contracts.outputs.Project;
import webservice.converters.ProjectConverter;
import webservice.infrastructure.TokenProvider;

import java.util.List;

@Service
public class ProjectService {
    private final ProjectClient projectClient;
    private final TokenProvider tokenProvider;

    public ProjectService(TokenProvider tokenProvider){
        this.tokenProvider = tokenProvider;
        projectClient = new ProjectClient();
    }

    public List<Project> getProjects(Integer pageSize, Integer pageNumber) throws Exception {
        var pagedContent = projectClient.getProjects(pageSize, pageNumber, tokenProvider.getToken());
        return pagedContent.getContent().stream().map(ProjectConverter::convert).toList();
    }
}
