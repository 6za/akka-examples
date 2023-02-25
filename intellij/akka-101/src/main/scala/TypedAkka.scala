import akka.actor.typed.{ActorSystem, Behavior}
import akka.actor.typed.scaladsl.Behaviors

import scala.concurrent.Future


object TypedAkka {
  // 1 - typed messages & actors
  trait ShoppingCartMessage
  case class AddItem(item: String) extends ShoppingCartMessage
  case class RemoveItem(item: String) extends ShoppingCartMessage
  case object ValidateCart extends ShoppingCartMessage

  // Creating a hierarchy of a message family

  val shoppingRootActor = ActorSystem(
    Behaviors.receiveMessage[ShoppingCartMessage]{
      message: ShoppingCartMessage =>
        message match {
          case AddItem(item) => println(s"Adding $item to cart")
          case RemoveItem(item) => println(s"Removing $item to cart")
          case ValidateCart => println("The cart is good")
        }
        Behaviors.same
    }, "simpleShoppingActor"
  )

  shoppingRootActor ! ValidateCart

  def main(args: Array[String]): Unit = {

  }
  // 2 - mutable state



}

object TypedAkkaMutable {
  // 2 - mutable state

  trait ShoppingCartMessage

  case class AddItem(item: String) extends ShoppingCartMessage

  case class RemoveItem(item: String) extends ShoppingCartMessage

  case object ValidateCart extends ShoppingCartMessage

  // Creating a hierarchy of a message family

  val shoppingRootActor = ActorSystem(
    Behaviors.setup[ShoppingCartMessage]{
      ctx =>
        var items: Set[String] = Set()
        Behaviors.receiveMessage[ShoppingCartMessage] {
          message: ShoppingCartMessage =>
            message match {
              case AddItem(item) =>
                println(s"Adding $item to cart")
                items += item
              case RemoveItem(item) =>
                println(s"Removing $item to cart")
                items -= item
              case ValidateCart =>
                println("The cart is good" + items)
            }
            Behaviors.same
        }
    } , "simpleShoppingActor"
  )


  shoppingRootActor ! AddItem("ball")
  shoppingRootActor ! AddItem("car")
  shoppingRootActor ! ValidateCart
  def main(args: Array[String]): Unit = {

  }
}

