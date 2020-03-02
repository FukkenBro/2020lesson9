import java.util.Random;

public class Main {
    public static final Random RANDOM = new Random();

    public static void main(String[] args) {

        /* Построим AVL-сбалансированное дерево со след структурой
                30
               /  \
             20   40
            /  \     \
           10  25    50
          /
         1
        */
        MyBST.AVLTree tree = new MyBST.AVLTree();
        tree.put(10, "test");
        tree.put(20, "test");
        tree.put(30, "test");
        tree.put(40, "test");
        tree.put(50, "test");
        tree.put(25, "test");
        tree.put(1, "test");

        System.out.println("Put() : ");
        tree.traverse(tree.root);
        System.out.println(" Size : " + MyBST.AVLTree.size);

        System.out.println("Put() с заменой V :");
        tree.put(10, "10");
        tree.put(20, "20");
        tree.put(30, "30");
        tree.put(40, "40");
        tree.put(50, "50");
        tree.put(25, "25");
        tree.put(1, "1");
        tree.put(999, "unique");
        tree.traverse(tree.root);
        System.out.println(" Size : " + tree.size);

        System.out.println("MIN is : " + tree.getMin());

        System.out.println("MAX is : " + tree.getMax());

        for (int i = 0; i < 100; i++) {
            tree.put(RANDOM.nextInt(100), "xxx");
        }
        tree.traverse(tree.root);
        System.out.println("\n Size : " + tree.size);

        System.out.println("\nContains key 50 : " + tree.containsKey(50));
        System.out.println("Get by Key 50 : " + tree.getByKey(50));
        System.out.println("\nGet by Value \"xxx\" : " + tree.getByValue("xxx"));
        System.out.println("Get by Value \"test\" : " + tree.getByValue("test"));
        System.out.println("\nContains \"unique\" : " + tree.containsValue("unique"));
        System.out.println("Get by Value \"unique\" : " + tree.getByValue("unique"));

        System.out.println("\nKey set : " + tree.getKeySet());
        System.out.println("Values: " + tree.getValues());

        for (int i = 0; i < 300; i++) {
            tree.remove(RANDOM.nextInt(100));
        }
        System.out.println();
        tree.traverse(tree.root);
        System.out.println("\n Size : " + MyBST.AVLTree.size);

        tree.clear();
        tree.traverse(tree.root);
        System.out.println("\n Size : " + MyBST.AVLTree.size);


    }
}
