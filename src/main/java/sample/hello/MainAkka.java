package sample.hello;

//New way of akka main instead of micro kernel
public class MainAkka {

  public static void main(String[] args) {
    akka.Main.main(new String[] { HelloWorld.class.getName() });
  }
}
