import akka.actor.{ActorSystem, Props}
import akka.pattern.ask
import akka.util.Timeout
import akkaquote.actor.{QuotePrinterActor, QuotesHandlerActor}
import akkaquote.message.{AddQuote, PrintRandomQuote, Quote}

import scala.concurrent.Await
import scala.concurrent.duration.DurationInt

object Main extends App {

  val system = ActorSystem("AkkaQuote")
  val quoteActorRef= system.actorOf(Props[QuotesHandlerActor], "quotesActor")
  val quotePrinterActorRef = system.actorOf(Props(new QuotePrinterActor(quoteActorRef)), "quotesPrinterActor")

  implicit  val timeout = Timeout(10 seconds)

  val future1 = quoteActorRef ? AddQuote (new Quote("Hello world", "Various book authors"))
  val future2 = quoteActorRef ? AddQuote (new Quote("To be or not to be","W.Shakespeare"))
  val future3 = quoteActorRef ? AddQuote (new Quote("In the middle od difficulty lies opportunity","A. Einstein"))

  Await.result(future1, timeout.duration)
  Await.result(future2, timeout.duration)
  Await.result(future3, timeout.duration)

  val future4 = quotePrinterActorRef ? PrintRandomQuote
  Await.result(future4, timeout.duration)

  system.terminate()
}
