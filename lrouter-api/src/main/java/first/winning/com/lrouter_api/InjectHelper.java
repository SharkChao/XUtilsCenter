package first.winning.com.lrouter_api;

import android.app.Activity;

import java.lang.reflect.Constructor;

/**
 * Created by Admin on 2018/4/19.
 */

public class InjectHelper {
    public static void inject(Activity host) {
        // 1、
        String classFullName = host.getClass().getName() + "$$ViewInjector";
        try {
            // 2、
            Class proxy = Class.forName(classFullName);
            // 3、
            Constructor constructor = proxy.getConstructor(host.getClass());
            // 4、
            constructor.newInstance(host);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
