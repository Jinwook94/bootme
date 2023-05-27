package com.bootme.course.service;

import com.bootme.common.exception.ResourceNotFoundException;
import com.bootme.course.domain.QCourse;
import com.bootme.stack.domain.Stack;
import com.bootme.stack.repository.StackRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.bootme.common.exception.ErrorType.NOT_FOUND_STACK;

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
@RequiredArgsConstructor
public class CourseFilterPredicate implements CoursePredicate {

    private final StackRepository stackRepository;

    public Predicate build(MultiValueMap<String, String> filters) {
        QCourse course = QCourse.course;

        if (filters == null || filters.isEmpty()) {
            return null;
        }

        BooleanBuilder builder = new BooleanBuilder();

        processCategoryFilters(builder, filters, "superCategories", course);
        processCategoryFilters(builder, filters, "subCategories", course);
        processStackFilters(builder, filters, "languages", course);
        processStackFilters(builder, filters, "frameworks", course);

        processInputFilters(builder, filters, "costInput", course.cost::loe, Integer::valueOf);
        processInputFilters(builder, filters, "periodInput", value -> course.period.loe(value * 30), Integer::valueOf);

        processBooleanFilters(builder, filters, "isRecommended", course.isRecommended::eq);
        processBooleanFilters(builder, filters, "isFree", course.isFree::eq);
        processBooleanFilters(builder, filters, "isKdt", course.isKdt::eq);
        processBooleanFilters(builder, filters, "isOnline", course.isOnline::eq);
        processBooleanFilters(builder, filters, "isTested", course.isTested::eq);
        processBooleanFilters(builder, filters, "isPrerequisiteRequired", course.isPrerequisiteRequired::eq);

        return builder.getValue();
    }

    private void processCategoryFilters(BooleanBuilder builder, MultiValueMap<String, String> filters,
                                        String key, QCourse course) {
        List<String> categories = filters.get(key);
        if (!CollectionUtils.isEmpty(categories)) {
            BooleanBuilder categoryBuilder = new BooleanBuilder();
            for (String category : categories) {
                categoryBuilder.or(course.categories.values.contains(category));
            }
            builder.and(categoryBuilder);
        }
    }

    private void processStackFilters(BooleanBuilder builder, MultiValueMap<String, String> filters,
                                     String key, QCourse course) {
        List<String> stackNames = filters.get(key);
        if (!CollectionUtils.isEmpty(stackNames)) {
            // 데이터베이스에서 해당 종류의 기술 스택 전체를 꺼낸다.
            List<Stack> stacks = stackRepository.findByNameIn(stackNames);
            if (stacks.size() != stackNames.size()) {
                throw new ResourceNotFoundException(NOT_FOUND_STACK, "선택된 기술 스택 중 등록되지 않은 것이 있습니다.");
            }

            // 빠른 조회를 위해 기술 스택과 각각의 ID 를 매핑한다.
            Map<String, Long> stackNameToIdMap = stacks.stream()
                    .collect(Collectors.toMap(Stack::getName, Stack::getId));

            BooleanBuilder stackBuilder = new BooleanBuilder();
            for (String stackName : stackNames) {
                Long stackId = stackNameToIdMap.get(stackName);
                stackBuilder.and(course.courseStacks.any().stack.id.eq(stackId));
            }
            builder.and(stackBuilder);
        }
    }

    private <T> void processInputFilters(BooleanBuilder builder, MultiValueMap<String, String> filters,
                                         String key, Function<T, BooleanExpression> expr, Function<String, T> converter) {
        String input = filters.getFirst(key);
        if (input != null && !input.isEmpty()) {
            builder.and(expr.apply(converter.apply(input)));
        }
    }

    private void processBooleanFilters(BooleanBuilder builder, MultiValueMap<String, String> filters,
                                       String key, Function<Boolean, BooleanExpression> expr) {
        processInputFilters(builder, filters, key, expr, Boolean::valueOf);
    }

}