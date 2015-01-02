package myapp.com.myapplication.data;

/**
 * Created by stecl on 01/01/15.
 */
public class EmptyWord extends WordBean {

    public EmptyWord() {
        super();
    }

    @Override
    public boolean isValid() {
        return false;
    }
}
