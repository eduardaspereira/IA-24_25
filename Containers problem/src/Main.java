import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception{
        BestFirst s = new BestFirst();
        Scanner sc = new Scanner(System.in);
        Iterator<BestFirst.State> it = s.solve(new Container(sc.nextLine()), new Container(sc.nextLine()));

        if(it == null) System.out.println("no solution found");
        else {
            while(it.hasNext()) {
                BestFirst.State i = it.next();
                System.out.println(i);
                if(!it.hasNext()) System.out.println((int) i.getK());
            }
        }
        sc.close();

    }
}