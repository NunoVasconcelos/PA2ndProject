package ist.meic.pa.GenericFunctions;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;


public class GenericFunction<T> implements Comparable<GenericFunction<T>>{


    //hashMap has all the methods that the user added
    HashMap<ArrayList<Class>, GFMethod> hashMap = new HashMap<>();

    //applicableMethods will have all the applicable methods to the current call that the user needs
    ArrayList<ArrayList<Class>> applicableMethods = new ArrayList<>();

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
            ArrayList<Class> argTypes = new ArrayList<>();

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

    }

    public void addAfterMethod(GFMethod gfMethod)
    {

    }

    public Object call(T ...x){

        Object result = new Object();

        //Will fill the applicableMethods ArrayList with all the applicable methods
        getApplicableMethods(x);


        ArrayList<Class<?>> args = new ArrayList<>();
        Class[] classes = new Class[x.length];

        for (int i = 0; i < x.length; i++)
        {
            args.add(x[i].getClass());
            classes[i] = x[i].getClass();
        }


        try {
            Method finalMethod = hashMap.get(args).getClass().getDeclaredMethod("call", classes);
            finalMethod.setAccessible(true);
            result = finalMethod.invoke(hashMap.get(args), x[0], x[1]);
        } catch (Exception e) {
            //TODO tratar a excepção de não haver métodos
            e.printStackTrace();
        }
        return result;
    }

    public void getApplicableMethods(T ...x) {

        //Iterates the hashmap
        for (Map.Entry<ArrayList<Class>, GFMethod> entry: hashMap.entrySet()) {
            
            //isApplicable decides if this method will or will not be added to the applicableMethods ArrayList
            boolean isApplicable = true;


            //Iterates the ArrayList that has the classes of the parameters
            for (int i = 0; i < entry.getKey().size(); i++) {

                //TODO add protection to the case of different number of arguments

                //If at least one of the arguments is not applicable, isApplicable turns false so that this method is
                //not added to the applicableMethods ArrayList
                if (!entry.getKey().get(i).getClass().isAssignableFrom(x[i].getClass())) {
                    isApplicable = false;
                }
            }

            //If all the parameters are assignable from the parameters given, it is applicable
            if (isApplicable)
            {
                System.out.println("É aplicável!");
                applicableMethods.add(entry.getKey());
                System.out.println(entry.getKey().get(0));
                System.out.println(entry.getKey().get(1));
            }
        }


    }

    @Override
    public int compareTo(GenericFunction<T> o) {
        return 0;
    }

    public static Comparator<GenericFunction> applicableMethodsComparator = new Comparator<GenericFunction>() {
        @Override
        public int compare(GenericFunction o1, GenericFunction o2) {
            return 0;
        }
    };


    //TODO usar introspecção para escolher o método certo e depois invocá-lo
    //TODO primeiro verificar se existem métodos aplicáveis, e fazer uma lista destes
    //TODO depois, tendo a lista dos métodos aplicáveis, ordenar os métodos por especificidade
    //TODO depois de tê-los ordenados, ver qual é o método que vai ser chamado
    //TODO depois de saber o método, invocar o before/after com esse método
}
