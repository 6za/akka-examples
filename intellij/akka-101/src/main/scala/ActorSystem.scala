
import akka.actor.typed.{Behavior,ActorSystem}
import akka.actor.typed.scaladsl.Behaviors

object TypedAkkaHierarchy {
  // 3 - hierarchy

  trait ShoppingCartMessage

  case class AddItem(item: String) extends ShoppingCartMessage

  case class RemoveItem(item: String) extends ShoppingCartMessage

  case object ValidateCart extends ShoppingCartMessage

  //Erlang-ish way
  def shoppingBehavior(items: Set[String]): Behavior[ShoppingCartMessage] =
    Behaviors.receiveMessage[ShoppingCartMessage] {
      case AddItem(item) =>
        println(s"Adding $item to cart")
        shoppingBehavior(items + item)
      case RemoveItem(item) =>
        println(s"Removing $item to cart")
        shoppingBehavior(items - item)
      case ValidateCart =>
        println("The cart is good" + items)
        Behaviors.same
    }

  val rootOnlineStoreActor = ActorSystem(
    Behaviors.setup[ShoppingCartMessage] { ctx =>
      //create children here
      ctx.spawn(shoppingBehavior(Set()), "6zaCodeShoppingCart")
      // no behavior on root actor
      Behaviors.empty
    }, "onlineStore"
  )
  //It doesn't seems to process the messages, somehting is missing.

  //rootOnlineStoreActor ! AddItem("ball")
  //rootOnlineStoreActor ! AddItem("car")
  //rootOnlineStoreActor ! ValidateCart

 // Thread.sleep(10000)
  def main(args: Array[String]): Unit = {

  }
}
