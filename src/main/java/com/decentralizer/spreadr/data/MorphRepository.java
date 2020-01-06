package com.decentralizer.spreadr.data;

import com.decentralizer.spreadr.data.entities.Morph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MorphRepository extends JpaRepository<Morph, Long> {
}
