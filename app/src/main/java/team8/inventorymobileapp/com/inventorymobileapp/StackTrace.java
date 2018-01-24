package team8.inventorymobileapp.com.inventorymobileapp;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by Trang Pham on 1/24/2018.
 */

public class StackTrace {

    public static String trace(Exception ex) {

        StringWriter outStream = new StringWriter();
        ex.printStackTrace(new PrintWriter(outStream));
        return outStream.toString();
    }
}
