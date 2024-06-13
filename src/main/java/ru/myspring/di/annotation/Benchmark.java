package ru.myspring.di.annotation;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Аннотация для маркировки методов или классов, которые должны быть замерены по времени выполнения.
 */
@Retention(RUNTIME)
public @interface Benchmark {
}
