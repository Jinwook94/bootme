package com.bootme.course.service;

import com.bootme.course.domain.QCourse;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import java.util.List;

/**
 * 코스 조회시 필터링을 위해 Querydsl Predicate 을 반환함
 *
 * <p>
 *     1. 쿼리 파라미터로 전달받은 필터 옵션들을 확인 <br>
 *     2. 전달 받은 필터링 옵션을 바탕으로 Predicate 객체 생성 <br>
 *     3. 이후 데이터베이스로부터 조건에 맞는 코스 정보를 가져오는데 Predicate 객체를 사용 <br> <br>
 * 필터 그룹 간의 상관관계 (OR, AND)
 * <pre>
 * |-----------------|-----------------|--------------|-----------|-----------|
 * |                 | SuperCategories | SubCategories| Languages | Frameworks|
 * |-----------------|-----------------|--------------|-----------|-----------|
 * | SuperCategories | OR              | OR           | AND       | AND       |
 * | SubCategories   | OR              | OR           | AND       | AND       |
 * | Languages       | AND             | AND          | OR        | OR        |
 * | Frameworks      | AND             | AND          | OR        | OR        |
 * |-----------------|-------------- --|--------------|-----------|-----------|
 * </pre>
 * </p>
 */
@Component
public class CoursePredicateBuilder {

    public Predicate build(MultiValueMap<String, String> filters) {
        QCourse course = QCourse.course;
        BooleanBuilder builder = new BooleanBuilder();

        List<String> superCategories = filters.get("superCategory");
        List<String> subCategories = filters.get("subCategory");

        if (superCategories != null && !superCategories.isEmpty()) {
            BooleanBuilder superCategoryBuilder = new BooleanBuilder();
            for (String superCategory : superCategories) {
                superCategoryBuilder.or(course.categories.superCategory.contains(superCategory));
            }
            builder.and(superCategoryBuilder);
        }

        if (subCategories != null && !subCategories.isEmpty()) {
            BooleanBuilder subCategoryBuilder = new BooleanBuilder();
            for (String subCategory : subCategories) {
                subCategoryBuilder.or(course.categories.subCategory.contains(subCategory));
            }
            builder.and(subCategoryBuilder);
        }

        List<String> languages = filters.get("languages");
        List<String> frameworks = filters.get("frameworks");

        if (languages != null && !languages.isEmpty()) {
            BooleanBuilder languageBuilder = new BooleanBuilder();
            for (String language : languages) {
                languageBuilder.and(course.stacks.languages.contains(language));
            }
            builder.and(languageBuilder);
        }

        if (frameworks != null && !frameworks.isEmpty()) {
            BooleanBuilder frameworkBuilder = new BooleanBuilder();
            for (String framework : frameworks) {
                frameworkBuilder.or(course.stacks.frameworks.contains(framework));
            }
            builder.and(frameworkBuilder);
        }

        String costInput = filters.getFirst("costInput");
        String periodInput = filters.getFirst("periodInput");
        String isRecommended = filters.getFirst("isRecommended");
        String isFree = filters.getFirst("isFree");
        String isKdt = filters.getFirst("isKdt");
        String isOnline = filters.getFirst("isOnline");
        String isTested = filters.getFirst("isTested");
        String isPrerequisiteRequired = filters.getFirst("isPrerequisiteRequired");

        if (costInput != null && !costInput.isEmpty()) {
            builder.and(course.cost.loe(Integer.valueOf(costInput)));
        }

        if (periodInput != null && !periodInput.isEmpty()) {
            builder.and(course.period.loe(Integer.parseInt(periodInput) * 30));
        }

        if (isRecommended != null && !isRecommended.isEmpty()) {
            builder.and(course.isRecommended.eq(Boolean.valueOf(isRecommended)));
        }

        if (isFree != null && !isFree.isEmpty()) {
            builder.and(course.isFree.eq(Boolean.valueOf(isFree)));
        }

        if (isKdt != null && !isKdt.isEmpty()) {
            builder.and(course.isKdt.eq(Boolean.valueOf(isKdt)));
        }

        if (isOnline != null && !isOnline.isEmpty()) {
            builder.and(course.isOnline.eq(Boolean.valueOf(isOnline)));
        }

        if (isTested != null && !isTested.isEmpty()) {
            builder.and(course.isTested.eq(Boolean.valueOf(isTested)));
        }

        if (isPrerequisiteRequired != null && !isPrerequisiteRequired.isEmpty()) {
            builder.and(course.isPrerequisiteRequired.eq(Boolean.valueOf(isPrerequisiteRequired)));
        }

        if (builder.hasValue()) {
            return builder.getValue();
        } else {
            return course.isNotNull();
        }
    }

}
