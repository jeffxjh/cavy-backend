package com.jh.cavy.common.utils.classScan;

import java.util.Set;
import java.util.function.Predicate;

public class ClassScannerUtils {
    public static Set<Class<?>> searchClasses(String packageName){
        return searchClasses(packageName,null);
    }

    public static Set<Class<?>> searchClasses(String packageName, Predicate<Class<?>> predicate){
        return ScanExecutor.getInstance().search(packageName,predicate);
    }

}
