package com.jh.cavy.common.utils.classScan;


import java.util.Set;
import java.util.function.Predicate;

public interface Scan {

    String CLASS_SUFFIX = ".class";

    Set<Class<?>> search(String packageName, Predicate<Class<?>> predicate);

    default Set<Class<?>> search(String packageName){
        return search(packageName,null);
    }

}
