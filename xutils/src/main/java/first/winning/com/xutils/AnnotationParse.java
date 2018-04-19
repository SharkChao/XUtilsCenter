package first.winning.com.xutils;

import android.app.Activity;
import android.view.View;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import first.winning.com.xutils.annotation.ClickParse;
import first.winning.com.xutils.annotation.LayoutParse;
import first.winning.com.xutils.annotation.ViewParse;

/**
 * Created by Admin on 2018/4/19.
 */

public class AnnotationParse {
    public static void injectActivity(Activity activity) {
        if (activity == null) {
            return;
        }
        Class<? extends Activity> aClass = activity.getClass();
        if (isLayoutParse(aClass)) {
            LayoutParse layoutParse = aClass.getAnnotation(LayoutParse.class);
            activity.setContentView(layoutParse.value());
        }
        View decorView = activity.getWindow().getDecorView();
        initViews(aClass.getDeclaredFields(), decorView, activity);
        initClick(aClass.getDeclaredMethods(),decorView,activity);
    }

    private static void initViews(Field[] fields, View decorView, Object object) {
        for (Field field : fields) {
            if (isViewParse(field)) {
                ViewParse viewParse = field.getAnnotation(ViewParse.class);
                int value = viewParse.value();
                if (value != -1) {
                    View viewById = decorView.findViewById(value);
                    if (viewById != null) {
                        field.setAccessible(true);
                        try {
                            field.set(object, viewById);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    private static void initClick(Method[] methods, View decorView, Object object) {
        for (Method method : methods) {
            if (isClickParse(method)) {
                ClickParse clickParse = method.getAnnotation(ClickParse.class);
                int[] value = clickParse.value();
                MyClickListener myClickListener = new MyClickListener(method, object);
                for (int id : value) {
                    decorView.findViewById(id).setOnClickListener(myClickListener);
                }
            }
        }
    }

    private static boolean isLayoutParse(Class<? extends Activity> clazz) {
        return clazz.isAnnotationPresent(LayoutParse.class);
    }

    private static boolean isViewParse(Field field) {
        return field.isAnnotationPresent(ViewParse.class);
    }

    private static boolean isClickParse(Method method) {
        return method.isAnnotationPresent(ClickParse.class);
    }

    static class MyClickListener implements View.OnClickListener {

        private Method mMethod;
        private Object mObject;

        public MyClickListener(Method method, Object receiver) {
            mMethod = method;
            mObject = receiver;
        }

        @Override
        public void onClick(View view) {
            mMethod.setAccessible(true);
            try {
                mMethod.invoke(mObject,view);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}
