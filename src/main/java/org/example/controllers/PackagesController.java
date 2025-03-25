package org.example.controllers;

import org.example.dto.AddPackageDto;
import org.example.models.NpmPackage;
import org.example.services.PackagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/packages")
public class PackagesController {

    @Autowired
    private PackagesService packagesService;

    @GetMapping
    public List<NpmPackage> get() {
        return packagesService.get();
    }

    @GetMapping("/{id}")
    public NpmPackage getById(@PathVariable("id") String id) {
        return packagesService.getById(Integer.parseInt(id));
    }

    @PostMapping
    public NpmPackage add(@RequestBody AddPackageDto addPackageDto) {
        return packagesService.add(addPackageDto.getName());
    }

    @GetMapping("/excel")
    public void exportToExcel(HttpServletResponse response) {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=packages_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        packagesService.excel(response);
    }
}
