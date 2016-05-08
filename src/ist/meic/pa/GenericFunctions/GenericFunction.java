package ist.meic.pa.GenericFunctions;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;


public class GenericFunction<T> {

    HashMap<ArrayList<Class<?>>, GFMethod> hashMap = new HashMap<>();
    Method finalMethod;


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
                    argTypes.add(method.getParameterTypes()[i]);
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
        int numberOfArgs = x.length;
        ArrayList<Class<?>> args = new ArrayList<>();
        Class[] classes = new Class[numberOfArgs];

        for (int i = 0; i < numberOfArgs; i++)
        {
            args.add(x[i].getClass());
            classes[i] = x[i].getClass();
        }

        try {
            finalMethod = hashMap.get(args).getClass().getDeclaredMethod("call", classes);
            finalMethod.setAccessible(true);
            result = finalMethod.invoke(hashMap.get(args), x[0], x[1]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    //TODO usar introspecção para escolher o método certo e depois invocá-lo
    //TODO primeiro verificar se existem métodos aplicáveis, e fazer uma lista destes
    //TODO depois, tendo a lista dos métodos aplicáveis, ordenar os métodos por especificidade
    //TODO depois de tê-los ordenados, ver qual é o método que vai ser chamado
    //TODO depois de saber o método, invocar o before/after com esse método
}
