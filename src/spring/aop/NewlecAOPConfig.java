package spring.aop;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import spring.aop.entity.NewlecExam;


@ComponentScan
@Configuration
@EnableAspectJAutoProxy
public class NewlecAOPConfig {

    @Bean
    Exam exam(){
        System.out.println("config!!!!");

        return new NewlecExam();
    }

//    @Bean
//    AnnoAspect annoAspect(){ // 이미 @EnableAspectJAutoProxy 통해서 @Aspect 어노테이션으로 선언했기때문에 중복으로 등록할 필요없다.
//        return new AnnoAspect();
//    }
}
