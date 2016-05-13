package ist.meic.pa.GenericFunctions;


import ist.meic.pa.GenericFunctionsExtended.Cache;
import ist.meic.pa.GenericFunctionsExtended.InvokeWrapper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;


public class GenericFunction<T> {

    private String functionName;

    private final String ASCENDING_FLAG = "ascending";
    private final String DESCENDING_FLAG = "descending";

    private Cache cache = new Cache();

    //hashMap has all the methods that the user added
    private HashMap<ArrayList<Class<?>>, GFMethod> hashMap = new HashMap<>();
    private HashMap<ArrayList<Class<?>>, GFMethod> beforeMap = new HashMap<>();
    private HashMap<ArrayList<Class<?>>, GFMethod> afterMap = new HashMap<>();



    public GenericFunction(String functionName)
    {
        this.functionName = functionName;
    }

    public void addMethod(GFMethod gfMethod)
    {
        cache = new Cache();
        Method[] anonymousMethods = gfMethod.getClass().getDeclaredMethods();
        for (Method method : anonymousMethods)
        {
            int numberOfArgs = method.getParameterCount();
            ArrayList<Class<?>> argTypes = new ArrayList<>();

            if (numberOfArgs > 0)
            {
                for (int i = 0; i < numberOfArgs; i++)
                {
                    argTypes.add(method.getParameterTypes()[i]);

                }
            }

            hashMap.put(argTypes, gfMethod);
        }
    }

    public void addBeforeMethod(GFMethod gfMethod)
    {
        cache = new Cache();
        Method[] anonymousMethods = gfMethod.getClass().getDeclaredMethods();
        for (Method method : anonymousMethods)
        {
            int numberOfArgs = method.getParameterCount();
            ArrayList<Class<?>> argTypes = new ArrayList<>();

            if (numberOfArgs > 0)
            {
                for (int i = 0; i < numberOfArgs; i++)
                {
                    argTypes.add(method.getParameterTypes()[i]);
                }
            }

            beforeMap.put(argTypes, gfMethod);
        }
    }

    public void addAfterMethod(GFMethod gfMethod)
    {
        cache = new Cache();
        Method[] anonymousMethods = gfMethod.getClass().getDeclaredMethods();
        for (Method method : anonymousMethods)
        {
            int numberOfArgs = method.getParameterCount();
            ArrayList<Class<?>> argTypes = new ArrayList<>();

            if (numberOfArgs > 0)
            {
                for (int i = 0; i < numberOfArgs; i++)
                {
                    argTypes.add(method.getParameterTypes()[i]);
                }
            }

            afterMap.put(argTypes, gfMethod);
        }
    }


