package geek.java3.lesson7;

public class Template {

    public Template(){

    }

    @BeforeSuite
    public void begin(){
        System.out.println("Starting testing reflections and annotations");
    }

    public void withoutTest(){
        System.out.println("You don't have to invoke this method. Check it up");
    }

    @Test(1)
    public void test1(){
        System.out.println("I have the first priority to run");
    }

    @Test(7)
    public void test2(){
        System.out.println("I have last priority to run");
    }

    @Test(4)
    public void test3(){
        System.out.println("I have second priority to run");
    }

    @AfterSuite
    public void end(){
        System.out.println("Finished testing reflections and annotations");
    }
}
