package ru.myspring;

import ru.myspring.di.ObjectFactory;
import ru.myspring.domain.RoboLecturer;

public class App {
    public static void main(String[] args) throws ReflectiveOperationException {
        RoboLecturer lecturer = new ObjectFactory().createObject(RoboLecturer.class);
        lecturer.lecture();
    }
}
