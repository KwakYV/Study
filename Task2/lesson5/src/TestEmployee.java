public class TestEmployee {
    public static void main(String[] args) {
        Employee[] listOfEmp = new Employee[5];
        listOfEmp[0] = new Employee("Alan",
                "Walker",
                "Singer",
                "alan.walker@gmail.com",
                "+398475783993",
                130000.456,
                35);

        listOfEmp[1] = new Employee("Ben",
                "Afflek",
                "Actor",
                "ben.afflek@gmail.com",
                "+3984454583993",
                1300000,
                45);

        listOfEmp[2] = new Employee("Zivert",
                "",
                "Singer",
                "zivert@gmail.com",
                "+89847573993",
                500000,
                30);
        listOfEmp[3] = new Employee("Robert",
                "Downey",
                "Actor",
                "robert.downey@gmail.com",
                "+3984759783993",
                300000000,
                52);
        listOfEmp[4] = new Employee("Johny",
                "Dept",
                "Actor",
                "johny.dept@gmail.com",
                "+39845475783993",
                10000000,
                57);

        for (Employee  emp: listOfEmp){
            if (emp.getAge() > 40){
                System.out.println("---------Information---------");
                emp.info();
            }
        }



    }
}
