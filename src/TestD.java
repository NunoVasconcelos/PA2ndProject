import java.util.*;
import ist.meic.pa.GenericFunctions.*;

public class TestD {

    /*
    public static void main(String args[]) {

        final GenericFunction add = new GenericFunction("add");

        add.addMethod(new GFMethod() {
            Object call(Integer a, Integer b) {
                return a + b;
            }});

        add.addMethod(new GFMethod() {
            Object call(Object[] a, Object[] b) {
                Object[] r = new Object[a.length];
                for (int i = 0; i < a.length; i++) {
                    r[i] = add.call(a[i], b[i]);
                }
                return r;
            }});

        println(add.call(1, 3));
        println(add.call(new Object[] { 1, 2, 3 }, new Object[] { 4, 5, 6 }));
        println(add.call(new Object[] { 1, 2 }, 3));

    }

    public static void println(Object obj) {

        if (obj instanceof Object[]) {
            System.err.println(Arrays.deepToString((Object[])obj));
        } else {
            System.err.println(obj);
        }
    }
    */
}

/*
4
[5, 7, 9]
Exception in thread "main" java.lang.IllegalArgumentException:
No methods for generic function add with args [[1, 2], 3]
of classes [class [Ljava.lang.Object;, class java.lang.Integer]
	at ist.meic.pa.GenericFunctions.GenericFunction.computeEffectiveMethod(GenericFunction.java:90)
	at ist.meic.pa.GenericFunctions.GenericFunction.call(GenericFunction.java:99)
	at TestD.main(TestD.java:25)

 */