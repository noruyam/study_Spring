package spring.di.ui;

import spring.di.Exam;

public class GridExamConsole implements ExamConsole {
    private Exam exam;

    public GridExamConsole() {
        /*
        ApplicationContext 를 사용하여 xml bean을 등록해서 run 할 때
        기본 생성자를 설정하지 않으면 실행오류가 난다.
        */
    }

    public GridExamConsole(Exam exam) {
        this.exam = exam;
    }

    @Override
    public void print() {
        System.out.println(exam.total());
        System.out.println(exam.avg());
    }

    @Override
    public void setExam(Exam exam) {
        this.exam = exam;
    }
}
