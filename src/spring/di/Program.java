package spring.di;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import spring.di.entity.NewlecExam;
import spring.di.ui.ExamConsole;
import spring.di.ui.GridExamConsole;
import spring.di.ui.InlineExamConsole;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

public class Program {
    public static void main(String[] args) {
        /*1. 생성자 주입 방법
        Exam exam = new NewlecExam();
        ExamConsole console = new InlineExamConsole(exam);
        ExamConsole console = new GridExamConsole(exam);*/


        /*2. setter setter주입 방법
        Exam exam = new NewlecExam();
        ExamConsole console = new GridExamConsole();
        console.setExam(exam);*/


        /*3. xml에서 bean 등록하여 사용하는 방법
        setting.xml -> 사용하기 위한 class를 bean으로 등록해 준다. 여기서 <property> 를 통해 의존관계를 설정해 줄 수 있다.
        pom.xml     -> 메이븐 프로젝트로 변경하여 pom.xml에 ApplicationContext을 사용하기위한 dependency를 등록해준다.(https://mvnrepository.com/)
        main.java   -> ApplicationContext 생성할때 bean이 등록된 xml을 통해서 생성해주면 xml에 등록된 bean 이름을 가지고 사용가능해진다.
        !!ExamConsole 을 상속한 GridExamConsole 클래스에 기본 생성자가 있어야만 run 가능
        */
        /* XML Configuration을 Java Configuration 변경
        ApplicationContext context = new ClassPathXmlApplicationContext("spring/di/setting.xml");
        */
        ApplicationContext context = new AnnotationConfigApplicationContext(NewlecDIConfig.class);
        ExamConsole console = (ExamConsole) context.getBean("console"); //xml에 <context:annotation-config/>를 지우면서 작동안되는데 @Component("console") 이름을 넣어줘야함
//        ExamConsole console = context.getBean(ExamConsole.class);
        console.print();

        // 생성자 DI index 순서 확인
//        Exam exam = context.getBean(Exam.class);
//        System.out.println("\n" + exam.toString());

        // 1. List<Exam> exams = new ArrayList<>();
        // 2.
//        List<Exam> exams = (List<Exam>) context.getBean("exams");
//        exams.add(new NewlecExam(1, 1, 1, 1));

//        for (Exam e : exams) {
//            System.out.println(e);
//        }
    }
}