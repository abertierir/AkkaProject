package akkaquote.actor

import akka.actor.{Actor, ActorRef}
import akkaquote.message.{PrintRandomQuote, QuotePrinted, QuoteRequested, RequestQuote}

class QuotePrinterActor(val quoteManagerActorRef : ActorRef) extends Actor{
  override def receive: Receive = {

    case PrintRandomQuote => {
      val originalSender = sender
      quoteManagerActorRef ! RequestQuote(originalSender)
    }

    case QuoteRequested(quote, originalSender) => {
      System.out.println('"'+quote.quote+'"')
      System.out.println("--"+quote.author)
      originalSender ! QuotePrinted
    }

  }
}
