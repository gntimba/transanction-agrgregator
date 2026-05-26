package gti.aggregatorservice.repo;

import gti.aggregatorservice.dto.DailyTxnCount;
import gti.aggregatorservice.dto.MonthlyTotal;
import gti.aggregatorservice.dto.TopMerchant;
import gti.aggregatorservice.dto.TransactionEvent;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


@Repository
public interface TransactionRepo extends JpaRepository<TransactionEvent, String> {

    List<TransactionEvent> findByCategory(String Category);

    List<TransactionEvent> findByMerchantId(Integer merchantId);

    List<TransactionEvent> findByTransactionDateBetween(Date transactionDateStart, Date transactionDateEnd);


    @Query("""
    SELECT SUM(t.amount)
    FROM TransactionEvent t
    WHERE t.accountId = :accountId
""")
    BigDecimal getTotalSpend(String accountId);

    @Query("""
    SELECT SUM(t.amount)
    FROM TransactionEvent t
    WHERE t.accountId = :accountId
    AND t.transactionDate BETWEEN :startDate AND :endDate
""")
    BigDecimal getTotalSpendBetweenDates(
            String accountId,
            Date startDate,
            Date endDate
    );
    @Query("""
    SELECT t.category, SUM(t.amount)
    FROM TransactionEvent t
    WHERE t.accountId = :accountId
    GROUP BY t.category
""")
    List<Object[]> getSpendPerCategory(String accountId);



    @Query("""
            SELECT FUNCTION('to_char', t.transactionDate, 'FMMonth') AS month,
                   SUM(t.amount) as total
                   FROM TransactionEvent t
                   GROUP BY FUNCTION('to_char', t.transactionDate, 'FMMonth'), EXTRACT(MONTH FROM t.transactionDate)
                   ORDER BY EXTRACT(MONTH FROM t.transactionDate)
            """)
    List<MonthlyTotal> getMonthlySpend();

    @Query("""
    SELECT t.merchant as merchant, SUM(t.amount) as total
    FROM TransactionEvent t
    GROUP BY t.merchant
    ORDER BY SUM(t.amount) DESC
""")
    List<TopMerchant> getTopMerchants();


    @Query("""
    SELECT DATE(t.transactionDate) as transactionDate,
           COUNT(t) as total
    FROM TransactionEvent t
    GROUP BY DATE(t.transactionDate)
""")
    List<DailyTxnCount> getDailyTransactionCount();
}
