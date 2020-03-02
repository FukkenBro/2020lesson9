import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

public class MyBST {

    private static class Node<K extends Comparable<K>, V> {
        K key;
        V value;
        int height;
        Node<K, V> left;
        Node<K, V> right;

        Node(K k, V v) {
            key = k;
            value = v;
            height = 1;
        }

        @Override
        public String toString() {
            return "{" +
                    "K=" + key +
                    " V=" + value +
                    '}';
        }

        public K getKey() {
            return key;
        }
    }

    static class AVLTree<K extends Comparable<K>, V> {

        Node<K, V> root;
        static int size = 0;

        public AVLTree() {
            this.root = null;
            size = 0;
        }

        private int height(Node<K, V> node) {
            if (node == null) {
                return 0;
            }
            return node.height;
        }

        private Node<K, V> rotateRight(Node<K, V> y) {
            Node<K, V> x = y.left;
            Node<K, V> T2 = x.right;

            // Правое вращение
            x.right = y;
            y.left = T2;

            // Переопределение высот
            y.height = Math.max(height(y.left), height(y.right)) + 1;
            x.height = Math.max(height(x.left), height(x.right)) + 1;

            // Возвращаем новый корень
            return x;
        }

        private Node<K, V> rotateLeft(Node<K, V> x) {
            Node<K, V> y = x.right;
            Node<K, V> T2 = y.left;

            // Левое вращение
            y.left = x;
            x.right = T2;

            // Переопределение высот
            x.height = Math.max(height(x.left), height(x.right)) + 1;
            y.height = Math.max(height(y.left), height(y.right)) + 1;

            // Возвращаем новый корень
            return y;
        }

        private int getBalanceFactor(Node<K, V> node) {
            if (node == null) {
                return 0;
            }
            return height(node.left) - height(node.right);
        }

        public Node<K, V> put(K key, V value) {
            return root = insert(root, key, value);
        }

        private Node<K, V> insert(Node<K, V> node, K key, V value) {
            //Действие №1

            // проверка на пустое дерево #1
            if (node == null) {
                size++;
                return (new Node<K, V>(key, value));
            }

            // Когда ключ меньше корневого - идём влево
            if (node.key.compareTo(key) > 0) {
                node.left = insert(node.left, key, value);
            }
            // Когда ключ больше корневого - идём вправо
            else if (node.key.compareTo(key) < 0) {
                node.right = insert(node.right, key, value);
            }
            // Когда ключ встречен
            else {
                //предотвращаем появление дубликатов
                node.value = value;
                return node;
            }

            //Действие №2 переопределение высоты узла
            node.height = 1 + Math.max(height(node.left), height(node.right));

            //Действие №3 проверка на баланс и балансировка дерева
            int balanceFactor = getBalanceFactor(node);

            //LL
            if (balanceFactor > 1 && node.left.key.compareTo(key) > 0) {
                return rotateRight(node);
            }

            // RR
            if (balanceFactor < -1 && node.right.key.compareTo(key) < 0) {
                return rotateLeft(node);
            }

            // LR
            if (balanceFactor > 1 && node.left.key.compareTo(key) < 0) {
                node.left = rotateLeft(node.left);
                return rotateRight(node);
            }

            // RL
            if (balanceFactor < -1 && node.right.key.compareTo(key) > 0) {
                node.right = rotateRight(node.right);
                return rotateLeft(node);
            }
            return node;
        }

        public void traverse(Node<K, V> node) {
            if (node != null) {
                System.out.print(node.toString() + "  ");
                traverse(node.left);
                traverse(node.right);
            }
        }

        public Node<K, V> getMin() {
            return minValueNode(root);
        }

        private Node<K, V> minValueNode(Node<K, V> node) {
            Node<K, V> tmp = node;

            while (tmp.left != null) {
                tmp = tmp.left;
            }

            return tmp;
        }

        public Node<K, V> getMax() {
            return maxValueNode(root);
        }

        private Node<K, V> maxValueNode(Node<K, V> node) {
            Node<K, V> tmp = node;

            while (tmp.right != null) {
                tmp = tmp.right;
            }

            return tmp;
        }

        public Node<K, V> remove(K k) {
            return root = deleteNode(root, k);
        }

