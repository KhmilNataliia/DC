import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

class FistWay{
    enum temple {
        guan_in, guan_jan
    }

    private ArrayList<temple> buddhists;
    private ArrayList<Integer> buddhistsForce;
    private int buddhistNum;

    public FistWay() {
        Random random = new Random();
        buddhistNum = random.nextInt(80) + 20;
        buddhists = new ArrayList<temple>();
        buddhistsForce = new ArrayList<Integer>();
        for (int i = 0; i < buddhistNum; i++) {
            if (random.nextBoolean()) {
                buddhists.add(temple.guan_jan);
            } else {
                buddhists.add(temple.guan_in);
            }
            buddhistsForce.add(random.nextInt(400) + 100);
        }
    }

    private class fight extends RecursiveTask<Integer> {
        Integer buddhist1, buddhist2, winner;

        public Integer getWinner() {
            return winner;
        }

        public fight(Integer Buddhist1, Integer Buddhist2) {
            buddhist1 = Buddhist1;
            buddhist2 = Buddhist2;
        }

        private Integer doFight() {
            if (buddhistsForce.get(buddhist1) > buddhistsForce.get(buddhist2)) {
                return buddhist1;
            }
            if (buddhistsForce.get(buddhist1) == buddhistsForce.get(buddhist2)) {
                Random random = new Random();
                if (random.nextBoolean()) {
                    return buddhist1;
                } else {
                    return buddhist2;
                }
            }
            return  buddhist2;
        }

        @Override
        protected Integer compute() {
            if (buddhist1 == buddhist2) {
                return buddhist1;
            }
            if (buddhist1 + 1 == buddhist2) {
                return doFight();
            }

            Integer middle = (buddhist1 + buddhist2)/2;

            fight Fight1 = new fight(buddhist1,middle);
            Fight1.fork();

            fight Fight2 = new fight(middle + 1, buddhist2);
            Fight2.fork();

            buddhist1 = Fight1.join();
            buddhist2 = Fight2.join();

            winner = doFight();

            return winner;
        }
    }

    public void runFights() {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        fight fights = new fight(0, buddhistNum - 1);
        forkJoinPool.invoke(fights);
        Integer win = fights.getWinner();
        System.out.println(buddhistNum + " buddhists took part in competitions;");
        System.out.println("winner : buddhist number " + win + " from temple " + buddhists.get(win));
    }
}