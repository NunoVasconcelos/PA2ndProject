package ist.meic.pa.GenericFunctions;

import java.lang.reflect.Method;
import java.util.*;


public class GenericFunction<T> {

    //hashMap has all the methods that the user added
    private HashMap<ArrayList<Class<?>>, GFMethod> hashMap = new HashMap<>();
    private HashMap<ArrayList<Class<?>>, GFMethod> beforeMap = new HashMap<>();
    private HashMap<ArrayList<Class<?>>, GFMethod> afterMap = new HashMap<>();

    //applicableMethods will have all the applicable methods to the current call that the user needs
    private ArrayList<ArrayList<Class<?>>> applicableBeforeMethods = new ArrayList<>();
    private ArrayList<ArrayList<Class<?>>> applicableMethods = new ArrayList<>();
    private ArrayList<ArrayList<Class<?>>> applicableAfterMethods = new ArrayList<>();

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

        //getApplicableMethods will fill the applicableMethods ArrayList with all the applicable methods
        getApplicableMethods(applicableBeforeMethods,beforeMap,x);
        getApplicableMethods(applicableMethods,hashMap,x);
        getApplicableMethods(applicableAfterMethods,afterMap,x);

        //Sort the lists by specificity
        sortApplicableMethods(applicableBeforeMethods);
        sortApplicableMethods(applicableMethods);
        sortApplicableMethods(applicableAfterMethods);

        applicableBeforeMethods.addAll(applicableMethods);
        applicableBeforeMethods.addAll(applicableAfterMethods);



        //To check if the list is sorted
        for (ArrayList<Class<?>> k:
                applicableMethods) {
            int i = 0;
            while(i < k.size())
            {
                if (i == 1)
                {
                    System.out.println(k.get(i));
                }
                else
                {
                    System.out.print(k.get(i) + " ");
                }


                i++;
            }


        }



//        ArrayList<Class<?>> args = new ArrayList<>();
//        Class[] classes = new Class[x.length];
//
//        for (int i = 0; i < x.length; i++)
//        {
//            args.add(x[i].getClass());
//            classes[i] = x[i].getClass();
//        }
//
//
//        try {
//            Method finalMethod = hashMap.get(args).getClass().getDeclaredMethod("call", classes);
//            finalMethod.setAccessible(true);
//            result = finalMethod.invoke(hashMap.get(args), x[0], x[1]);
//        } catch (Exception e) {
//            //TODO tratar a excepção de não haver métodos
//            e.printStackTrace();
//        }
        return result;
    }

    public void getApplicableMethods(ArrayList<ArrayList<Class<?>>> applicableMethods, HashMap<ArrayList<Class<?>>, GFMethod> map ,T ...x) {

        //Iterates the hashmap
        for (Map.Entry<ArrayList<Class<?>>, GFMethod> entry: map.entrySet()) {
            
            //isApplicable decides if this method will or will not be added to the applicableMethods ArrayList
            boolean isApplicable = true;


            //Iterates the ArrayList that has the classes of the parameters
            for (int i = 0; i < entry.getKey().size(); i++) {

                //TODO add protection to the case of different number of arguments

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

    //TODO usar introspecção para escolher o método certo e depois invocá-lo
    //TODO primeiro verificar se existem métodos aplicáveis, e fazer uma lista destes
    //TODO depois, tendo a lista dos métodos aplicáveis, ordenar os métodos por especificidade
    //TODO depois de tê-los ordenados, ver qual é o método que vai ser chamado
    //TODO depois de saber o método, invocar o before/after com esse método
}
