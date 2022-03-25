package akkaquote.actor

import akka.actor.Actor
import akkaquote.message.{AddQuote, Quote, QuoteAdded, QuoteRequested, RequestQuote}

import scala.collection.mutable.ListBuffer
import scala.util.Random

class QuotesHandlerActor extends Actor{

  val quotes = new ListBuffer[Quote]()

  override def receive: Receive = {
    case AddQuote(quote) => {
      quotes+= quote
      sender ! QuoteAdded
    }
    case RequestQuote(originalSender) =>{
      val index = Random.nextInt(quotes.size)
      sender ! QuoteRequested(quotes(index), originalSender)
    }
  }

}
