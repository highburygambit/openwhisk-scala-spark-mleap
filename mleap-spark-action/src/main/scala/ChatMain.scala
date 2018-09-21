import com.jowanza.ExecutePipeline
import spray.json._
import DefaultJsonProtocol._

object ChatMain {


  def main(args: Array[String]): Unit = {

    val jsonString: String = args(0)

//    val json: String = ExecutePipeline.convertJSONString(jsonString)

    print(ExecutePipeline.predictModel(jsonString.parseJson.asJsObject.getFields("data").last.toString()))
  }
}


