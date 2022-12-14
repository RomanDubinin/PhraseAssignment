package webApiClient.contracts.project;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import webApiClient.contracts.common.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class Project {
    private String id;
    private String uid;
    private int internalId;
    private String name;
    private String userRole;
    private Progress progress;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    private LocalDateTime dateCreated;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    private LocalDateTime dateDue;
    private List<Reference> references;
    private AnalyseSettings analyseSettings;
    private AccessSettings accessSettings;
    private FinancialSettings financialSettings;
    private QualityAssuranceSettings qualityAssuranceSettings;
    private List<MtSettingsPerLanguage> mtSettingsPerLanguageList;
    private List<WorkflowStep> workflowSteps;
    private CostCenter costCenter;
    private String sourceLang;
    private List<String> targetLangs;
    private Domain domain;
    private Domain subDomain;
    private String businessUnit;
    private String purchaseOrder;
    private User owner;
    private User createdBy;
    private User client;
    private String status;
    private String note;
    private Boolean isPublishedOnJobBoard;
    private Boolean archived;
    private Boolean shared;
}
