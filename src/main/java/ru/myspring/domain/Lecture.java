package ru.myspring.domain;

import java.util.stream.Stream;

public interface Lecture {
    Stream<Slide> getSlides();
}
