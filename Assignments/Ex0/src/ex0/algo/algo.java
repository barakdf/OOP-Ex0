package ex0.algo;

import ex0.Building;
import ex0.CallForElevator;
import ex0.Elevator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class algo implements ElevatorAlgo {
    final static int UP = 1, DOWN = -1;
    public Building building;
    //public int direction;
    int allo;
    ArrayList<FloorQueue> E_List;
    private boolean[] _firstTime;


    public algo(Building b) {
        this.building = b;
        E_List = new ArrayList<>();
        // this.direction = UP;
        allo = 0;
        _firstTime = new boolean[building.numberOfElevetors()];
        Arrays.fill(_firstTime, true);

        for (int i = 0; i < b.numberOfElevetors(); i++) {
            FloorQueue e = new FloorQueue(this.building.getElevetor(i));
            E_List.add(e);
        }
    }

    @Override
    public Building getBuilding() {
        return this.building;
    }

    @Override
    public String algoName() {
        return "algorithm : boy ";
    }


    @Override
    public int allocateAnElevator(CallForElevator c) {

        System.out.println("get time :" + c.getTime(c.getState()));


        System.out.println("src :" + c.getSrc());
        System.out.println("dest :" + c.getDest());
        ;

        int num = this.building.numberOfElevetors();
        double min = timeToSrc(0, c);
        int best_ele = 0;

        System.out.println("pos :" + this.building.getElevetor(best_ele).getPos());
        System.out.println("state of elevator " + " :" + this.building.getElevetor(best_ele).getState());
        for (int i = 1; i < num; i++) {
            System.out.println("pos of the elevator " + i + " : " + this.building.getElevetor(i).getPos());
            System.out.println("state of elevator " + i + " :" + this.building.getElevetor(i).getState());
            if (min > timeToSrc(i, c)) {
                min = timeToSrc(i, c);
                best_ele = i;
            }
        }
        System.out.println("pos :" + this.building.getElevetor(best_ele).getPos());

        //  if (c.getSrc()<c.getDest() && this.building.getElevetor(best_ele).getState()== 1)
        //    this.building.getElevetor(best_ele).getState()

        this.E_List.get(best_ele).setFloor(c.getSrc());
        this.E_List.get(best_ele).push();
        System.out.println("the chosen elevator is :" + best_ele);
        return best_ele;

    }


    public double timeToSrc(int index, CallForElevator c) {
        Elevator curr = this.getBuilding().getElevetor(index);
        LinkedList<Integer> this_q = E_List.get(index).queue;
        int q_size = this_q.size() + 1;
        int num_of_stops = 0;
        double floor_time =
                (curr.getStartTime() + curr.getTimeForClose() + curr.getStopTime() + curr.getTimeForOpen());
        int num_to_des = 0;
        for (int i = 0; i < E_List.get(index).queue.size(); i++) { // how many stops in the way to the src.
            if (this_q.get(i) < c.getSrc()) {
                num_of_stops++;
            } else if (this_q.get(i) == c.getSrc()) {
                num_of_stops = i;
                num_to_des = i+1;
                break;
            } else {
                num_of_stops = i;
                num_to_des = i;
                break;
            }
        }
        int src_range = Math.abs(curr.getPos() - this_q.get(num_of_stops));
        int des_range = Math.abs(c.getDest() - this_q.get(num_to_des));
        for (int j = num_to_des; j < q_size + 1 ; j++) {
            if (this_q.get(j) < c.getDest()) {
                num_to_des++;
            }
            else if (this_q.get(j) == c.getDest()) {
                num_to_des = j;
                break;
            }
            else {
                num_to_des = j+1;
            }
        }
        double total_src_speed = (src_range/curr.getSpeed());
        double total_des_speed = (des_range/curr.getSpeed());
        double total_src = total_src_speed + (num_of_stops*floor_time);
        double total_des = total_des_speed + (num_to_des*floor_time);

        return (total_src + total_des);

    }

    public void index() {
        Elevator[] arr = new Elevator[this.building.numberOfElevetors()];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = this.building.getElevetor(i);
        }


    }


    @Override
    public void cmdElevator(int elev) {


        if (!_firstTime[elev] && !E_List.get(elev).queue.isEmpty()) {
            Elevator curr = this.building.getElevetor(elev);
            int pos = curr.getPos();
            int first_F = E_List.get(elev).queue.getFirst();
            int last_F = E_List.get(elev).queue.getLast();
            if (curr.getState() == Elevator.LEVEL) {
                if (E_List.get(elev).queue.size() == 1) {
                    curr.goTo(first_F);
                } else {
                    E_List.get(elev).queue.removeFirst();
                    curr.goTo(last_F);
                    curr.stop(first_F);
                }
            }

        } else {
            _firstTime[elev] = false;
            int min = this.building.minFloor(), max = this.building.maxFloor();
            for (int i = 0; i < this.building.numberOfElevetors(); i++) {
                Elevator curr = this.getBuilding().getElevetor(elev);
                int floor = rand(min, max);
                curr.goTo(floor);
            }
        }
    }

    public static int rand(int min, int max) {
        if (max < min) {
            throw new RuntimeException("ERR: wrong values for range max should be >= min");
        }
        int ans = min;
        double dx = max - min;
        double r = Math.random() * dx;
        ans = ans + (int) (r);
        return ans;
    }
}
