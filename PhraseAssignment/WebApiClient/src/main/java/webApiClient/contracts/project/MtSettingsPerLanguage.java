package webApiClient.contracts.project;

import webApiClient.contracts.common.MachineTranslateSettings;
import lombok.Getter;

@Getter
public class MtSettingsPerLanguage {
    private String targetLang;
    private MachineTranslateSettings machineTranslateSettings;
}
