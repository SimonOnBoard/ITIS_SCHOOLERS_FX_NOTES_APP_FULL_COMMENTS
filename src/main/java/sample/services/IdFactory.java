package sample.services;

public class IdFactory {
    public static Long currentId = 0L;

    public static void setCurrentId(Long id) {
        currentId = id;
    }

    public static Long getNextId() {
        currentId += 1;
        return currentId;
    }
}
