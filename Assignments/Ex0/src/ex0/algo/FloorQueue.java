package ex0.algo;

import ex0.CallForElevator;
import ex0.Elevator;

import java.util.LinkedList;
import java.util.Queue;

public class FloorQueue {
    LinkedList<Integer> queue = new LinkedList<>();
    int floor;
    int pos;
    int direction ;
    public FloorQueue(Elevator e) {
        this.pos = e.getPos();
        this.direction = e.getState();

    }

    public void copy(LinkedList<Integer> E) {
        E.addAll(queue);
    }

    public void setFloor(int new_src) {
        this.floor = new_src;
    }

    public boolean removeObject(int o) {
//        if (findIndex(o) == -1) {
//            System.out.println("false! " + "src " + o + " queue " + queue.toString());
//            return false;
//        }
        queue.remove(findIndex(o));
        return true;
    }

    public int findIndex(int o) {
        for (int j = 0; j < queue.size(); j++) {
            if (queue.get(j) == o) {
                return j;
            }
        }
        return -1;
    }

    public void removeTheFirst() {
        queue.remove(0);
    }

    public static int range(int floor, int pos) {
        return Math.abs(floor - pos);
    }



    public int getDirection(){
        return this.direction;
    }

    public void setDirection(int direction){
        this.direction = direction;
    }


    public void push(int src) {
        System.out.println("first" + queue.toString());
        if (!queue.contains(src)) {
            if (!queue.isEmpty()) {
                boolean added = false;
                for (int i = 0; i < queue.size() && !added; i++) {
                    if (range(src, pos) < range(queue.get(i), pos)) {
                        queue.add(i, src);
                        added = true;
                    }
                }
                if (!added) {
                    queue.addLast(src);
                }
            } else {
                queue.addFirst(src);
            }
        }

        System.out.println("queue " + queue.toString());
    }



}
