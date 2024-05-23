package prog03;
import prog02.UserInterface;
import prog02.GUI;

/**
 *
 * @author vjm
 */
public class Main {
  /** Use this variable to store the result of each call to fib. */
  public static double fibn;

  /** Determine the time, in microseconds, it takes to calculate the
      n'th Fibonacci number.
      @param fib an object that implements the Fib interface
      @param n the index of the Fibonacci number to calculate
      @return the time for the call to fib(n)
  */
  public static double time (Fib fib, int n) {
    // Get the current time in nanoseconds.
    long start = System.nanoTime();
    // Calculate the n'th Fibonacci number.  Store the
    // result in fibn.
    fibn = fib.fib(n);
    // Get the current time in nanoseconds.
    long end = System.nanoTime(); // fix this
    // Return the difference between the end time and the
    // start time divided by 1000.0 to convert to microseconds.
    return (end-start)/1000.0; // fix this
  }

  /** Determine the average time in microseconds it takes to calculate
      the n'th Fibonacci number.
      @param fib an object that implements the Fib interface
      @param n the index of the Fibonacci number to calculate
      @param ncalls the number of calls to average over
      @return the average time per call
  */
  public static double averageTime (Fib fib, int n, int ncalls) {
    // Copy the contents of Main.time here.
    long start = System.nanoTime();
    // Add a "for loop" line before the line with the call to fib.fib
    // to make that line run ncalls times.
    for (int i=0; i < ncalls; i++){
      fibn = fib.fib(n);
    }
    long end = System.nanoTime();
    // Modify the return value to get the average.
    return (end-start)/1000.0/ncalls;
  }

  /** Determine the time in microseconds it takes to to calculate the
      n'th Fibonacci number.  Average over enough calls for a total
      time of at least one second.
      @param fib an object that implements the Fib interface
      @param n the index of the Fibonacci number to calculate
      @return the time it it takes to compute the n'th Fibonacci number
  */
  public static double accurateTime (Fib fib, int n) {
    // Get the time in microseconds for one call.
    double t = averageTime(fib, n, 1);
    // If the time is more than a second, return it.
    if (t> Math.pow(10,6) ){
      return t;
    }
    // Estimate the number of calls that would add up to one second.
    // Use   (int)(YOUR EXPESSION)   so you can save it into an int variable.
    int numcalls = (int)( (Math.pow(10,6))/t ); // fix this
    // Get the average time using averageTime above and that many
    // calls and return it.
    return averageTime(fib, n, numcalls);
  }



  //private static UserInterface ui = new TestUI("Fibonacci experiments");
  private static UserInterface ui = new GUI("Fibonacci experiments");




  /** Get a non-negative integer from the using using ui.
      If the user enters a negative integer, like -2, say
      "-2 is negative...invalid" and ask again.
      If the user enters a non-integer, like abc, say
      "abc is not an integer" and ask again.
      If the user clicks cancel, return -1.
      @return the non-negative integer entered by the user or -1 for cancel.
  */
  static int getInteger () {
    String s = ui.getInfo("Enter n");
    if (s == null)
      return -1;
    try {
        int n= Integer.parseInt(s);
        if (n < 0 ){
            ui.sendMessage(n +" is a negative integer. Enter a positive.");
        }
        else {
            return n;
        }
    }catch (NumberFormatException numberFormatException) {
        ui.sendMessage(s + " is not a integer.");
    }
    return -1;
  }

  public static void doExperiments (Fib fib) {
    System.out.println("doExperiments " + fib);
   // doExperiments(new ExponentialFib());
    boolean firstTime=true;
   // UserInterface ui= new GUI("Fibonacci experiment");
      while (true) {
        int n = getInteger();
        if (n == -1) {
          ui.sendMessage("Cancelled");
          return;
        }
          if (firstTime){
              double actualTime= accurateTime(fib, n);
              fib.recordConstant(n, actualTime);
            ui.sendMessage("fib("+ n+ ") = "+ fibn+ " in "+ actualTime + " microseconds");
          }
          if (!firstTime){
            double estimateTime= fib.estimateTime(n);
            ui.sendMessage("The estimated time is " + estimateTime + " microseconds." );
            if (estimateTime > 3.6e9){
              ui.sendMessage("Estimated time is more than a hour. Are you are you want to proceed?");
              String[] options= {"Yes", "No"};
              int userChoice= ui.getCommand(options);
              switch (userChoice){
                case 0:
                    double actualTime= accurateTime(fib, n);
                    fib.recordConstant(n, actualTime);
                    double percentageError = ((estimateTime - actualTime) / estimateTime) * 100;
                    ui.sendMessage("fib(" + n + ") = " + fibn + ", actual time: " + actualTime + " microseconds, percentage error: " + percentageError + "%");
                case 1:
                  return ;
              }
            }
              double actualTime= accurateTime(fib, n);
              fib.recordConstant(n, actualTime);
              double percentageError = ((estimateTime - actualTime) / estimateTime) * 100;
              ui.sendMessage("fib(" + n + ") = " + fibn + ", actual time: " + actualTime + " microseconds, percentage error: " + percentageError + "%");
            }

          firstTime= false;
        }
      }


  public static void doExperiments () {
    String[] userchoices= {"ExponentialFib", "LinearFib", "LogFib", "ConstantFib", "MysteryFib", "Exit"};
    while (true){
      int choice=ui.getCommand(userchoices);
      switch (choice){
        case 0:
          doExperiments(new ExponentialFib());
          break;
        case 1:
          doExperiments(new LinearFib());
         break;
        case 2:
        doExperiments(new LogFib());
         break;
       case 3:
         doExperiments(new ConstantFib());
        break;
        case 4:
         doExperiments(new MysteryFib());
         break;
        case 5:
          return;
      }

    }

    // Give the user a choice instead, in a loop, with the option to exit.

  }

  static void labExperiments () {

    // Create (Exponential time) Fib object and test it.
    //Fib efib = new ExponentialFib();
    //Fib efib = new LinearFib();
    //Fib efib = new LogFib();
    Fib efib = new ConstantFib();
    System.out.println(efib);
    for (int i = 0; i < 11; i++)
      System.out.println(i + " " + efib.fib(i));
    
    // Determine running time for n1 = 20 and print it out.
    int n1 = 20;
    double time1 = accurateTime(efib, n1);
    System.out.println("n1 " + n1 + " time1 " + time1 + " microseconds");
    
    // Calculate constant:  time = constant times O(n).
    double c = time1 / efib.O(n1);
    System.out.println("c " + c);

    // Estimate running time for n2=30.
    int n2 = 30;
    double time2est = c * efib.O(n2);
    System.out.println("n2 " + n2 + " estimated time " + time2est + " microseconds");
    
    // Calculate actual running time for n2=30.
    double time2 = accurateTime(efib, n2);
    System.out.println("n2 " + n2 + " actual time " + time2 + " microseconds");

    // Estimate how long ExponentialFib.fib(100) would take.
    int n3=100;
    double time3est= c * efib.O(n3);;
    double years= time3est/ (1e6 * 60 * 60 * 24 *365);
    System.out.println("Estimated time to computer fib(100): " + years + " years");
  }

  /**
   * @param args the command line arguments
   */
  public static void main (String[] args) {
    labExperiments();
    doExperiments();
  }
}
