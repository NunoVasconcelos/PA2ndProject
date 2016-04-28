import ist.meic.pa.GenericFunctions.GenericFunction;
import ist.meic.pa.GenericFunctions.GFMethod;

import java.util.Arrays;


public class Test {

    public static void main(String[] args)
    {
        final GenericFunction add = new GenericFunction("add");

        add.addMethod(new GFMethod(){
            Object call(Integer a, Integer b){
                return a + b;
            }
        });

    }

    public static void println(Object obj) {
        if (obj instanceof Object[]) {
            System.out.println(Arrays.deepToString((Object[])obj));
        } else {
            System.out.println(obj);
        }
    }

}
