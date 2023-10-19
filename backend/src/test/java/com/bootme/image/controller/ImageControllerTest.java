package com.bootme.image.controller;

import com.bootme.util.ControllerTest;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;

import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ImageController.class)
@DisplayName("ImageController 클래스의")
class ImageControllerTest extends ControllerTest {

    @Test
    @DisplayName("upload()는 정상 요청시 상태코드 200을 반환한다.")
    void upload() throws Exception {
        //given
        MockMultipartFile imageFile = new MockMultipartFile("image", "filename.jpg", "image/jpeg", "some-image".getBytes());
        given(imageService.upload(any(), any(), any())).willReturn("https://bootme-images.s3.ap-northeast-2.amazonaws.com/etc/smile_face.png");

        //when
        ResultActions perform = mockMvc.perform(
                RestDocumentationRequestBuilders.multipart("/images")
                        .file(imageFile)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .param("imageType", "PROFILE")
        );

        //then
        perform.andExpect(status().isOk());

        //docs
        perform.andDo(print())
                .andDo(document("images/upload/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseBody(),
                        resource(ResourceSnippetParameters.builder()
                                .summary("이미지 업로드")
                                .description("텍스트 에디터에서 첨부된 이미지를 S3 버킷에 업로드 후 객체 URL을 반환한다." +
                                        "<br><br> restdocs-api-spec 라이브러리에서 Mutlipart 파라미터 지원하지 않기 때문에 정상적으로 문서 작성하지 못함 " +
                                        "<br> 참고: https://github.com/ePages-de/restdocs-api-spec/issues/105")
                                .build())
                ));
    }

}
