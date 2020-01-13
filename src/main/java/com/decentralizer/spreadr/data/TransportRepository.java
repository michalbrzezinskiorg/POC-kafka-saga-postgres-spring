package com.decentralizer.spreadr.data;

import com.decentralizer.spreadr.data.entities.Transport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransportRepository extends JpaRepository<Transport, Long> {
    @Query("from Transport t where t.order.eventId = :eventId")
    Optional<Transport> findByOrderId(String eventId);
}
