package com.decentralizer.spreadr.data;

import com.decentralizer.spreadr.data.entities.Client;
import com.decentralizer.spreadr.data.entities.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
    @Query("SELECT w from Warehouse w join fetch Client c where c = :client")
    Optional<Warehouse> findByClient(Client client);
}
