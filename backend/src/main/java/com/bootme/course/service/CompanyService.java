package com.bootme.course.service;

import com.bootme.course.domain.Company;
import com.bootme.course.dto.CompanyRequest;
import com.bootme.course.dto.CompanyResponse;
import com.bootme.course.exception.CompanyNotFoundException;
import com.bootme.course.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.bootme.common.exception.ErrorType.NOT_FOUND_COMPANY;

@Service
@Transactional
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;

    public Long addCompany(CompanyRequest companyRequest){
        Company company = Company.builder()
                .url(companyRequest.getUrl())
                .name(companyRequest.getName())
                .courses(new ArrayList<>())
                .build();
        Company savedCompany = companyRepository.save(company);
        return savedCompany.getId();
    }

    @Transactional(readOnly = true)
    public CompanyResponse findById(Long id) {
        Company foundCompany = companyRepository.findById(id)
                .orElseThrow(() -> new CompanyNotFoundException(NOT_FOUND_COMPANY));
        return CompanyResponse.of(foundCompany);
    }

    @Transactional(readOnly = true)
    public List<CompanyResponse> findAll() {
        List<Company> companyList = companyRepository.findAll();
        return companyList.stream().map(CompanyResponse::of).collect(Collectors.toList());
    }

    public void modifyCompany(Long companyId, CompanyRequest companyRequest){
        Company foundCompany = companyRepository.findById(companyId)
                                .orElseThrow(() -> new CompanyNotFoundException(NOT_FOUND_COMPANY));
        foundCompany.modifyCompany(companyRequest);
    }

    public void deleteCompany(Long companyId){
        companyRepository.deleteById(companyId);
    }

}
