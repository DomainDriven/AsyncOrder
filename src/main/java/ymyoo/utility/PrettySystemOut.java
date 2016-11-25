package ymyoo.utility;

/**
 * Created by 유영모 on 2016-10-11.
 */
public class PrettySystemOut {
    public static void println(Class<?> clazz, String msg) {
        System.out.println(String.format("[Current Thread ID - %2s][%-58s] %s", Thread.currentThread().getId(), clazz.getName(), msg));
    }
}
