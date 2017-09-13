import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * Created by chzq on 2017/6/1.
 */
public class TestMain {
    public static void main(String[] args) throws Exception{
        TestMain t = new TestMain();
        t.testThread();
    }

    public static void testList(){
        List<String> list = new ArrayList<String>();
        list.add("A");
        list.add("B");
        List<String> subList = list.subList(0,list.size());
        subList.add("C");
        System.out.println(list.size());
        System.out.println(subList.size());

    }

    public void  PrintStr(){
        System.out.println("hoa d ");
        throw new RuntimeException("eee");
    }


    // 模拟100米跑步
    public  void testThread() throws Exception{

        //用于记录同步发枪
        CountDownLatch blatch = new CountDownLatch(1);

        // 用于记录5个运动员是否全部跑完
        CountDownLatch elatch = new CountDownLatch(5);

        // 5个线程用于5个运动员跑步
        ExecutorService  service = Executors.newFixedThreadPool(5);

        // 存放5个运动员的跑步结果
        List<Future<Integer>> futures = new ArrayList <Future <Integer>>(5);
        for(int i=0;i<5;i++) {
            futures.add(service.submit(new RunThread(blatch,elatch)));
        }
        // 开枪-------------跑步开始
        blatch.countDown();
        // 等待所以运动员跑完
        elatch.await();

        int count = 0;
        for(Future<Integer> future :futures){
            count += future.get();
        }
        System.out.println("5位运动员的平均成绩" + count/5);
    }

    class RunThread implements Callable<Integer>{
        private CountDownLatch blatch;
        private CountDownLatch elatch;
        RunThread(CountDownLatch blatch,CountDownLatch elatch){
            this.blatch = blatch;
            this.elatch = elatch;
        }

        public Integer call() throws Exception {

            // 每个运动员等待开枪
            blatch.await();
            // 跑步阶段
            int score =  new Random().nextInt(25);
            TimeUnit.MILLISECONDS.sleep(score);
            System.out.println("跑完了..........................用时" + score);
            // 跑步结束
            elatch.countDown();
            return score;
        }
    }
}
