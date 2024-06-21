package extra;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;

public class FiltersComparators {

    public static void main(String[] args) {
        // Comparators

        SortedSet<SortClass> ss =  new TreeSet<>();
        ss.add(new SortClass(100));
        ss.add(new SortClass(0));
        ss.add(new SortClass(10));
        System.out.println(ss);

        SortedSet<SortClass> ss2 = new TreeSet<>((obj1, obj2) -> obj1.num1 < obj2.num1 ? -1 : 1);
        ss2.add(new SortClass(100));
        ss2.add(new SortClass(0));
        ss2.add(new SortClass(10));
        System.out.println(ss2);

        Map<String , Integer> map1 = new HashMap<String , Integer>(){{
            put("1",1);
            put("2",2);
        }};

        System.out.println(map1);

        System.out.println(map1.getOrDefault("3",10));
        map1.computeIfAbsent("3",(k)->0);

        Map<Integer, List<Integer>> graph = new HashMap<>();
        graph.put(2,new ArrayList<Integer>(){{add(2);add(3);}});

        graph.computeIfAbsent(2,(key)-> new ArrayList<>()).add(10);
        graph.computeIfAbsent(1,(key)-> new ArrayList<>()).add(10);

        System.out.println(graph);


        // Filters
        Predicate<Integer> predicate = (num1)->num1%2==0; // even numbers

        List<Integer> filteredArr = graph.keySet().stream().filter(predicate).collect(Collectors.toList());


        for(Map.Entry<Integer,List<Integer>> entry:graph.entrySet()){
            System.out.print(entry.getKey()+ " ");
            System.out.println(entry.getValue());
        }



        // Functional Interfaces
        Function<Integer,Integer> function = (a)->a;
        BiFunction<Integer,Integer,Integer> bifunction = (a,b)->a+b;
        Consumer<Integer> consumer = (val)->{
            System.out.println(val);
        };

        Supplier<String> supplier = ()->"hello";

    }

    static class SortClass implements Comparable<SortClass>{

        Integer num1;

        SortClass(Integer a){
            num1 = a;
        }

        @Override
        public int compareTo(SortClass s2){
            return num1<s2.num1 ?-1:1;
        }

        @Override
        public String toString() {
            return "SortClass{" +
                    "num1=" + num1 +
                    '}';
        }
    }


}
