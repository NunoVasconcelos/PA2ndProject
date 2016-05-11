package ist.meic.pa.GenericFunctionsExtended;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

public class Cache {

    private HashMap<ArrayList<Class<?>>,ArrayList<Method>> cachedMethods;

    public void addToCache(ArrayList<Class<?>> key,ArrayList<Method> value)
    {
        cachedMethods.put(key,value);
    }

    public boolean containsKey(ArrayList<Class<?>> key)
    {
        return cachedMethods.containsKey(key);
    }

    public void invokeMethods(ArrayList<Class<?>> key ,Object obj, Object[] objs) throws InvocationTargetException, IllegalAccessException {
        ArrayList<Method> methodsToInvoke = cachedMethods.get(key);

        for (Method m: methodsToInvoke)
        {
            m.invoke(obj,objs);
        }
    }
}
