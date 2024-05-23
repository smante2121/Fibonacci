package prog03;

public class LinearFib extends Fib{
    public double fib(int n){
        double a, b, c;
        a=0;
        b=1;
        c=0;


        for (int i= 0; i < n; i++){
            c= b;
            b=b +a;
            a= c;

        }
        return a;
    }


    public double O(int n){
        return n;
    }
}
