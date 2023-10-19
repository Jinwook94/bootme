package com.bootme.course.service;

import com.bootme.common.exception.ConflictException;
import com.bootme.common.exception.ResourceNotFoundException;
import com.bootme.course.domain.Company;
import com.bootme.course.dto.CompanyRequest;
import com.bootme.course.dto.CompanyResponse;
import com.bootme.course.repository.CompanyRepository;
import com.bootme.course.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.bootme.common.exception.ErrorType.ALREADY_SAVED_COMPANY;
import static com.bootme.common.exception.ErrorType.NOT_FOUND_COMPANY;

@Service
@Transactional
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final CourseRepository courseRepository;

    public Company getCompanyById(Long id) {
        return companyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND_COMPANY, String.valueOf(id)));
    }

    public Company getCompanyByName(String name) {
        return companyRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND_COMPANY, name));
    }

    public Long addCompany(CompanyRequest companyRequest){
        validateDuplicate(companyRequest.getName());
        Company company = Company.builder()
                .name(companyRequest.getName())
                .serviceName(companyRequest.getServiceName())
                .url(companyRequest.getUrl())
                .serviceUrl(companyRequest.getServiceUrl())
                .logoUrl(companyRequest.getLogoUrl())
                .courses(new ArrayList<>())
                .build();
        Company savedCompany = companyRepository.save(company);
        return savedCompany.getId();
    }

    @Transactional(readOnly = true)
    public CompanyResponse findById(Long id) {
        Company foundCompany = getCompanyById(id);
        return CompanyResponse.of(foundCompany);
    }

    @Transactional(readOnly = true)
    public List<CompanyResponse> findAll() {
        List<Company> companyList = companyRepository.findAll();
        return companyList.stream()
                .map(CompanyResponse::of)
                .toList();
    }

    public void modifyCompany(Long id, CompanyRequest companyRequest){
        Company foundCompany = getCompanyById(id);
        foundCompany.modifyCompany(companyRequest);
    }

    public void deleteCompany(Long id){
        Company company = getCompanyById(id);
        courseRepository.deleteAll(company.getCourses());
        companyRepository.delete(company);
    }

    private void validateDuplicate(String name){
        boolean isExist = companyRepository.existsByName(name);
        if(isExist){
            throw new ConflictException(ALREADY_SAVED_COMPANY, name);
        }
    }

}
