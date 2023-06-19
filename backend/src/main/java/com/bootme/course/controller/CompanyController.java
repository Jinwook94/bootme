package com.bootme.course.controller;

import com.bootme.course.dto.CompanyRequest;
import com.bootme.course.dto.CompanyResponse;
import com.bootme.course.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/companies")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @PostMapping
    public ResponseEntity<CompanyResponse> addCompany(@Valid CompanyRequest companyRequest){
        Long companyId = companyService.addCompany(companyRequest);
        CompanyResponse companyResponse = companyService.findById(companyId);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/companies/" + companyId);
        return new ResponseEntity<>(companyResponse, headers, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyResponse> findCompanyById(@PathVariable Long id) {
        CompanyResponse companyResponse = companyService.findById(id);
        return ResponseEntity.ok(companyResponse);
    }

    @GetMapping
    public ResponseEntity<List<CompanyResponse>> findAllCompanies(){
        List<CompanyResponse> companyResponses = companyService.findAll();
        int size = companyResponses.size();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Access-Control-Expose-Headers", "X-Total-Count");
        headers.add("X-Total-Count", String.valueOf(size));
        headers.add("Content-Range", "companies 0-"+size+"/"+size);

        return ResponseEntity.ok().headers(headers).body(companyResponses);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> modifyCompany(@PathVariable Long id, @Valid CompanyRequest companyRequest){
        companyService.modifyCompany(id, companyRequest);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable Long id){
        companyService.deleteCompany(id);
        return ResponseEntity.noContent().build();
    }
}
