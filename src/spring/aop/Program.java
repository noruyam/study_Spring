package spring.aop;


import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import spring.aop.advice.AnnoAspect;
import spring.aop.entity.NewlecExam;
import spring.aop.Exam;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class Program {
    public static void main(String[] args) {
//        ApplicationContext context = new ClassPathXmlApplicationContext("spring/aop/setting.xml");
        ApplicationContext context = new AnnotationConfigApplicationContext(NewlecAOPConfig.class);

        Exam exam = (Exam) context.getBean("exam");

        System.out.printf("total is %d\n", exam.total());
//        System.out.printf("avg is %f\n", exam.avg());

//        Exam ee = new NewlecExam(1,1,1,1);

//        System.out.println(ee.total());

//        SpringApplication.run(Program.class,args);
        /* 순수 자바를 이용한 AOP 구현 방법
        Exam exam = new NewlecExam(1, 1, 1, 1);
        Exam proxy = (Exam) Proxy.newProxyInstance(NewlecExam.class.getClassLoader()
                , new Class[]{Exam.class}
                , new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        long start = System.currentTimeMillis();

                        Object result = method.invoke(exam, args);
                        long end = System.currentTimeMillis();
                        String message = (end - start) + "ms 시간이 걸렸습니다.";
                        System.out.println(message);

                        return result;
                    }
                }
        );
        */


    }
}
