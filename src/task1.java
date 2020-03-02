import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static java.util.stream.Collectors.toMap;

public class task1 {

    /* 1.) Есть документ со списком URL:
    https://drive.google.com/open?id=1wVBKKxpTKvWwuCzqY1cVXCQZYCsdCXTl
    Вывести топ 10 доменов которые встречаются чаще всего. В документе могут встречается пустые и недопустимые строки. */

    public static final Integer TOP = 10;

    public static void main(String[] args) throws FileNotFoundException {
        HashMap<String, Integer> domains = new HashMap<>();
        File file = new File("src\\domains.txt");
        Scanner sc = new Scanner(file);
        while (sc.hasNextLine()) {
            String s = sc.nextLine().split("/", 2)[0];
            if (s.contains("m.")) {
                s = s.replace("m.", "");
            }
            if (s.contains("www.")) {
                s = s.replace("www.", "");
            }
            Integer count = domains.get(s);
            if (count != null) {
                count++;
            } else {
                count = 1;
            }
            domains.put(s, count);
        }
        getTop(reverseSortMap(domains), TOP);
    }

    public static HashMap<String, Integer> reverseSortMap(HashMap<String, Integer> input) {
        HashMap<String, Integer> sorted = input
                .entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(
                        toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                                LinkedHashMap::new));

        return sorted;
    }

    public static void getTop(HashMap<String, Integer> sorted, int topN) {
        ArrayList<String> keyList = new ArrayList<>(sorted.keySet());
        ArrayList<Integer> valueList = new ArrayList<>(sorted.values());
        System.out.println("TOP " + TOP + " DOMAIN NAMES:");
        for (int i = 0; i < topN; i++) {
            int N = i + 1;
            System.out.printf("%-5s", "#" + N);
            System.out.printf("%-30s", keyList.get(i));
            System.out.printf("%4s%n", valueList.get(i));
        }

    }
}
