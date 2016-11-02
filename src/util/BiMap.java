/*
* Justin Sybrandt
*
* Description:
* Inspired by a google class which can be found here.
* https://google.github.io/guava/releases/19.0/api/docs/com/google/common/collect/BiMap.html
*
* This map stores pairs where both the key and value must be unique in their own sets.
* Data is duplicated, but this allows for constant time lookups in both directions.
*
* Important Values:
* forwardsMap - mapping from key to value
* backwardsMap - mapping from value to key
*
* */

package util;

import java.util.HashMap;
import java.util.Set;

public class BiMap<K,V> {
    HashMap<K, V> forwardsMap = new HashMap<>();
    HashMap<V,K> backwardsMap = new HashMap<>();

    public void put(K key, V val){
        forwardsMap.put(key,val);
        backwardsMap.put(val,key);
    }

    public void putValue(V val, K key){
        forwardsMap.put(key,val);
        backwardsMap.put(val,key);
    }
    public V get(K key){
        return forwardsMap.get(key);
    }
    public K getValue(V val){
        return backwardsMap.get(val);
    }
    public int size(){return forwardsMap.size();}
    public Set<K> keySet(){
        return forwardsMap.keySet();
    }
    public Set<V> values(){
        return backwardsMap.keySet();
    }
}
