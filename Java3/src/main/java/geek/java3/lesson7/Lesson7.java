package geek.java3.lesson7;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Lesson7 {
    public static void start(Class clazz) {
        Method[] methods =  clazz.getDeclaredMethods();
        List<Method> before = new ArrayList<>();
        List<Method> after = new ArrayList<>();
        List<Method> test = new ArrayList<>();

        for (Method method : methods) {
            if (method.getAnnotation(BeforeSuite.class) != null){
                before.add(method);
            }
            if (method.getAnnotation(AfterSuite.class) != null){
                after.add(method);
            }
            if (method.getAnnotation(Test.class) != null){
                test.add(method);
            }
        }

        Method temp;
        for (int i = 0; i < test.size(); i++){
            if (i != test.size()-1){
                if (test.get(i).getAnnotation(Test.class).value() >
                test.get(i+1).getAnnotation(Test.class).value()){
                    temp = test.get(i);
                    test.set(i, test.get(i+1));
                    test.set(i+1, temp);
                }
            }
        }

        if (before.size()>1 || after.size() >1){
            throw new RuntimeException("There are more then one BeforeSuite or AfterSuite declaration");
        }



        Template templ = new Template();

        try {
            before.get(0).invoke(templ);
            for (Method method : test) {
                method.invoke(templ);
            }
            after.get(0).invoke(templ);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        start(Template.class);
    }
}
