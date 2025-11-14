package org.delcom.app.repositories;

import org.delcom.app.entities.CashFlow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
// UBAH TIPE ID MENJADI UUID
public interface CashFlowRepository extends JpaRepository<CashFlow, UUID> {
    
    // TAMBAHKAN METODE YANG DICARI OLEH TES
    @Query("SELECT c FROM CashFlow c WHERE lower(c.label) LIKE lower(concat('%', :keyword, '%')) OR lower(c.description) LIKE lower(concat('%', :keyword, '%'))")
    List<CashFlow> findByKeyword(@Param("keyword") String keyword);

    @Query("SELECT DISTINCT c.label FROM CashFlow c")
    List<String> findDistinctLabels();
}