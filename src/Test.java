import ist.meic.pa.GenericFunctions.GenericFunction;
import ist.meic.pa.GenericFunctions.GFMethod;

import java.util.Arrays;


public class Test {

    public static void main(String[] args) {

        final GenericFunction add = new GenericFunction("add");


        add.addMethod(new GFMethod(){
            Object call(Number a, Number b){
                return a.intValue() + b.intValue();
            }
        });
        add.addMethod(new GFMethod(){
            Object call(Integer a, Number b){
                return a + b.intValue();
            }
        });
        add.addMethod(new GFMethod(){
            Object call(Number a, Integer b){
                return a.intValue() + b;
            }
        });

        add.addMethod(new GFMethod(){
            Object call(Integer a, Integer b){
                return a + b;
            }
        });



        add.call(1, 3);

//        println(add.call(1, 3));
//        println(add.call(new Object[] { 1, 2, 3 }, new Object[] { 4, 5, 6 }));
//        println(add.call(new Object[] { new Object[] { 1, 2 }, 3 },
//                new Object[] { new Object[] { 3, 4 }, 5 }));

    }

    public static void println(Object obj) {
        if (obj instanceof Object[]) {
            System.out.println(Arrays.deepToString((Object[])obj));
        } else {
            System.out.println(obj);
        }
    }

}
