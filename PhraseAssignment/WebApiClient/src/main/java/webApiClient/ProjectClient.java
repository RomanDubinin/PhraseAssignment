package webApiClient;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import webApiClient.contracts.common.PagedContent;
import webApiClient.contracts.project.Project;
import webApiClient.utilities.TokenProvider;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProjectClient {
    private final Logger logger = Logger.getLogger(String.valueOf(ProjectClient.class));
    private final HttpClient httpClient;
    private final TokenProvider tokenProvider;

    public ProjectClient() {
        httpClient = HttpClient.newBuilder().build();
        tokenProvider = new TokenProvider();
    }

    public PagedContent<Project> getProjects(Long userId, Integer pageSize, Integer pageNumber) throws Exception {
        var token = tokenProvider.getToken(userId);
        var uri = URI.create(String.format("https://cloud.memsource.com/web/api2/v1/projects?pageSize=%s&pageNumber=%s", pageSize, pageNumber));

        var request = HttpRequest.newBuilder()
                .uri(uri)
                .header("Authorization", "ApiToken " + token)
                .GET()
                .build();

        var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if(response.statusCode() != 200) {
            logger.log(Level.SEVERE, response.body());
            throw new Exception(response.body());
        }

        var json = response.body();
        var objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper.readValue(json, new TypeReference<>() {});
    }
}
