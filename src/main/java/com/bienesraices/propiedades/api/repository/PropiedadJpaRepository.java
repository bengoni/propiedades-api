package com.bienesraices.propiedades.api.repository;

import com.bienesraices.propiedades.api.model.Propiedad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropiedadJpaRepository extends JpaRepository<Propiedad, Long> {
}
