package datastructure.lru;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: GuoFei
 * @Date: 2021/11/15/16:03
 * @Description: 继承JDK（HashMap）自带的 removeEldestEntry()方法
 */
public class LRUCacheLinkedHashMap {
    private LinkedHashMap<Integer, Integer> map;
    private final int capacity;

    public LRUCacheLinkedHashMap(int capacity) {
        this.map = new LinkedHashMap<Integer, Integer>(capacity, 0.75f, true){
            @Override
            protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
                //return super.removeEldestEntry(eldest);
                return size()>capacity;
            }
        };
        this.capacity = capacity;
    }
    public int get(int key){
        return map.getOrDefault(key, -1);
    }
    public void put(int key, int value){
        map.put(key, value);
    }

    public static void main(String[] args) {
        LRUCacheLinkedHashMap cache = new LRUCacheLinkedHashMap(2);
        cache.put(1,1);
        cache.put(2,2);
        System.out.println(cache.get(1));
        cache.put(3, 3);
        System.out.println(cache.get(2));
        cache.put(4, 4);
        System.out.println(cache.get(1));
        System.out.println(cache.get(3));
        System.out.println(cache.get(4));
    }

}
