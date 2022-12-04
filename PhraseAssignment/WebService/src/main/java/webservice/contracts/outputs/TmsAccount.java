package webservice.contracts.outputs;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TmsAccount {
    private Long id;
    private String userName;
    private String password;
}