    public Object call(T ...x){

        Object result = new Object();
        ArrayList<InvokeWrapper> methodsToCache = new ArrayList<>();
        ArrayList<Class<?>> keysToCache = getClasses(x);

        if(cache.containsKey(keysToCache))
        {
            try {
                cache.invokeMethods(keysToCache,x);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        //applicableMethods will have all the applicable methods to the current call that the user needs
        ArrayList<ArrayList<Class<?>>> applicableBeforeMethods = new ArrayList<>();
        ArrayList<ArrayList<Class<?>>> applicableMethods = new ArrayList<>();
        ArrayList<ArrayList<Class<?>>> applicableAfterMethods = new ArrayList<>();


        //getApplicableMethods will fill the applicableMethods ArrayList with all the applicable methods
        getApplicableMethods(applicableBeforeMethods,beforeMap,x);
        getApplicableMethods(applicableMethods,hashMap,x);
        getApplicableMethods(applicableAfterMethods,afterMap,x);

        //Sort the lists by specificity
        sortApplicableMethods(applicableBeforeMethods,ASCENDING_FLAG);
        sortApplicableMethods(applicableMethods,ASCENDING_FLAG);
        sortApplicableMethods(applicableAfterMethods,DESCENDING_FLAG);

        if (applicableMethods.isEmpty())
        {
            ArrayList<Object> args = new ArrayList<>();
            ArrayList<Object> classes = new ArrayList<>();
            for (int i = 0; i < x.length; i++)
            {
                if(x[i] instanceof Object[])
                    args.add(Arrays.deepToString((Object[]) x[i]));
                else
                    args.add(x[i]);

                classes.add(x[i].getClass());
            }

            throw new IllegalArgumentException("\nNo methods for generic function " + functionName
                    + " with args "  + args +
                    "\nof classes " + classes);
        }

        try {


            //Need a foreach, because every before method needs to be called.
            for (ArrayList<Class<?>> beforeMethodClasses : applicableBeforeMethods)
            {
                Class[] classes = new Class[beforeMethodClasses.size()];
                for (int i = 0; i < beforeMethodClasses.size(); i++)
                {
                    classes[i] = beforeMethodClasses.get(i);
                }
                GFMethod MethodToBeCalled = beforeMap.get(beforeMethodClasses);
                Method beforeMethod = MethodToBeCalled.getClass().getDeclaredMethod("call", classes);
                beforeMethod.setAccessible(true);

                beforeMethod.invoke(MethodToBeCalled, x);

                methodsToCache.add(new InvokeWrapper(MethodToBeCalled,beforeMethod));
            }

            //Get the Class[] needed to use in getDeclaredMethod()
            ArrayList<Class<?>> args = applicableMethods.get(0);
            Class[] classes = new Class[args.size()];
            for (int i = 0; i < args.size(); i++)
            {
                classes[i] = args.get(i);
            }

            //Get the GFMethod with the right call function
            GFMethod methodToBeCalled = hashMap.get(args);
            Method finalMethod = methodToBeCalled.getClass().getDeclaredMethod("call", classes);
            finalMethod.setAccessible(true);

            //Invoke the call function, giving the VarArgs that we received
            result = finalMethod.invoke(methodToBeCalled, x);
            methodsToCache.add(new InvokeWrapper(methodToBeCalled,finalMethod));


            //Need a foreach, because every before method needs to be called.
            for (ArrayList<Class<?>> afterMethodClasses : applicableAfterMethods)
            {
                Class[] classesAfter = new Class[afterMethodClasses.size()];
                for (int i = 0; i < afterMethodClasses.size(); i++)
                {
                    classesAfter[i] = afterMethodClasses.get(i);
                }
                GFMethod MethodToBeCalled = afterMap.get(afterMethodClasses);
                Method beforeMethod = MethodToBeCalled.getClass().getDeclaredMethod("call", classesAfter);
                beforeMethod.setAccessible(true);

                beforeMethod.invoke(MethodToBeCalled, x);
                methodsToCache.add(new InvokeWrapper(MethodToBeCalled,beforeMethod));
            }

        } catch (Exception e) {
            System.out.println(e);
        }

        cache.addToCache(keysToCache,methodsToCache);

        return result;
    }

    private void getApplicableMethods(ArrayList<ArrayList<Class<?>>> applicableMethods, HashMap<ArrayList<Class<?>>, GFMethod> map ,T ...x) {

        //Iterates the hashmap
        for (Map.Entry<ArrayList<Class<?>>, GFMethod> entry: map.entrySet()) {
            
            //isApplicable decides if this method will or will not be added to the applicableMethods ArrayList
            boolean isApplicable = true;


            //Iterates the ArrayList that has the classes of the parameters
            for (int i = 0; i < entry.getKey().size(); i++) {

                Class<?> hashMapClass = entry.getKey().get(i);

                //If at least one of the arguments is not applicable, isApplicable turns false so that this method is
                //not added to the applicableMethods ArrayList
                if (!hashMapClass.isAssignableFrom(x[i].getClass())) {
                    isApplicable = false;
                    break;
                }
            }

            //If all the parameters are assignable from the parameters given, it is applicable
            if (isApplicable) {
                applicableMethods.add(entry.getKey());
            }
        }
    }


    public void sortApplicableMethods(ArrayList<ArrayList<Class<?>>> applicableMethods, String flag)
    {

        //Sort the list with the new Comparator<ArrayList<Class<?>>>
        Collections.sort(applicableMethods, (ArrayList<Class<?>> o1, ArrayList<Class<?>> o2) -> {
                for (int i = 0; i < o1.size() && i < o2.size(); i++)
                {
                    //If the classes are equals, take to the next parameter so that it can solve the tie
                    if (o1.get(i).equals(o2.get(i)))
                        continue;

                    if (o1.get(i).isAssignableFrom(o2.get(i)))
                    {
                        if(flag.equals("ascending"))
                            return 1;
                        else
                            return -1;
                    }
                    else
                    {
                        if(flag.equals("ascending"))
                            return -1;
                        else
                            return 1;
                    }
                }
                return 0;
        });

    }

    public ArrayList<Class<?>> getClasses(T... params)
    {
        ArrayList<Class<?>> paramsClasses = new ArrayList<>();

        for(int i = 0 ; i< params.length ; i++)
        {
            paramsClasses.add(params[i].getClass());
        }

        return paramsClasses;
    }
}
