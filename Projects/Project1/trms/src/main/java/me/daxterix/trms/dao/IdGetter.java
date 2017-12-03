package me.daxterix.trms.dao;

import java.io.Serializable;

public interface IdGetter<T> {
    Serializable getId(T obj);
}
