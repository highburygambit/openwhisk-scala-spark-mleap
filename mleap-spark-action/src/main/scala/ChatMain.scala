import com.jowanza.ExecutePipeline
import spray.json._
import spray.json.DefaultJsonProtocol._

object ChatMain {

  def main(args: Array[String]): Unit = {

    val jsonString = args(0).parseJson.asJsObject.getFields("data").seq.head.asInstanceOf[JsArray]

    val convertedSeq = convertSeq(jsonString.elements)

    print(ExecutePipeline.predictModel(convertedSeq))
  }

  def convertSeq(data: Vector[JsValue]): Seq[Any] = {
    val g = Seq(data.head.convertTo[String],
      data(1).convertTo[Double],data(2).convertTo[Double],
      data(3).convertTo[Double],data(4).convertTo[Double],data(5).convertTo[Double]
      ,data(6).convertTo[Double],data(7).convertTo[Double],
      data(8).convertTo[Double],data(9).convertTo[String],data(10).convertTo[String],
      data(11).convertTo[String],data(12).convertTo[String])
    g
  }
}


