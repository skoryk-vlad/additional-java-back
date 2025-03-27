package org.example.repositories;

import org.example.models.NpmPackage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NpmPackageRepository extends JpaRepository<NpmPackage, Long> {
    Optional<NpmPackage> findByName(String name);
}
