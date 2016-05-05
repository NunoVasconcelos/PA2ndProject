import ist.meic.pa.GenericFunctions.GenericFunction;
import ist.meic.pa.GenericFunctions.GFMethod;

import java.util.Arrays;


public class Test {

    public static void main(String[] args)
    {

        final GenericFunction add = new GenericFunction("add");

        add.addMethod(new GFMethod(){
            Object call(int a, Integer b){
                return a + b;
            }
            Object call(Object[] a, Object b) {
                Object[] ba = new Object[a.length];
                Arrays.fill(ba, b);
                return add.call(a, ba);
            }
        });

        add.addMethod(new GFMethod() {
            Object call(String a, Object b) {
                return add.call(Integer.decode(a), b);
            }});

        add.addMethod(new GFMethod() {
            Object call(Object a, String b) {
                return add.call(a, Integer.decode(b));
            }});

        add.printFunctionsDeclared();

        int a = 0;
        System.out.println(int.class.isAssignableFrom(Number.class));


    }

    public static void println(Object obj) {
        if (obj instanceof Object[]) {
            System.out.println(Arrays.deepToString((Object[])obj));
        } else {
            System.out.println(obj);
        }
    }

}
