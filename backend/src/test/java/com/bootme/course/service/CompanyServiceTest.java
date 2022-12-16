package com.bootme.course.service;

import com.bootme.course.domain.Company;
import com.bootme.course.dto.CompanyResponse;
import com.bootme.course.exception.CompanyNotFoundException;
import com.bootme.course.repository.CompanyRepository;
import com.bootme.util.ServiceTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static com.bootme.common.exception.ErrorType.NOT_FOUND_COMPANY;
import static com.bootme.util.fixture.CourseFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("CompanyService 클래스의")
class CompanyServiceTest extends ServiceTest {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private CompanyRepository companyRepository;

    private Company company1;
    private Company company2;

    @BeforeEach
    public void setUp() {
        company1 = Company.builder()
                .name(VALID_COM_NAME_1)
                .serviceName(VALID_COM_SERVICE_NAME_1)
                .url(VALID_COM_URL_1)
                .serviceUrl(VALID_COM_SERVICE_URL_1)
                .logoUrl(VALID_COM_LOGO_URL_1)
                .courses(new ArrayList<>())
                .build();
        company2 = Company.builder()
                .name(VALID_COM_NAME_2)
                .serviceName(VALID_COM_SERVICE_NAME_2)
                .url(VALID_COM_URL_2)
                .serviceUrl(VALID_COM_SERVICE_URL_2)
                .logoUrl(VALID_COM_LOGO_URL_2)
                .courses(new ArrayList<>())
                .build();
    }

    @Test
    @DisplayName("addCompany()는 회사를 추가한다.")
    public void addCompany(){
        //given
        long count = companyRepository.count();

        //when
        Long id = companyService.addCompany(VALID_COMPANY_REQUEST_1);
        Company foundCompany = companyRepository.findById(id).orElseThrow();

        //then
        assertAll(
                () -> assertThat(companyRepository.count()).isEqualTo(count + 1),
                () -> assertThat(foundCompany.getUrl()).isEqualTo(VALID_COMPANY_REQUEST_1.getUrl()),
                () -> assertThat(foundCompany.getName()).isEqualTo(VALID_COMPANY_REQUEST_1.getName())
        );
    }

    @Test
    @DisplayName("findById()는 회사 정보를 반환한다.")
    public void findById(){
        //given
        Long id = companyRepository.save(company1).getId();

        //when
        CompanyResponse companyResponse = companyService.findById(id);

        //then
        assertAll(
                () -> assertThat(companyResponse.getId()).isEqualTo(company1.getId()),
                () -> assertThat(companyResponse.getName()).isEqualTo(company1.getName()),
                () -> assertThat(companyResponse.getServiceName()).isEqualTo(company1.getServiceName()),
                () -> assertThat(companyResponse.getUrl()).isEqualTo(company1.getUrl()),
                () -> assertThat(companyResponse.getServiceUrl()).isEqualTo(company1.getServiceUrl()),
                () -> assertThat(companyResponse.getLogoUrl()).isEqualTo(company1.getLogoUrl())
        );
    }

    @Test
    @DisplayName("findAll()은 모든 회사 정보를 반환한다.")
    public void findAll(){
        //given
        companyRepository.save(company1);
        companyRepository.save(company2);

        //when
        List<CompanyResponse> companyResponses = companyService.findAll();

        //then
        assertAll(
                () -> assertThat(companyResponses.size()).isEqualTo(2)
        );
    }

    @Test
    @DisplayName("modifyCompany()는 회사 정보를 변경한다.")
    public void modifyCompany() {
        //given
        Long id = companyRepository.save(company1).getId();

        //when
        companyService.modifyCompany(id, VALID_COMPANY_REQUEST_1);
        Company foundCompany = companyRepository.findById(id)
                .orElseThrow(() -> new CompanyNotFoundException(NOT_FOUND_COMPANY));
        //then
        assertAll(
                () -> assertThat(foundCompany.getName()).isEqualTo(VALID_COM_NAME_1),
                () -> assertThat(foundCompany.getServiceName()).isEqualTo(VALID_COM_SERVICE_NAME_1),
                () -> assertThat(foundCompany.getUrl()).isEqualTo(VALID_COM_URL_1),
                () -> assertThat(foundCompany.getServiceUrl()).isEqualTo(VALID_COM_SERVICE_URL_1),
                () -> assertThat(foundCompany.getLogoUrl()).isEqualTo(VALID_COM_LOGO_URL_1)
        );
    }

    @Test
    @DisplayName("deleteCompany()는 회사 정보를 삭제한다.")
    public void deleteCompany(){
        //given
        Long id = companyRepository.save(company1).getId();
        long count = companyRepository.count();

        //when
        companyService.deleteCompany(id);

        //then
        assertAll(
                () -> assertThat(companyRepository.findById(id).isEmpty()).isTrue(),
                () -> assertThat(companyRepository.count()).isEqualTo(count - 1)
        );
    }

}
