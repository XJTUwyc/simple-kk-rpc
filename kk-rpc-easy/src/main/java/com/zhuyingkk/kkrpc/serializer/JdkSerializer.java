package com.zhuyingkk.kkrpc.serializer;

import java.io.*;

/**
 * jdk 序列化器
 */
public class JdkSerializer implements Serializer {
    @Override
    public <T> byte[] serialize(T object) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        try {
            // 将对象写入输出流（序列化）
            objectOutputStream.writeObject(object);
            objectOutputStream.flush();
            // 返回序列化后的字节数组
            return outputStream.toByteArray();
        } finally {
            // 关闭输出流，释放资源
            objectOutputStream.close();
        }

    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> type) throws IOException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        try {
            return (T) objectInputStream.readObject();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            objectInputStream.close();
        }
    }
}
