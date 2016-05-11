package ist.meic.pa.GenericFunctionsExtended;


import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

public class Cache {

    private HashMap<ArrayList<Class<?>>,ArrayList<Method>> cachedMethods;

    public void addToCache(ArrayList<Class<?>> key,ArrayList<Method> value)
    {
        cachedMethods.put(key,value);
    }
}
