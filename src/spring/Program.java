package spring;

import spring.di.Exam;
import spring.di.ui.ExamConsole;
import spring.di.ui.GridExamConsole;
import spring.di.ui.InlineExamConsole;
import spring.entity.NewlecExam;

public class Program {
    public static void main(String[] args) {
        Exam exam = new NewlecExam();

//        ExamConsole console = new InlineExamConsole(exam);
        ExamConsole console = new GridExamConsole(exam);
        console.print();

    }
}