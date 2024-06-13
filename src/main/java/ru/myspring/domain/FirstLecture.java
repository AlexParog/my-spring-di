package ru.myspring.domain;

import java.util.stream.Stream;

public class FirstLecture implements Lecture {
    @Override
    public Stream<Slide> getSlides() {
        return Stream.of(new Slide("slide1", "blah-blah"),
                new Slide("slide2", "blah-blah-blah"));
    }
}
