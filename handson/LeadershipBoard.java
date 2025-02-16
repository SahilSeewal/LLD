package handson;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.PriorityBlockingQueue;

public class LeadershipBoard {
    PriorityBlockingQueue<Integer> pq = new PriorityBlockingQueue<>(5, new SampleComparator());
    ConcurrentSkipListSet<Integer> p = new ConcurrentSkipListSet<>(new SampleComparator());
    List<Integer> ls = new CopyOnWriteArrayList<Integer>();
    ConcurrentSkipListMap<String,List<List<Integer>>> mp = new ConcurrentSkipListMap<>();

    public void addUser(int score) {
        pq.offer(score);
        ls.add(score);
        ls.sort(new SampleComparator());
    }

    public void addCar(String car, Integer start, Integer end) {
        if(mp.containsKey(car)) {
            Integer lb = findLowerBound(mp.get(car), start);
        if(lb < 0 && mp.get(car).getLast().get(1) >= start) {
            System.out.println(car+" already booked.");
            return;
        } else if(lb < 0 && mp.get(car).getLast().get(1) < start) {
            mp.get(car).add(new CopyOnWriteArrayList<>(Arrays.asList(start, end)));
            mp.get(car).sort(new SampleComparator2());
            System.out.println(car+" car booked.");
            return;
        }
        
        else{
            List<Integer> ls2 = mp.get(car).get(lb);
        if((lb-1>=0 && mp.get(car).get(lb-1).get(1) >= start) 
        || ls2.get(0) == start || ls2.get(0)<=end) {
            System.out.println(car+" already booked.");
        return;
        }
        }
        
    } else{
        mp.put(car, new CopyOnWriteArrayList<>());
    }
        
        mp.get(car).add(new CopyOnWriteArrayList<>(Arrays.asList(start, end)));
        mp.get(car).sort(new SampleComparator2());
        System.out.println(car+" car booked.");
    } 

    public void searchCar(String car, Integer start, Integer end) {
        if(mp.containsKey(car)) {
            Integer lb = findLowerBound(mp.get(car), start);
            if(lb < 0) return;

            List<Integer> ls = mp.get(car).get(lb);
            if(ls.get(0) == start && ls.get(1) == end) {
                System.out.println(car+" found.");
            }
        }
    }

    public Integer findLowerBound(List<List<Integer>> interval, Integer start) {
        Integer ans = -1, low = 0, high = interval.size()-1;
        while(low<=high) {
            Integer mid = (low+high)/2;

            if(interval.get(mid).get(0) >= start){
                ans = mid;
                high = mid-1;
            } else {
                low = mid + 1; 
            }
        }
        return ans;
    }

    public List<Integer> getTopFiveUser() {
        return ls.stream().toList().reversed().subList(0, Math.min(5, ls.size()));
    }

    private class SampleComparator implements Comparator<Integer> {

        @Override
        public int compare(Integer o1, Integer o2) {
            return o1.intValue() - o2.intValue();
        }
        
    }

    private class SampleComparator2 implements Comparator<List<Integer>> {

        @Override
        public int compare(List<Integer> o1, List<Integer> o2) {
            if(o1.get(0) == o2.get(0)) {
                return o1.get(1) - o2.get(1);
            } 
            return o1.get(0) - o2.get(0);
        }
        
    }
}
