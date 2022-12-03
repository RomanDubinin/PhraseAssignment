package webservice.converters;

import webservice.contracts.Project;

public class ProjectConverter {
    private ProjectConverter() {
    }

    public static Project convert(webApiClient.contracts.project.Project project) {
        return Project
                .builder()
                .name(project.getName())
                .status(project.getStatus())
                .sourceLanguage(project.getSourceLang())
                .targetLanguages(project.getTargetLangs())
                .build();
    }
}
