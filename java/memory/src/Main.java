public class Main {
  public static void main(String[] args) {
    int localValue = 5;
    calculate(localValue);
    System.out.println(localValue);

  }

  public static void calculate(int calcValue) {
    calcValue = calcValue * 100;
  }

}