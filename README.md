# study_Spring
# DI(dependency Injection)


## DI(dependency Injection) 종속성 주입
DI란 외부에서 두 객체 간의 관계를 결정해주는 디자인 패턴으로,   
인터페이스를 사이에 둬서 클래스 레벨에서는 의존관계가 고정되지 않도록 하고   
런타임 시에 관계를 동적으로 주입하여 유연성을 확보하고 결합도를 낮출 수 있게 해준다.   
의존관계를 주입해주는 여러가지 방법중 몇가지를 살펴보도록 한다.

### 1. 생성자 주입 방법
```java
Exam exam = new NewlecExam();
/*
ExamConsole = 인터페이스 
InlineExamConsole = ExamConsole 상속클래스
GridExamConsole = ExamConsole 상속클래스
ExamConsole이라는 중간 인터페이스를 놓고 클래스를 생성하도록 하여 결합력을 낮춤
*/
//ExamConsole console = new InlineExamConsole(exam);
ExamConsole console = new GridExamConsole(exam);
console.print();
```
### 2. setter 주입방법
```java
Exam exam = new NewlecExam();
ExamConsole console = new GridExamConsole();
console.setExam(exam);
console.print();
```

### 3. xml에서 bean 등록하여 사용하는 방법

* pom.xml   
  메이븐 프로젝트로 변경 후 pom.xml에 ApplicationContext을 사용하기위한 dependency를 등록해준다.(https://mvnrepository.com/)
```
<dependency>
  <groupId>org.springframework</groupId>
  <artifactId>spring-context</artifactId>
  <version>5.3.19</version>
</dependency>
```

* setting.xml   
  사용하기 위한 class를 bean으로 등록해 준다. 여기서 `<property>` 를 통해 의존관계를 설정해 줄 수 있다.
```
<!--Exam exam = new NewlecExam();-->
<bean id="exam" class="spring.di.entity.NewlecExam"/>

<!--ExamConsole console = new GridExamConsole(exam);-->
<bean id="console" class="spring.di.ui.GridExamConsole">
  <!--console.setExam(exam); !!여기서 의존성 주입!! -->
  <property name="exam"  ref="exam"/>
</bean>
```

* main.java   
  ApplicationContext 생성할 때 위에서 bean을 설정한 setting.xml을 통해서 생성해 주면 xml에 등록된 bean name을 가지고 사용 가능해진다.
```java
ApplicationContext context = new ClassPathXmlApplicationContext("spring/di/setting.xml");
//ExamConsole console = (ExamConsole) context.getBean("console");
ExamConsole console = context.getBean(ExamConsole.class);
console.print();
```
---
