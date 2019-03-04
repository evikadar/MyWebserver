public class Routes {

    @WebServer(url = "/test1")
    public static String test1 () {
        return "I got this string from test1";
    }


    @WebServer(url = "/test2")
    public static String test2() {
        return "And this text is from test2";
    }

    @WebServer(url = "/habits")
    public static String habits() {
        return "Habit Changer App";
    }

    @WebServer(url = "/topics")
    public static String topics() {
        return String.format("Diet %n Fitness %n " +
                "Personal Development %n Learn Something New %n Quit Addicition %n");
    }

}
