package com.bootme.config;

import org.hibernate.boot.model.FunctionContributions;
import org.hibernate.dialect.MariaDBDialect;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.type.StandardBasicTypes;

public class CustomDialect extends MariaDBDialect {

    public CustomDialect() {
        super();
    }

    @Override
    public void initializeFunctionRegistry(FunctionContributions functionContributions) {
        super.initializeFunctionRegistry(functionContributions);

        functionContributions.getFunctionRegistry().register(
                "match",
                new StandardSQLFunction("match(?1) against (?2)", StandardBasicTypes.BOOLEAN)
        );
    }

}