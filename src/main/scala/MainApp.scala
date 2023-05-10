import akka.actor.ActorSystem
import io.cequence.openaiscala.service.OpenAIServiceFactory

import scala.concurrent.ExecutionContext

object MainApp extends App {
  val systemName = "quote-extraction-service"

  lazy implicit val system: ActorSystem = ActorSystem.create(systemName)
  lazy implicit val ec: ExecutionContext = system.dispatcher

  val service = OpenAIServiceFactory(
    apiKey = ""
  )

  val prompt =  """
Text - `Ms. Carroll’s allegations first became public in a 2019 New York Magazine article that was an excerpt of a book she published the same year, “What Do We Need Men For?”

The writer, who continues to self-publish a column on Substack, testified over three days, telling jurors that she and Mr. Trump, then a prominent New York City figure and real-estate mogul, struck up a rapport after bumping into one another at Bergdorf Goodman around 1996. The playful banter continued in the lingerie section, she said, but ended once the two entered a dressing room for what she thought was so Mr. Trump could try on a see-through bodysuit as a gag.

“He immediately shut the door and shoved me up against the wall and shoved me so hard my head banged,” she recalled.`

For the Text given above please extract any quotes and then attribute them to the person who said them in the format of Quote-Person
"""


  service.createCompletion(prompt).map(completion =>
    println(completion.choices.head.text)
  )

}
