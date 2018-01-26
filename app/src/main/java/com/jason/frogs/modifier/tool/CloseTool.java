package com.jason.frogs.modifier.tool;

import java.io.Closeable;
import java.io.IOException;

/**
 * @author Jason
 * @time 2018/1/26
 */

public class CloseTool {

    public CloseTool() {}

    static void closeQuietly(Closeable closeable){
        if (null != closeable){
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
