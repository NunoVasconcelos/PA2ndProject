package ist.meic.pa.GenericFunctions;


import java.util.Comparator;
import java.util.StringJoiner;

public class GFMethod {

    final GenericFunction add = new GenericFunction("add");

    GFMethod GFMethodInstanceInteger = new GFMethod(){
        public Object call(Integer integer1, Integer integer2){
            return integer1 + integer2;
        }
    };

    GFMethod GFMethodInstanceNumber = new GFMethod(){
        public Object call(Number number1, Number number2){
            System.out.println("number");
            return "";
        }
    };

    GFMethod GFMethodInstanceString = new GFMethod(){
        public Object call(String string){
            System.out.println("string");
            return "";
        }
    };

    GFMethod GFMethodInstanceObjectArray = new GFMethod(){
        public Object call(Object[] objects1,Object[] objects2){
            Object[] r = new Object[objects1.length];
            for (int i = 0; i < objects1.length; i++) {
                r[i] = add.call(objects1[i], objects2[i]);
            }
            return r;
        }
    };


    GFMethod GFMethodInstanceObject = new GFMethod(){
        public Object call(Object object){
            System.out.println("object");
            return "";
        }
    };

//    public <T extends GenericFunction> Object GFMethod()
//    {
//
//
//    }

//    public static void main(String[] args)
//    {
//        Integer[] ints = {1,2,3,4};
//        Character[] chars = {'a', 'b', 'c'};
//
//        print(ints);
//        print(chars);

        //System.out.println(max(23,1,24));
        //System.out.println(max("dfdf","vdfb","fdfdf"));
//    }

    //generic returns type
//    public static <T extends Comparable<T>> T max(T a , T b, T c)
//    {
//        T m = a;//assuming that a is the max
//        if(b.compareTo(a)> 0)
//            m = b;
//        if(c.compareTo(m) > 0)
//            m = c;
//
//        return m;
//    }

    //Example of Generic method
//    public static <T> void print(T[] args) //t is generic so anytype of array will be proceed
//    {
//        for(T var: args)
//            System.out.printf("%s", var);
//        System.out.println();
//    }

//    Using overloading
//    public static void print(Integer[] var)
//    {
//        for(Integer x : var)
//            System.out.printf("%s", x);
//        System.out.println();
//    }
//
//    public static void print(Character[] var)
//    {
//        for(Character x : var)
//            System.out.printf("%s", x);
//        System.out.println();
//    }
}
