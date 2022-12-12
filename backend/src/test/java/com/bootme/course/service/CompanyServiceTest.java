package com.bootme.course.service;

import com.bootme.course.domain.Company;
import com.bootme.course.dto.CompanyRequest;
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
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("CompanyService 클래스의")
class CompanyServiceTest extends ServiceTest {

    @Autowired
    CompanyService companyService;

    @Autowired
    CompanyRepository companyRepository;

    private Company company1;
    private Company company2;

    @BeforeEach
    public void setUp() {
        company1 = Company.builder()
                .url("www.naver.com")
                .name("네이버")
                .courses(new ArrayList<>())
                .build();
        company2 = Company.builder()
                .url("www.kakao.com")
                .name("카카오")
                .courses(new ArrayList<>())
                .build();
    }

    @Test
    @DisplayName("addCompany()는 회사를 추가한다.")
    public void addCompany(){
        //given
        CompanyRequest companyRequest = CompanyRequest.builder()
                .url("www.naver.com")
                .name("네이버")
                .build();
        long count = companyRepository.count();

        //when
        Long id = companyService.addCompany(companyRequest);
        Company foundCompany = companyRepository.findById(id).orElseThrow();

        //then
        assertAll(
                () -> assertThat(companyRepository.count()).isEqualTo(count + 1),
                () -> assertThat(foundCompany.getUrl()).isEqualTo("www.naver.com"),
                () -> assertThat(foundCompany.getName()).isEqualTo("네이버")
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
                () -> assertThat(companyResponse.getUrl()).isEqualTo(company1.getUrl()),
                () -> assertThat(companyResponse.getName()).isEqualTo(company1.getName()),
                () -> assertThat(companyResponse.getCourses()).isEqualTo(company1.getCourses())
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
    public void modifyCompany() throws Exception {
        //given
        Long id = companyRepository.save(company1).getId();
        CompanyRequest companyRequest = CompanyRequest.builder()
                                        .url("www.kakao.com")
                                        .name("카카오")
                                        .build();

        //when
        companyService.modifyCompany(id, companyRequest);
        Company foundCompany = companyRepository.findById(id)
                .orElseThrow(() -> new CompanyNotFoundException(NOT_FOUND_COMPANY));
        //then
        assertAll(
                () -> assertThat(foundCompany.getUrl()).isEqualTo(companyRequest.getUrl()),
                () -> assertThat(foundCompany.getName()).isEqualTo(companyRequest.getName())
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
