package webservice.converters;

import domain.user.Password;
import domain.user.UserName;
import webservice.contracts.parameters.TmsAccountParameter;

public class TmsAccountConverter {
    private TmsAccountConverter(){}

    public static domain.user.TmsAccount convert(TmsAccountParameter tmsAccount){
        return convert(null, tmsAccount);
    }

    public static domain.user.TmsAccount convert(Long id, TmsAccountParameter tmsAccount){
        return domain.user.TmsAccount.builder()
                .id(id)
                .userName(new UserName(tmsAccount.getUserName()))
                .password(new Password(tmsAccount.getPassword()))
                .build();
    }

    public static webservice.contracts.outputs.TmsAccount convert(domain.user.TmsAccount tmsAccount){
        return webservice.contracts.outputs.TmsAccount.builder()
                .id(tmsAccount.getId())
                .userName(tmsAccount.getUserName().toString())
                .password(tmsAccount.getPassword().toString())
                .build();
    }
}
