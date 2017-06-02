/**
 * Created by chzq on 2017/6/1.
 */
public class TestMain {
    public static void main(String[] args){
        TestMain testMain = new TestMain();
        testMain.PrintStr();
        testMain.PrintStr();
    }

    public void  PrintStr(){
        System.out.println("hoa d ");
        throw new RuntimeException("eee");
    }
}
