package ru.myspring.domain;

public class SpeakerImpl implements Speaker {
    @Override
    public void speak(String text) {
        System.out.printf("Speaking: %s%n", text);
    }
}
