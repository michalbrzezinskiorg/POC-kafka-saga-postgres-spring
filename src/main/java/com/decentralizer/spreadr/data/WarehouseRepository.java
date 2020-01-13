package com.decentralizer.spreadr.data;

import com.decentralizer.spreadr.data.entities.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
    @Query("SELECT w FROM Warehouse w join fetch w.clients c WHERE c.id = :id")
    Optional<Warehouse> findByClient(Long id);
}
