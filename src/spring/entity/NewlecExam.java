package spring.entity;

import spring.di.Exam;

public class NewlecExam implements Exam {

    private int kor, ent, math, com;

    @Override
    public int total() {
        return kor + ent + math + com;
    }

    @Override
    public float avg() {
        return total() / 4.0f;
    }
}
