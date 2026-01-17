public class Simon {

    private static String name;

    private static void sayHi() {
        System.out.println();
        System.out.printf("""
                ____________________________________________________________
                 Hello! I'm %s!
                 What can I do for you?
                ____________________________________________________________
                %n""", name);
    }

    private static void sayBye() {
        System.out.printf("""
                Bye. Hope to see you again soon!
                ____________________________________________________________ 
                """);
    }

    public static void main(String[] args) {
        name = "Simon";
        sayHi();
        sayBye();
    }
}