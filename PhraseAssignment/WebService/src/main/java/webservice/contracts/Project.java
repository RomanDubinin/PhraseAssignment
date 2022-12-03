package webservice.contracts;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Project {
    private String name;
    private String status;
    private String sourceLanguage;
    private String[] targetLanguages;
}
