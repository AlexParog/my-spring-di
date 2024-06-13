package ru.myspring.di.annotation;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Аннотация для инъекции случайного целого числа в поле.
 */
@Retention(RUNTIME)
public @interface InjectRandomInt {
    /**
     * Минимальное значение.
     *
     * @return минимальное значение
     */
    int min();

    /**
     * Максимальное значение.
     *
     * @return максимальное значение
     */
    int max();
}
