package datastructure.trietree;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: GuoFei
 * @Date: 2021/09/23/18:59
 * @Description:
 */
public class TrieTreeMain {
    public static void main(String[] args) {
        TrieTree<Integer> tree = new TrieTree<>();
        tree.add("cat",1);
        tree.add("dog",2);
        tree.add("catalog",3);
        tree.add("cast",4);
        tree.add("郭fei",5);
        System.out.println(tree.size());
        System.out.println(tree.contains("castee"));
        System.out.println(tree.get("cat"));
        System.out.println(tree.get("郭fei"));
        System.out.println(tree.startWith("c"));
        System.out.println(tree.startWith("ca"));
        System.out.println(tree.startWith("cas"));
        System.out.println(tree.startWith("郭"));
        System.out.println(tree.startWith("郭e"));
        System.out.println(tree.remove("cast"));
        System.out.println(tree.get("cast"));
        System.out.println(tree.size());
    }
}
