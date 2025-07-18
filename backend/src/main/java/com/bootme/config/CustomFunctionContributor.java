package com.bootme.config;

import org.hibernate.boot.model.FunctionContributions;
import org.hibernate.boot.model.FunctionContributor;

import static org.hibernate.type.StandardBasicTypes.BOOLEAN;

public class CustomFunctionContributor implements FunctionContributor {

    private static final String FUNCTION_NAME = "fulltext_search";
    private static final String FUNCTION_PATTERN = "to_tsvector('simple', ?1 || ' ' || ?2 || ' ' || ?3) @@ plainto_tsquery('simple', ?4)";

    @Override
    public void contributeFunctions(final FunctionContributions functionContributions) {
        functionContributions.getFunctionRegistry()
                .registerPattern(FUNCTION_NAME, FUNCTION_PATTERN,
                        functionContributions.getTypeConfiguration().getBasicTypeRegistry().resolve(BOOLEAN));
    }

}
