package ist.meic.pa.GenericFunctionsExtended;

import ist.meic.pa.GenericFunctions.GFMethod;

import java.lang.reflect.Method;


public class InvokeWrapper {

    private GFMethod gfMethod;
    private Method method;
    private String whichMethod;

    public GFMethod getGfMethod() {
        return gfMethod;
    }

    public Method getMethod() {
        return method;
    }

    public String getWhichMethod(){
        return whichMethod;
    }

    public InvokeWrapper(GFMethod gfMethod, Method method, String whichMethod)
    {
        this.gfMethod = gfMethod;
        this.method = method;
        this.whichMethod = whichMethod;
    }
}
