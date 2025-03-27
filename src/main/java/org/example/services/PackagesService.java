package org.example.services;

import org.example.models.NpmPackage;
import org.example.repositories.NpmPackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class PackagesService {
    private final NpmPackageRepository repository;

    @Autowired
    private PackagesParserService packagesParserService;

    @Autowired
    private ExcelService excelService;

    public PackagesService(NpmPackageRepository repository) {
        this.repository = repository;
    }

    public List<NpmPackage> get() {
        return repository.findAll();
    }

    public Optional<NpmPackage> getById(Long id) {
        return repository.findById(id);
    }

    public Optional<NpmPackage> getByName(String name) {
        return repository.findByName(name);
    }

    public NpmPackage add(String name) {
        Optional<NpmPackage> existingPackage = getByName(name);

        if (existingPackage.isPresent()) {
            return existingPackage.get();
        } else {
            NpmPackage newPackage = packagesParserService.parseByName(name);
            if (newPackage != null) {
                return repository.save(newPackage);
            } else {
                throw new RuntimeException("Failed to parse package with name: " + name);
            }
        }
    }

    public void excel(HttpServletResponse response) {
        List<NpmPackage> packages = repository.findAll();

        try {
            excelService.export(response, packages);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
