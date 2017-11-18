package daxterix.bank.dao;

public abstract class Closer {
    public static void close(AutoCloseable closeable) {
        try {
            if (closeable != null)
                closeable.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}