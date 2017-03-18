package sample.hello;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.Terminated;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;



//New way of akka main instead of micro kernel
public class MainSelf {

  public static void main(String[] args) {
    ActorSystem system = ActorSystem.create("System");
    ActorRef helloWorldActor = system.actorOf(Props.create(HelloWorld.class), "helloWorld");
    system.actorOf(Props.create(HelloWorldTerminator.class, helloWorldActor), "terminator");
  }

  public static class HelloWorldTerminator extends UntypedActor {

    private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);
    private final ActorRef ref;

    public HelloWorldTerminator(ActorRef ref) {
      this.ref = ref;
      getContext().watch(ref);
    }

    @Override
    public void onReceive(Object msg) {
      if (msg instanceof Terminated) {
        log.info("{} has terminated, shutting down system", ref.path());
        getContext().system().terminate();
      } else {
        unhandled(msg);
      }
    }

  }
}
