package dev.epxzzy.epxzzysabers.registration;

import java.util.function.Supplier;

public interface ISlaveRegistry {
    <T> Supplier<T> register(String name, Supplier<T>entry);

    void init();
}
