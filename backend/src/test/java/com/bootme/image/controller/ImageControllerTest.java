package com.bootme.image.controller;

import com.bootme.util.ControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
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
        given(imageService.upload(any(), any(), any())).willReturn("S3 업로드 된 이미지의 객체 URL");

        //when
        ResultActions perform = mockMvc.perform(
                MockMvcRequestBuilders.multipart("/images")
                        .file(imageFile)
                        .param("imageType", "PROFILE")
        );

        //then
        perform.andExpect(status().isOk());

        //docs
        perform.andDo(print())
                .andDo(document("images/upload/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }

}
