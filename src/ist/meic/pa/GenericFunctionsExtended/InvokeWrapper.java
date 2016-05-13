package ist.meic.pa.GenericFunctionsExtended;

import ist.meic.pa.GenericFunctions.GFMethod;

import java.lang.reflect.Method;


public class InvokeWrapper {

    private GFMethod gfMethod;
    private Method method;

    public GFMethod getGfMethod() {
        return gfMethod;
    }

    public void setGfMethod(GFMethod gfMethod) {
        this.gfMethod = gfMethod;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public InvokeWrapper(GFMethod gfMethod, Method method)
    {
        this.gfMethod = gfMethod;
        this.method = method;
    }
}
