package spring.di.ui;

import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    @Override
    public void setExam(Exam exam) {
        this.exam = exam;
    }
}
