package ist.meic.pa.GenericFunctions;


import java.lang.reflect.Method;
import java.util.ArrayList;

import static javafx.scene.input.KeyCode.T;

public class GenericFunction {

    //TODO arrayList de GFMethods, em que cada um vai corresponder a um call, pois ccada GFMethod é um objecto com um call
//    ArrayList<Method> methods = new ArrayList<>();
    ArrayList<GFMethod> methods = new ArrayList<>();

    public GenericFunction(String functionName)
    {

    }

    //TODO aproveitar do professor e adicionar o gfMethod em vez do método call mesmo
    public void addMethod(GFMethod gfMethod)
    {
//        Method[] method = gfMethod.getClass().getDeclaredMethods();
//        methods.add(method[0]);
        methods.add(gfMethod);

    }

    public void addBeforeMethod(GFMethod gfMethod)
    {

    }

    public void addAfterMethod(GFMethod gfMethod)
    {

    }

    //TODO ainda falta ver o que vai estar nesta função
    public Object call(Object a, Object b)
    {
        return call(a,b);
    }

    public void printFunctionsDeclared()
    {
//        for (Method method : methods)
//            System.out.println(method.toString());

        for (GFMethod method : methods)
            System.out.println(method.toString());
    }

    //TODO usar introspecção para escolher o método certo e depois invocá-lo
    //TODO primeiro verificar se existem métodos aplicáveis, e fazer uma lista destes
    //TODO depois, tendo a lista dos métodos aplicáveis, ordenar os métodos por especificidade
    //TODO depois de tê-los ordenados, ver qual é o método que vai ser chamado
    //TODO depois de saber o método, invocar o before/after com esse método
}
