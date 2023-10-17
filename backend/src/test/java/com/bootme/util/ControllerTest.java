package com.bootme.util;

import com.bootme.auth.service.AuthService;
import com.bootme.auth.util.AuthenticationArgumentResolver;
import com.bootme.auth.util.TokenProvider;
import com.bootme.bookmark.service.CourseBookmarkService;
import com.bootme.bookmark.service.PostBookmarkService;
import com.bootme.comment.service.CommentService;
import com.bootme.common.interceptor.RateLimitInterceptor;
import com.bootme.common.interceptor.TokenValidationInterceptor;
import com.bootme.course.service.CompanyService;
import com.bootme.course.service.CourseService;
import com.bootme.image.service.ImageService;
import com.bootme.member.service.MemberService;
import com.bootme.notification.service.NotificationService;
import com.bootme.post.service.PostService;
import com.bootme.prompt.service.PromptService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.http.HttpDocumentation.httpRequest;
import static org.springframework.restdocs.http.HttpDocumentation.httpResponse;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;

@ExtendWith(RestDocumentationExtension.class)
public abstract class ControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected AuthenticationArgumentResolver authenticationArgumentResolver;

    @MockBean
    protected RateLimitInterceptor rateLimitInterceptor;

    @Autowired
    protected TokenValidationInterceptor tokenValidationInterceptor;

    @MockBean
    protected TokenProvider tokenProvider;

    @MockBean
    protected AuthService authService;

    @MockBean
    protected CompanyService companyService;

    @MockBean
    protected CourseService courseService;

    @MockBean
    protected PostService postService;

    @MockBean
    protected CommentService commentService;

    @MockBean
    protected MemberService memberService;

    @MockBean
    protected CourseBookmarkService courseBookmarkService;

    @MockBean
    protected PostBookmarkService postBookmarkService;

    @MockBean
    protected NotificationService notificationService;

    @MockBean
    protected ImageService imageService;

    @MockBean
    protected PromptService promptService;

    @BeforeEach
    void setUp(final WebApplicationContext context, final RestDocumentationContextProvider provider) {
        setMockMvcRestDocsSpec(context, provider);
    }

    private void setMockMvcRestDocsSpec(final WebApplicationContext context,
                                        final RestDocumentationContextProvider provider) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(documentationConfiguration(provider)
                        .snippets()
                        .withDefaults(httpRequest(), httpResponse())
                        .and()
                        .operationPreprocessors()
                        .withRequestDefaults(
                                modifyUris()
                                        .scheme("https")
                                        .host("api.bootMe.app")
                                        .removePort(),
                                prettyPrint()
                        ).withResponseDefaults(
                                removeHeaders(
                                        "Transfer-Encoding",
                                        "Date",
                                        "Keep-Alive",
                                        "Connection"),
                                prettyPrint()
                        )
                ).build();
    }

}
