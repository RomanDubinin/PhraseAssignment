package repositories;

import domain.user.TmsAccount;
import org.springframework.data.repository.CrudRepository;

public interface TmsAccountRepository extends CrudRepository<TmsAccount, Long> {
}
