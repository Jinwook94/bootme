package com.bootme.course.service;

import com.bootme.course.domain.*;
import com.bootme.course.dto.CourseRequest;
import com.bootme.course.dto.CourseResponse;
import com.bootme.course.exception.CompanyNotFoundException;
import com.bootme.course.exception.CourseNotFoundException;
import com.bootme.course.repository.CompanyRepository;
import com.bootme.course.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.bootme.common.exception.ErrorType.NOT_FOUND_COMPANY;
import static com.bootme.common.exception.ErrorType.NOT_FOUND_COURSE;

@Service
@Transactional
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final CompanyRepository companyRepository;

    public Long addCourse(CourseRequest courseRequest){
        String companyName = courseRequest.getCompanyName();
        Company company = companyRepository.findByName(companyName)
                                    .orElseThrow(() -> new CompanyNotFoundException(NOT_FOUND_COMPANY));
        Course course = Course.builder()
                        .title(courseRequest.getTitle())
                        .url(courseRequest.getUrl())
                        .company(company)
                        .location(courseRequest.getLocation())
                        .cost(courseRequest.getCost())
                        .costType(Enum.valueOf(CostType.class, courseRequest.getCostType()))
                        .dates(courseRequest.getDates())
                        .onoffline(Enum.valueOf(OnOffline.class, courseRequest.getOnOffline()))
                        .tags(courseRequest.getTags())
                        .prerequisites(Enum.valueOf(Prerequisites.class, courseRequest.getPrerequisites()))
                        .isRecommended(courseRequest.isRecommended())
                        .isTested(courseRequest.isTested())
                        .build();
        Course savedCourse = courseRepository.save(course);
        return savedCourse.getId();
    }

    @Transactional(readOnly = true)
    public CourseResponse findById(Long id) {
        Course foundCourse = courseRepository.findById(id)
                                .orElseThrow(() -> new CourseNotFoundException(NOT_FOUND_COURSE));
        return CourseResponse.of(foundCourse);
    }

    public void modifyCourse(Long id, CourseRequest courseRequest){
        Course course = courseRepository.findById(id)
                         .orElseThrow(() -> new CourseNotFoundException(NOT_FOUND_COURSE));

        course.modifyCourse(courseRequest);
    }

    public void deleteCourse(Long id){
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException(NOT_FOUND_COURSE));
        Long companyId = course.getCompany().getId();
        Company company = this.companyRepository.findById(companyId)
                .orElseThrow(() -> new CompanyNotFoundException(NOT_FOUND_COMPANY));

        company.deleteCourse(course);
        courseRepository.delete(course);
    }

}
