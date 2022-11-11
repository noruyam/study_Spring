package spring.di.ui;

import spring.di.Exam;

public class InlineExamConsole implements ExamConsole {
    private Exam exam;

    public InlineExamConsole() {
        /*
        ApplicationContext 를 사용하여 xml bean을 등록해서 run 할 때
        기본 생성자를 설정하지 않으면 실행오류가 난다.
        */
    }

    public InlineExamConsole(Exam exam) {
        this.exam = exam;
    }

    public void print() {
        System.out.printf("total : %d , avg : %f", exam.total(), exam.avg());
    }

    @Override
    public void setExam(Exam exam) {
        this.exam = exam;
    }

}
