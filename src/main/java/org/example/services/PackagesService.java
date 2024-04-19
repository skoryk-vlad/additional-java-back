package org.example.services;

import org.example.models.NpmPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PackagesService {
    @Autowired
    private PackagesParserService packagesParserService;

    @Autowired
    private ExcelService excelService;

    List<NpmPackage> packages = new ArrayList<>();

    public List<NpmPackage> get() {
        return packages;
    }

    public NpmPackage getById(int id) {
        return packages.get(id - 1);
    }

    public NpmPackage add(String name) {
        NpmPackage npmPackage = packages.stream().filter(onePackage -> name.equals(onePackage.getName())).findAny().orElse(null);

        if (npmPackage == null) {
            npmPackage = packagesParserService.parseByName(name);
            npmPackage.setId(packages.size() + 1);

            packages.add(npmPackage);
        }

        return npmPackage;
    }

    public void excel(HttpServletResponse response) {
        try {
            excelService.export(response, packages);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
