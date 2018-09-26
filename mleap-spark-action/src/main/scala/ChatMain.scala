import com.jowanza.ExecutePipeline
import spray.json._
import spray.json.DefaultJsonProtocol._

object ChatMain {

  def main(args: Array[String]): Unit = {

    val jsonString = args(0).parseJson.asJsObject.getFields("data").

    println(jsonString)
    println(Seq("NY", 2.0, 1250.0, 3.0, 50.0, 30.0, 2.0, 56.0, 90.0, "Entire home/apt", "1.0", "strict", "1.0"))

//    print(jsonString)
//    print(ExecutePipeline.predictModel(jsonString))
  }
}


