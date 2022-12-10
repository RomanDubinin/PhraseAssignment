package webservice.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import repositories.TmsAccountRepository;
import webservice.contracts.outputs.TmsAccount;
import webservice.contracts.parameters.TmsAccountParameter;
import webservice.converters.TmsAccountConverter;

import java.util.NoSuchElementException;

@Service
public class TmsAccountService {
    private final TmsAccountRepository tmsAccountRepository;

    public TmsAccountService(TmsAccountRepository tmsAccountRepository) {
        this.tmsAccountRepository = tmsAccountRepository;
    }

    public TmsAccount get(Long id) {
        var account = tmsAccountRepository.findById(id).map(TmsAccountConverter::convert).orElse(null);
        if (account == null)
            throw new NoSuchElementException(String.format("TMS account with id %s does not exists", id));
        return account;
    }

    public TmsAccount save(TmsAccountParameter account) {
        var domainEntity = TmsAccountConverter.convert(account);
        return TmsAccountConverter.convert(tmsAccountRepository.save(domainEntity));
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public TmsAccount edit(Long id, TmsAccountParameter account) {
        if (!tmsAccountRepository.existsById(id))
            throw new IllegalArgumentException(String.format("TMS account with id %s does not exists", id));

        var domainEntity = TmsAccountConverter.convert(id, account);
        return TmsAccountConverter.convert(tmsAccountRepository.save(domainEntity));
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void delete(Long id) {
        if (!tmsAccountRepository.existsById(id))
            throw new NoSuchElementException(String.format("TMS account with id %s does not exists", id));

        tmsAccountRepository.deleteById(id);
    }
}
