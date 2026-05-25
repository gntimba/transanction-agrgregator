package gti.aggregatorservice.repo;

import gti.aggregatorservice.dto.TransactionEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TransactionRepo extends JpaRepository<TransactionEvent, String> {
}
