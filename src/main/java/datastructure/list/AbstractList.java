package datastructure.list;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: GuoFei
 * @Date: 2021/09/22/16:57
 * @Description: 虽然对外暴露了接口，但是有些东西并不想暴露给外界，全部放在接口里面并不是好
 * 再抽象一层出来，为了放他们共同的代码，但是又不想暴露给外界的方法
 */
public abstract class AbstractList<E> implements List<E>{
    /**
     * 元素的数量
     */
    protected int size;

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size==0;
    }

    /**
     * 是否包含某个元素
     * @param element
     * @return
     */
    @Override
    public boolean contains(E element) {
        return indexOf(element)!=ELEMENT_NOT_FOUND;
    }

    /**
     * 添加元素到尾部
     * @param element
     */
    @Override
    public void add(E element) {
        add(size,element);
    }


    /**
     * 越界检查
     * @param index
     */
    protected void outOfBounds(int index) {
        throw new IndexOutOfBoundsException("Index:" + index + ", Size:" + size);
    }

    /**
     * 范围检查
     * @param index
     */
    protected void rangeCheck(int index) {
        if (index < 0 || index >= size) {
            outOfBounds(index);
        }
    }

    /**
     * 添加的时候范围检查
     * @param index
     */
    protected void rangeCheckForAdd(int index) {
        if (index < 0 || index > size) {
            outOfBounds(index);
        }
    }
}
