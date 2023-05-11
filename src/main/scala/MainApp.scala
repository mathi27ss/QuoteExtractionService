import akka.actor.ActorSystem
import io.cequence.openaiscala.service.OpenAIServiceFactory
import play.api.libs.json.{ Format, Json }
import scala.concurrent.{ Await, ExecutionContext }



object Constant {
  val firstPoint = "From the following JSON, get the quotes for each news article and send back the quotes with attributions to the person in JSON format, include the id to identify the quote."
  val responseFormat = """Here is an example of the format of expected response:
                         |[
                         |{
                         |        articleIdentifier: "",
                         |        "quote": "",
                         |        "attribution": ""
                         |}
                         |]""".stripMargin
}
object MainApp extends App {
  val systemName = "quote-extraction-service"

  lazy implicit val system: ActorSystem = ActorSystem.create(systemName)
  lazy implicit val ec: ExecutionContext = system.dispatcher

  case class QuoteExtraction (id: String, content: String)
  implicit val quoteExtractionFormat: Format[QuoteExtraction] = Json.format[QuoteExtraction]

  val id = "1234"
  val content = "Ukraine's President Volodymyr Zelensky has said his country needs more time to launch a much-anticipated counter-offensive against Russian forces, as its military awaits the delivery of promised aid.\nThe expected attack could be decisive in the war, redrawing frontlines that, for months, have remained unchanged. It will also be a crucial test for Ukraine, eager to prove that the weapons and equipment it has received from the West can result in significant battlefield gains.\nSpeaking at his headquarters in Kyiv, President Zelensky described combat brigades, some of which were trained by Nato countries, as being \"ready\" but said the army still needed \"some things\", including armoured vehicles that were \"arriving in batches\".\n\"With [what we already have] we can go forward, and, I think, be successful,\" he said in an interview for public service broadcasters who are members of Eurovision News, like the BBC. \"But we'd lose a lot of people. I think that's unacceptable. So we need to wait. We still need a bit more time.\"\nWhen and where the Ukrainian push will happen is a secret. Russian forces, meanwhile, have fortified their defences along a frontline that runs for 900 miles (1,450km) from the eastern regions of Luhansk and Donetsk, to Zaporizhzhia and Kherson in the south.\nUkrainian authorities have tried to lower expectations of a breakthrough, publicly and in private. Earlier this month, a senior government official, who spoke on condition of anonymity, said the country's leaders \"understood that [they] needed to be successful\" but that the assault should not be seen as a \"silver bullet\" in a war now in its 15th month."

  val id1 = "3456"
  val content1 = "Madrid Open organisers have apologised to players and fans for the \"unacceptable decision\" to not allow the women's doubles finalists to make presentation speeches.\n\nVictoria Azarenka and Beatriz Haddad Maia beat Americans Jessica Pegula and Coco Gauff 6-1 6-4 on Sunday.\n\nAll finalists in the singles and the men's doubles addressed the crowd after their matches.\n\nOrganisers say they have apologised directly to the four players involved.\n\n'What century were they living in?' - Pegula on Madrid Open\nTournament chief executive Gerard Tsobanian said: \"We sincerely apologise to all the players and fans who expect more of the Mutua Madrid Open tournament.\n\n\"Not giving our women's doubles finalists the chance to address their fans at the end of the match was unacceptable and we have apologised directly to Victoria, Beatriz, Coco and Jessica.\n\n\"We are working internally and with the WTA (Women's Tennis Association) to review our protocols and are committed to improving our process moving forward. We made a mistake and this will not ever happen again.\"\n\nOrganisers had initially said they would \"not comment on the matter\" when contacted by BBC Sport earlier this week.\n\nPegula said the decision was \"disappointing\" and questioned \"what century everyone was living in\", while Belarus' Azarenka said it was \"hard to explain\" to her young son Leo why she was not able to address him in a victory speech.\n\nThe Professional Tennis Players' Association said the players had been denied \"the right to freedom of expression\".\n\nThe WTA, the governing body of the women's tour, has not commented.\n\nThe incident brought more accusations of sexism at the clay-court tournament, after criticism over the ball girls' outfits and the difference in sizes of the birthday cakes presented to men's champion Carlos Alcaraz and women's champion Aryna Sabalenka."
  val req = List(QuoteExtraction(id, content), QuoteExtraction(id1, content1) )



  val service = OpenAIServiceFactory(
    apiKey = ""
  )


def convertToJson(payload: List[QuoteExtraction]) = {
  Json.toJson(payload).toString()
}
  import Constant._
  def getChatGptRequest(payload: List[QuoteExtraction]) = {
    val jsonString = convertToJson(payload)
    s"${firstPoint}+ ${jsonString}+ ${responseFormat}"
  }
  def chatGptResponse(prompt:String) = {
    service.createCompletion(prompt).map(completion =>
      completion.choices.head.text
    )
  }

  val request = getChatGptRequest(req)
  val response = chatGptResponse(request)
//  Await(response, 10.seconds)








}
