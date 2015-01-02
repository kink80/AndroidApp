package myapp.com.myapplication.data;

import android.content.Context;

import java.util.List;

/**
 * Created by stecl on 01/01/15.
 */
public interface DataProvider {

    public void rateUp(WordBean word);
    public void rateDown(WordBean word);
    public void load();
    public WordBean getNext();
    public void rateCurrent(String rating);
}
