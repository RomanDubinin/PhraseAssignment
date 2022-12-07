package webservice.contracts.outputs;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class Project {
    private String name;
    private String status;
    private String sourceLanguage;
    private List<String> targetLanguages;
}