        public Node<K, V> deleteNode(Node<K, V> node, K key) {
            //Действие №1

            // проверка на пустое дерево #1
            if (node == null) {
                return node;
            }

            // Когда искомый ключ меньше корневого - идём влево
            if (node.key.compareTo(key) > 0) {
                node.left = deleteNode(node.left, key);
            }

            // Когда искомый ключ меньше корневого - идём вправо
            else if (node.key.compareTo(key) < 0) {
                node.right = deleteNode(node.right, key);
            }

            // Когда искомый ключ встречен
            else {
                // Когда встреченый узел не имеет\имеет одного потомка
                if ((node.left == null) || (node.right == null)) {

                    Node<K, V> tmp = null;

                    if (tmp == node.left) {
                        tmp = node.right;
                    } else {
                        tmp = node.left;
                    }

                    // Без потомков
                    if (tmp == null) {
                        tmp = node;
                        node = null;
                        size--;
                    }
                    // Один потомок
                    else {
                        // Скопировать сожержимое в потомка (не пустого)
                        node = tmp;
                        size--;
                    }
                } else {
                    /* Когда встречен узел с двумя потомками:
                    принимаем потомка (наименьший в правом поддереве)
                     */

                    Node<K, V> temp = minValueNode(node.right);
                    //Перенести поддерево потомка в текущий узел
                    node.key = temp.key;
                    node.value = temp.value;

                    //Удалить потомка
                    node.right = deleteNode(node.right, temp.key);
                }
            }

            // проверка на пустое дерево #2
            if (node == null) {
                return node;
            }

            //Действие №2 переопределение высоты узла
            node.height = Math.max(height(node.left), height(node.right)) + 1;

            //Действие №3 проверка на баланс и балансировка дерева
            int balanceFactor = getBalanceFactor(node);

            // LL
            if (balanceFactor > 1 && getBalanceFactor(node.left) >= 0)
                return rotateRight(node);

            // LR
            if (balanceFactor > 1 && getBalanceFactor(node.left) < 0) {
                node.left = rotateLeft(node.left);
                return rotateRight(node);
            }

            // RR
            if (balanceFactor < -1 && getBalanceFactor(node.right) <= 0)
                return rotateLeft(node);

            // RL
            if (balanceFactor < -1 && getBalanceFactor(node.right) > 0) {
                node.right = rotateRight(node.right);
                return rotateLeft(node);
            }

            return node;
        }

        public void clear() {
            root = null;
            size = 0;
        }

        public Node<K, V> getByKey(K key) {
            return searchKey(root, key);
        }

        private Node<K, V> searchKey(Node<K, V> node, K key) {

            if (node == null || node.key == key)
                return node;

            if (node.key.compareTo(key) > 0)
                return searchKey(node.left, key);

            return searchKey(node.right, key);

        }

        public boolean containsKey(K key) {
            return searchKey(root, key) != null;
        }

        public Node<K, V> getByValue(V value) {
            ArrayList<Node<K, V>> list = new ArrayList<>();
            searchValue(root, value, list);
            for (int i = 0; i < list.size(); i++) {
                Node<K, V> tmp = list.get(i);
                if (tmp.value == value) {
                    return tmp;
                }
            }
            return null;
        }

        private void searchValue(Node<K, V> node, V value, ArrayList<Node<K, V>> list) {
            if (list.size() >= 1) {
                return;
            }
            if (node != null) {
                if (node.value == value) {
                    list.add(node);
                }
                searchValue(node.left, value, list);
                searchValue(node.right, value, list);
            }
        }

        public boolean containsValue(V value) {
            ArrayList<Node<K, V>> list = new ArrayList<>();
            searchValue(root, value, list);
            return (list.size() == 1);
        }

        public HashSet<K> getKeySet() {
            HashSet<K> result = new HashSet<>();
            keySet(root, result);
            return result;
        }

        public LinkedList<V> getValues() {
            LinkedList<V> result = new LinkedList<>();
            valueSet(root, result);
            return result;
        }

        private void keySet(Node<K, V> node, HashSet<K> set) {
            if (node != null) {
                set.add(node.key);
                keySet(node.left, set);
                keySet(node.right, set);
            }
        }

        private void valueSet(Node<K, V> node, LinkedList<V> list) {
            if (node != null) {
                list.add(node.value);
                valueSet(node.left, list);
                valueSet(node.right, list);
            }
        }

    }
}