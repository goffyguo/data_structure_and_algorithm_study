package datastructure.trietree;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: GuoFei
 * @Date: 2021/09/23/18:55
 * @Description: TrieTree 字典树（前缀树，单词查找树）,字符串搜索只跟字符串的长度有关
 * /**
 *  * 需求：如何判断一堆不重复的字符串是否是某个前缀开头
 *  * {dog,cat,does}
 *
 *  */

public class TrieTree<V> {

    /**
     * 存储元素的数量
     */
    private int size;

    /**
     * 根节点
     */
    private Node<V> root;

    public int size() {
        return this.size;

    }

    public boolean isEmpty() {
        return false;
    }

    public void clear() {
        size = 0;
        root = null;
    }

    public V get(String key) {
        Node<V> node = node(key);
        return node == null ? null : node.value;
    }

    /**
     * 是否包含这个key
     *
     * @param key
     * @return
     */
    public boolean contains(String key) {
        return node(key) != null;
    }


    /**
     * 添加的时候需要注意的地方：
     * 1、如果我们添加的时候已经有这个字符串了，那我们就只需要覆盖就好了
     * 2、如果是已经有的字符串比新添加的这个长，那我们把新添加的字符串遍历覆盖，然后把最后一个字符的 word 置为true
     * 3、如果是已经有的字符串比新添加的这个短，那么
     *
     * @param key
     * @param value
     */
    public V add(String key, V value) {
        keyCheck(key);
        // 从根节点开始
        if (root == null) {
            root = new Node<>(null);
        }
        Node<V> node = root;
        int length = key.length();
        // 遍历新添加的key
        for (int i = 0; i < length; i++) {
            char c = key.charAt(i);
            // 取出子节点
            boolean emptyChildren = node.children == null;
            Node<V> childNode = emptyChildren ? null : node.children.get(c);
            // 如果子节点为null,就创建新的节点，然后把创建出来的新的节点放进之前的node中
            if (childNode == null) {
                childNode = new Node<>(node);
                childNode.character = c;
                node.children = emptyChildren ? new HashMap<>() : node.children;
                node.children.put(c, childNode);
            }
            node = childNode;
        }
        // 如果可以来的这里，就说明for循环已经走完，已经添加完
        // 已经存在这个单词就覆盖
        if (node.word) {
            V oldValue = node.value;
            node.value = value;
            return oldValue;
        }
        // 可以来到这里，说明是新增一个节点
        node.word = true;
        node.value = value;
        size++;
        return null;
    }


    /**
     * 删除的时候我们考虑如下几点（明确一点，我们删除的是单词并不是前缀）
     * 1、如果单词存在才删，不存在就不删
     * 2、如果要删除的单词后面还有节点，就不能直接删除，因为他还是后面单词的一部分，这个时候只需要把这个要删单词的最后一位变成不是一个单词的最后字符就好了（word为false）
     * 3、如果没有节点，还需要看待删单词的最后一个节点有没有其他的子节点（或者他是某个单词的最后一个字符【word为true】），也不能删除
     * 4、如果上述条件都过了，我们就删，以此往上推即可
     *
     * @param key
     * @return
     */
    public V remove(String key) {
        // 找到最后一个子节点
        Node<V> node = node(key);
        // 如果是单词结尾，不用做任何处理
        if (node == null || !node.word) {
            return null;
        }
        size--;
        V oldValue = node.value;
        //如果还有子节点
        if (node.children != null && !node.children.isEmpty()) {
            node.word = false;
            node.value = null;
            return oldValue;
        }
        //如果没有子节点
        Node<V> parent = null;
        while ((parent = node.parent) != null) {
            parent.children.remove(node.character);
            if (parent.word || !parent.children.isEmpty()) {
                break;
            }
            node = parent;
        }
        return oldValue;
    }

    /**
     * 是否以它为前缀
     *
     * @param prefix
     * @return
     */
    public boolean startWith(String prefix) {
        //keyCheck(prefix);
        return node(prefix) != null;
    }

    /**
     * 根据key查找value
     *
     * @param key
     * @return
     */
    private Node<V> node(String key) {
        //if (root == null) return null;
        keyCheck(key);

        Node<V> node = this.root;
        int length = key.length();
        for (int i = 0; i < length; i++) {
            //这里之所以不这样写的原因就是因为node（root）可能为空,所以给Node增加get的构造方法，然后在返回值中判断，如果为空先new一个
            //Node<V> node1 = node.getChildren().get(c);
            //            if (node1==null) return null;
            //Node<V> vNode = node().children.get(c);
            // 当然也可以这么写，如果这么写就需要现在这里判断下
            if (node == null || node.children == null || node.children.isEmpty()) {
                return null;
            }
            char c = key.charAt(i);
            node = node.children.get(c);
        }
        return node;
    }

    private void keyCheck(String key) {
        if (key == null || key.length() == 0) {
            throw new IllegalArgumentException("key must not be empty");
        }
    }



    /**
     * 此处的重点是考虑如何设计这个node节点
     * //todo
     * 每一个node就是一个数据，用红色表示每个单词的结尾，
     * 我们不应该存储这一整个单词，因为从上开始，每一个单词已经存储过了，没必要再存储一边，但是我们总应该有一个字符 character
     *
     * @param <V>
     */
    private static class Node<V> {

        // 父节点
        Node<V> parent;
        // 子节点
        HashMap<Character, Node<V>> children;
        Character character;

        V value;
        // 是否为单词的结尾（是否为一个完成的单词）
        boolean word;

        public Node(Node<V> parent) {
            this.parent = parent;
        }
    }


}
