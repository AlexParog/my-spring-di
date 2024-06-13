package ru.myspring.di.annotation;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Аннотация для автоматического внедрения зависимостей по типу.
 */
@Retention(RUNTIME)
public @interface InjectByType {
}
