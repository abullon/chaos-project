package com.fiveadvantages.chaos.util;

import io.advantageous.boon.core.reflection.BeanUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class Util {

    public static<K,T> List<K> CopyCollectionProperties(Collection<T> origen, Class<K> claseDestino){
        Iterator<T> it = origen.iterator();
        List<K> destino = new ArrayList<K>();
        K objDestino = null;
        while(it.hasNext()) {
            try {
                objDestino = claseDestino.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            BeanUtils.copyProperties(it.next(),objDestino);
            destino.add(objDestino);
        }

        return destino;
    }
}
