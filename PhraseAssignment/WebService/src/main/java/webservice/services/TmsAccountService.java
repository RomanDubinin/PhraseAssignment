package webservice.services;

import org.springframework.stereotype.Service;
import repositories.TmsAccountRepository;
import webservice.contracts.outputs.TmsAccount;
import webservice.contracts.parameters.TmsAccountParameter;
import webservice.converters.TmsAccountConverter;

@Service
public class TmsAccountService {
    private final TmsAccountRepository tmsAccountRepository;

    public TmsAccountService(TmsAccountRepository tmsAccountRepository) {
        this.tmsAccountRepository = tmsAccountRepository;
    }

    public TmsAccount get(Long id) {
        return tmsAccountRepository.findById(id).map(TmsAccountConverter::convert).orElse(null);
    }

    public TmsAccount save(TmsAccountParameter account) {
        var domainEntity = TmsAccountConverter.convert(account);
        return TmsAccountConverter.convert(tmsAccountRepository.save(domainEntity));
    }

    public TmsAccount edit(Long id, TmsAccountParameter account) {
        if (!tmsAccountRepository.existsById(id))
            throw new IllegalArgumentException(String.format("TMS account with id %s does not exists", id));

        var domainEntity = TmsAccountConverter.convert(id, account);
        return TmsAccountConverter.convert(tmsAccountRepository.save(domainEntity));
    }

    public void delete(Long id) {
        tmsAccountRepository.deleteById(id);
    }
}
