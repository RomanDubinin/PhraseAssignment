package webservice.contracts.parameters;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TmsAccountParameter {
    private String userName;
    private String password;
}
