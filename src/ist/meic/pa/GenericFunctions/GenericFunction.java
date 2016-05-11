package ist.meic.pa.GenericFunctions;

import java.lang.reflect.Method;
import java.util.*;


public class GenericFunction<T> {

    //hashMap has all the methods that the user added
    private HashMap<ArrayList<Class<?>>, GFMethod> hashMap = new HashMap<>();
    private HashMap<ArrayList<Class<?>>, GFMethod> beforeMap = new HashMap<>();
    private HashMap<ArrayList<Class<?>>, GFMethod> afterMap = new HashMap<>();



    public GenericFunction(String functionName)
    {

    }

    //TODO aproveitar do professor e adicionar o gfMethod em vez do método call mesmo
    public void addMethod(GFMethod gfMethod)
    {
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

        //applicableMethods will have all the applicable methods to the current call that the user needs
        ArrayList<ArrayList<Class<?>>> applicableBeforeMethods = new ArrayList<>();
        ArrayList<ArrayList<Class<?>>> applicableMethods = new ArrayList<>();
        ArrayList<ArrayList<Class<?>>> applicableAfterMethods = new ArrayList<>();


        //getApplicableMethods will fill the applicableMethods ArrayList with all the applicable methods
        getApplicableMethods(applicableBeforeMethods,beforeMap,x);
        getApplicableMethods(applicableMethods,hashMap,x);
        getApplicableMethods(applicableAfterMethods,afterMap,x);

        //Sort the lists by specificity
        sortApplicableMethods(applicableBeforeMethods);
        sortApplicableMethods(applicableMethods);
        sortApplicableMethods(applicableAfterMethods);

        if (applicableMethods.isEmpty())
            throw new IllegalArgumentException();
        applicableBeforeMethods.add(applicableMethods.get(0));
        applicableBeforeMethods.addAll(applicableAfterMethods);


        try {


            //Need a foreach, because every before method needs to be called.
//            for (ArrayList<Class<?>> beforeMethodClasses : applicableBeforeMethods)
//            {
//                Class[] classes = new Class[beforeMethodClasses.size()];
//                for (int i = 0; i < beforeMethodClasses.size(); i++)
//                {
//                    classes[i] = beforeMethodClasses.get(i);
//                }
//                GFMethod beforeMethodToBeCalled = beforeMap.get(beforeMethodClasses);
//                Method beforeMethod = beforeMethodToBeCalled.getClass().getDeclaredMethod("call", classes);
//                beforeMethod.setAccessible(true);
//
//                //TODO falta aqui result
//            }



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

        } catch (Exception e) {
            System.out.println(e);
        }
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


    public void sortApplicableMethods(ArrayList<ArrayList<Class<?>>> applicableMethods)
    {

        //Sort the list with the new Comparator<ArrayList<Class<?>>>
        Collections.sort(applicableMethods, (ArrayList<Class<?>> o1, ArrayList<Class<?>> o2) -> {
                for (int i = 0; i < o1.size() && i < o2.size(); i++)
                {
                    //If the classes are equals, take to the next parameter so that it can solve the tie
                    if (o1.get(i).equals(o2.get(i)))
                        continue;

                    if (o1.get(i).isAssignableFrom(o2.get(i)))
                        return 1;
                    else
                        return -1;
                }
                return 0;
        });

    }

    //TODO after tem de ser organizado ao contrário
    //TODO falta tratar a excepção
}
