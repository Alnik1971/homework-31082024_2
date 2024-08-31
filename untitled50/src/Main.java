import java.sql.SQLOutput;
import java.util.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static final Map<Integer, Integer> sizeToFreg = new HashMap<>();
    public static void main(String[] args) throws InterruptedException {
        List<Thread> threads = new ArrayList<Thread>();
        for (int i = 0; i < 1000; i++) {
            Thread thread = new Thread(() -> {
                String s = generateRoute("RLRFR", 100);
                int f = (int) s.chars().filter(ch -> ch == 'R').count();

                synchronized (sizeToFreg) {
                    sizeToFreg.put(f, sizeToFreg.getOrDefault(f, 0) + 1);
                }
            });
            threads.add(thread);
            thread.start();
        }
        for(Thread thread : threads) {
            thread.join();
        }
        Map.Entry<Integer, Integer> max = sizeToFreg.entrySet().stream().max(Map.Entry.comparingByValue()).get();

        System.out.printf("Самое частое кол-во повторений %d (встретилось %d раз)%n", max.getKey(), max.getValue());

        System.out.println("Другие размеры: ");
        sizeToFreg.entrySet().stream().sorted(Map.Entry.comparingByValue()).forEach(el -> System.out.println(" - " + el.getKey() + " (" + el.getValue() + " раз)"));


    }
    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }
}
