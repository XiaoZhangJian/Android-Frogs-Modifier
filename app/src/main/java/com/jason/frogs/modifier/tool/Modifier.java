package com.jason.frogs.modifier.tool;

import com.jason.frogs.modifier.model.Frogs;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * @author Jason
 * @time 2018/1/25
 */
public class Modifier {
    private File file;

    Modifier(Builder builder) {
        this.file = builder.file;
    }

    public static class Builder {
        private File file;


        public Builder() {
        }

        public Builder(Modifier tool) {
            this.file = tool.file;
        }


        public Builder setFile(File file) {
            this.file = file;
            return this;
        }


        public Modifier build() {
            return new Modifier(this);
        }

    }


    public int readInt(Frogs frogs) {
        RandomAccessFile r = null;
        try {
            r = new RandomAccessFile(file, "r");
            r.seek(frogs.offset);
            ByteBuffer buffer = ByteBuffer.wrap(new byte[4]);
            buffer.order(ByteOrder.LITTLE_ENDIAN);
            buffer.asIntBuffer().put(r.readInt());
            buffer.order(ByteOrder.BIG_ENDIAN);
            return buffer.getInt();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            CloseTool.closeQuietly(r);
        }
        return -1;
    }

    public void writeInt(Frogs frogs) {
        RandomAccessFile r = null;
        try {
            r = new RandomAccessFile(file, "rw");
            r.seek(frogs.offset);

            ByteBuffer buffer = ByteBuffer.wrap(new byte[4]);
            buffer.order(ByteOrder.BIG_ENDIAN);
            buffer.asIntBuffer().put(frogs.value);
            buffer.order(ByteOrder.LITTLE_ENDIAN);
            r.writeInt(buffer.getInt());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            CloseTool.closeQuietly(r);
        }
    }


}
