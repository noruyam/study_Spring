package spring.di.ui;

import spring.di.Exam;

public class GridExamConsole implements ExamConsole{
    Exam exam;

    public GridExamConsole(Exam exam) {
        this.exam = exam;
    }

    @Override
    public void print() {
        System.out.println(exam.total());
        System.out.println(exam.avg());
    }
}
