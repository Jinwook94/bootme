package com.bootme.data_count.service;

import com.bootme.data_count.domain.DataCount;
import com.bootme.data_count.repository.DataCountRepository;
import com.bootme.post.domain.PostStatus;
import com.bootme.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
@Slf4j
public class DataCountScheduler {

    private final DataCountRepository dataCountRepository;
    private final PostRepository postRepository;

    @Transactional
    @Scheduled(cron = "0 0 * * * *") // 매시간 0분 0초마다 실행
    public void updateDataCounts() {
        updateDataCount(PostStatus.DISPLAY);
        updateDataCount(PostStatus.HIDDEN);
        updateDataCount(PostStatus.DELETED);
    }

    private void updateDataCount(PostStatus postStatus) {
        long actualCount = postRepository.countByStatus(postStatus);
        DataCount dataCount = dataCountRepository.findByTableNameAndStatus("post", postStatus.toString())
                .orElse(new DataCount("post", postStatus.toString(), 0L));

        dataCount.updateCount(actualCount);
        dataCountRepository.save(dataCount);
        log.info("@Scheduled data_count: post 테이블 count 값 업데이트 완료");
    }

}
