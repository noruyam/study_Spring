package spring.di.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.w3c.dom.ls.LSOutput;
import spring.di.Exam;

@Component("console")
public class InlineExamConsole implements ExamConsole {
    @Autowired(required = true) // xml에서 exam이 없어도 일단 OK!
//    @Qualifier("exam2")
    private Exam exam;

    public InlineExamConsole() {
        /*
        ApplicationContext 를 사용하여 xml bean을 등록해서 run 할 때
        기본 생성자를 설정하지 않으면 실행오류가 난다.
        */
        System.out.println("constructor");
    }

    /* Qualifier 메소드 위에 못쓰고 파라미터에 사용해야한다.
    @Autowired
    public InlineExamConsole(@Qualifier("exam2")Exam exam) {
    */
    public InlineExamConsole(Exam exam) {
        this.exam = exam;
        System.out.println("overload constructor");
    }

    public void print() {
        if (exam == null)
            System.out.printf("total : %d , avg : %f", 0, 0.0);
        else
            System.out.printf("total : %d , avg : %f", exam.total(), exam.avg());
    }


    @Override
    public void setExam(Exam exam) {
        this.exam = exam;
        System.out.println("setter");
    }

}
