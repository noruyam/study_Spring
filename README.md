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

### 3. IoC(Inversion of Control) Container 에 bean 등록하여 사용하는 방법

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
* GridExamConsole.java   
  ※ExamConsole 을 상속한 GridExamConsole 클래스에 기본 생성자와 setter가 있어야만 bean등록이 된다.
```java
public class GridExamConsole implements ExamConsole {
  public GridExamConsole() {}
  private Exam exam;
  @Override
  public void setExam(Exam exam) {
      this.exam = exam;
  }
  ...
}
```
* main.java   
  ApplicationContext 생성할 때 위에서 bean을 설정한 setting.xml을 통해서 생성해 주면 xml에 등록된 bean name을 가지고 사용 가능해진다.
```java
ApplicationContext context = new ClassPathXmlApplicationContext("spring/di/setting.xml");
//ExamConsole console = (ExamConsole) context.getBean("console");
ExamConsole console = context.getBean(ExamConsole.class);
console.print();
```

### 4. 어노테이션을 사용한 방법 

```xml
<!--setting.xml에 어노테이션을 사용한다는 코드를 우선 선언한다.-->
<context:annotation-config/>

<bean id="console" class="spring.di.ui.InlineExamConsole">
<!--console.setExam(exam);-->
<!--■ 해당 부분을 지우고 해당 setter에 @Autowired-->
<!--property name="exam" ref="exam"/-->
</bean>

<!--@Qualifier("exam2") // xml에 id값을 매칭해준다.-->
<bean id="exam1" class="spring.di.entity.NewlecExam"/>
<bean id="exam2" class="spring.di.entity.NewlecExam"/>
```

```java
@Autowired // xml에 값을 지우고 대응되는 곳에 어노테이션을 사용한다. 
@Qualifier("exam2") // xml에 id값을 매칭해준다.
@Override
public void setExam(Exam exam) {
    this.exam = exam;
}
```




---

## IoC(Inversion of Control) Container
Bean은 개발자가 IoC에 등록한 객체들이다.   
IoC Container는 Bean의 관리를 도와주는 컨테이너이다.   
Bean을 생성해서 Container에 등록하여 IoC 방식으로 운영하는 것이 많은 이점이 있기 때문에 존재한다.
