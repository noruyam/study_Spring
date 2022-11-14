# study_Spring


# AOP (Aspect Oriented Programming)
관점 지향 프로그래밍이라고 불린다.   
관점 지향은 쉽게 말해 어떤 로직을 기준으로 핵심적인 관점, 부가적인 관점으로 나누어서 보고 그 관점을 기준으로 각각 모듈화하겠다는 것이다.   
여기서 모듈화란 어떤 공통된 로직이나 기능을 하나의 단위로 묶는 것을 말한다.

AOP에서 각 관점을 기준으로 로직을 모듈화한다는 것은 코드들을 부분적으로 나누어서 모듈화하겠다는 의미다.   
이때, 소스 코드상에서 다른 부분에 계속 반복해서 쓰는 코드들을 발견할 수 있는 데   
이것을 흩어진 관심사 (Crosscutting Concerns)라 부른다.

---

## 1. AOP 주요 개념
* Aspect : 위에서 설명한 흩어진 관심사를 모듈화 한 것. 주로 부가기능을 모듈화함.
* Target : Aspect를 적용하는 곳 (클래스, 메서드 .. )
* Advice : 실질적으로 어떤 일을 해야할 지에 대한 것, 실질적인 부가기능을 담은 구현체
* JointPoint : Advice가 적용될 위치, 끼어들 수 있는 지점. 메서드 진입 지점, 생성자 호출 시점, 필드에서 값을 꺼내올 때 등 다양한 시점에 적용가능
* PointCut : JointPoint의 상세한 스펙을 정의한 것. 'A란 메서드의 진입 시점에 호출할 것'과 같이 더욱 구체적으로 Advice가 실행될 지점을 정할 수 있음

---
## 2. AOP 구현 방법 

### 1. 순수 JAVA를 통한 구현 방법
proxy를 사용하여 구현
```java
public class NewlecExam implements Exam {
    @Override
    public int total() {
        long start = System.currentTimeMillis();
        int result = kor + eng + math + com;
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        String message = (end - start) + "ms 시간이 걸렸습니다.";
        System.out.println(message);
        return result;
    }
}
```
## ↓
```java
public class Program {
    public static void main(String[] args) {
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
        System.out.printf("total is %d\n", exam.total());
        System.out.printf("total is %f\n", exam.avg());
        System.out.printf("total is %d\n", proxy.total());
        System.out.printf("total is %f\n", proxy.avg());

    }
}
```

### 2. 스프링을 통한 구현 방법
setting.xml을 통하여 구현
```xml
<beans>
<bean id="target" class="spring.aop.entity.NewlecExam" p:kor="101" p:eng="1" p:com="1" p:math="1"/>

    <bean id="logAroundAdvice" class="spring.aop.advice.LogAroundAdvice"/>
    <bean id="logBeforeAdvice" class="spring.aop.advice.LogBeforeAdvice"/>
    <bean id="logAfterRetuningAdvice" class="spring.aop.advice.LogAfterRetuningAdvice"/>
    <bean id="logAfterThrowingAdvice" class="spring.aop.advice.LogAfterThrowingAdvice"/>

    <bean id="exam" class="org.springframework.aop.framework.ProxyFactoryBean">
        <property name="target" ref="target"/>
        <property name="interceptorNames">
            <list>
                <value>logAroundAdvice</value>
                <value>logBeforeAdvice</value>
                <value>logAfterRetuningAdvice</value>
                <value>logAfterThrowingAdvice</value>
            </list>
        </property>
    </bean>
</beans>
```
```java

public class Program {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring/aop/setting.xml");
//        ApplicationContext context = new AnnotationConfigApplicationContext(NewlecDIConfig.class);

        Exam exam = (Exam) context.getBean("exam");

        System.out.printf("total is %d\n", exam.total());
        System.out.printf("avg is %f\n", exam.avg());
    }
}
```
#### Pointcut : 특정 조건에만 사용할 수 있도록 정의하는 것

```xml
<bean id="classicPointCut" class="org.springframework.aop.support.NameMatchMethodPointcut">
    <property name="mappedName" value="total"/>
</bean>

<bean id="classicBeforeAdvisor" class="org.springframework.aop.support.DefaultPointcutAdvisor">
    <property name="advice" ref="logBeforeAdvice"/>
    <property name="pointcut" ref="classicPointCut"/>
</bean>
<bean id="classicAroundAdvice" class="org.springframework.aop.support.DefaultPointcutAdvisor">
    <property name="advice" ref="logAroundAdvice"/>
    <property name="pointcut" ref="classicPointCut"/>
</bean>

<bean id="exam" class="org.springframework.aop.framework.ProxyFactoryBean">
    <property name="target" ref="target"/>
    <property name="interceptorNames">
        <list>
<!--                <value>logAroundAdvice</value>-->
<!--                <value>logBeforeAdvice</value>-->
            <value>classicAroundAdvice</value>
            <value>classicBeforeAdvisor</value>
            <value>logAfterRetuningAdvice</value>
            <value>logAfterThrowingAdvice</value>
        </list>
    </property>
</bean>
```
#### 간소화된 advisor
```xml
<bean id="classicBeforeAdvisor" class="org.springframework.aop.support.RegexpMethodPointcutAdvisor">
    <property name="advice" ref="logBeforeAdvice"/>
    <property name="patterns" >
        <list>
            <value>.*to.*</value>
        </list>
    </property>
</bean>
```
### 3. 어노테이션을 통한 구현 방법
추후 업데이트 예정