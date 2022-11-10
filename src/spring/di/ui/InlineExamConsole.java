package spring.di.ui;

import spring.di.Exam;

public class InlineExamConsole implements ExamConsole {
    private Exam exam;

    public InlineExamConsole(Exam exam) {
        this.exam = exam;
    }

    public void print(){
        System.out.printf("total : %d , avg : %f",exam.total(),exam.avg());
    }

}
