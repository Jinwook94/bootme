package com.bootme.admin.service;

import com.bootme.admin.domain.AdminAccount;
import com.bootme.admin.dto.AdminLoginRequest;
import com.bootme.admin.exception.InvalidAdminException;
import com.bootme.auth.dto.AuthInfo;
import com.bootme.auth.dto.TokenResponse;
import com.bootme.auth.token.JwtTokenProvider;
import com.bootme.common.exception.ErrorType;
import com.bootme.course.dto.CourseRequest;
import com.bootme.course.dto.CourseResponse;
import com.bootme.course.service.CourseService;
import com.bootme.member.domain.RoleType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminService {

    public final JwtTokenProvider jwtTokenProvider;
    public final AdminAccount adminAccount;
    public final CourseService courseService;

    public TokenResponse login(AdminLoginRequest adminLoginRequest) {
        validateLogin(adminLoginRequest.getId(), adminLoginRequest.getPassword());
        AuthInfo authInfo = new AuthInfo(1L, RoleType.ADMIN.getName(), "ADMIN");
        String token = jwtTokenProvider.createAccessToken(authInfo);
        return new TokenResponse(token);
    }

    public void validateLogin(String id, String password) {
        if (!Objects.equals(adminAccount.getId(), id) || !Objects.equals(adminAccount.getPassword(), password)) {
            throw new InvalidAdminException(ErrorType.NOT_AUTHENTICATED);
        }
    }

    public Long addCourse(CourseRequest courseRequest){
        return courseService.addCourse(courseRequest);
    }

    public CourseResponse findById(Long id) {
        return courseService.findById(id);
    }

    public List<CourseResponse> findAll(){
        return courseService.findAll();
    }

    public void modifyCourse(Long id, CourseRequest courseRequest){
        courseService.modifyCourse(id, courseRequest);
    }

    public void deleteCourse(Long id){
        courseService.deleteCourse(id);
    }
}
