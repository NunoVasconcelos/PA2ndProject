package ist.meic.pa.GenericFunctionsExtended;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

public class Cache {

    private HashMap<ArrayList<Class<?>>,ArrayList<InvokeWrapper>> cachedMethods;

    public Cache()
    {
        cachedMethods = new HashMap<>();
    }

    public void addToCache(ArrayList<Class<?>> key,ArrayList<InvokeWrapper> value)
    {
        cachedMethods.put(key,value);
    }

    public boolean containsKey(ArrayList<Class<?>> key)
    {
        return cachedMethods.containsKey(key);
    }

    public void invokeMethods(ArrayList<Class<?>> key , Object[] x) throws InvocationTargetException, IllegalAccessException {
        ArrayList<InvokeWrapper> methodsToInvoke = cachedMethods.get(key);

        for (InvokeWrapper m: methodsToInvoke)
        {
            m.getMethod().invoke(m.getGfMethod(),x);
        }
    }

    public boolean isEmpty()
    {
        return cachedMethods.isEmpty();
    }
}
