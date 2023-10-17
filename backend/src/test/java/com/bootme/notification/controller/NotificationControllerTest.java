package com.bootme.notification.controller;

import com.bootme.notification.dto.NotificationResponse;
import com.bootme.util.ControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static com.bootme.util.fixture.NotificationFixture.getNotificationResponse;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(NotificationController.class)
@DisplayName("NotificationController 클래스의")
class NotificationControllerTest extends ControllerTest {

    @Test
    @DisplayName("findNotificationsByMemberId()는 정상 요청시 상태코드 200을 반환한다.")
    void findNotificationsByMemberId() throws Exception {
        //given
        NotificationResponse notificationResponse1 = getNotificationResponse(1);
        NotificationResponse notificationResponse2 = getNotificationResponse(2);
        NotificationResponse notificationResponse3 = getNotificationResponse(3);
        given(notificationService.findNotificationsByMemberId(anyLong())).willReturn(List.of(notificationResponse1, notificationResponse2, notificationResponse3));

        //when
        ResultActions perform = mockMvc.perform(
                RestDocumentationRequestBuilders.get("/notifications/{memberId}", 1)
                        .accept(MediaType.APPLICATION_JSON));

        //then
        perform.andExpect(status().isOk());

        //docs
        perform.andDo(print())
                .andDo(document("notification/findNotificationsByMemberId/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }

    @Test
    @DisplayName("markAllNotificationsAsCheckedForMember()는 정상 요청시 상태코드 200을 반환한다.")
    void markAllNotificationsAsCheckedForMember() throws Exception {
        //given
        NotificationResponse notificationResponse1 = getNotificationResponse(1);
        NotificationResponse notificationResponse2 = getNotificationResponse(2);
        NotificationResponse notificationResponse3 = getNotificationResponse(3);
        given(notificationService.findNotificationsByMemberId(anyLong())).willReturn(List.of(notificationResponse1, notificationResponse2, notificationResponse3));

        //when
        ResultActions perform = mockMvc.perform(
                RestDocumentationRequestBuilders.put("/notifications/{memberId}/checked", 1)
                        .accept(MediaType.APPLICATION_JSON));

        //then
        perform.andExpect(status().isOk());

        //docs
        perform.andDo(print())
                .andDo(document("notification/markAllNotificationsAsCheckedForMember/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }

}