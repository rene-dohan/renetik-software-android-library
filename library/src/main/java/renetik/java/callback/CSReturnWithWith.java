package renetik.java.callback;

public interface CSReturnWithWith<Type, WithA, WithB> {
    Type invoke(WithA withA, WithB withB);
}