import com.jowanza.ExecutePipeline
import com.fasterxml.jackson.databind.{DeserializationFeature, ObjectMapper}
import com.fasterxml.jackson.module.scala.DefaultScalaModule


object ChatMain {

  implicit def getObjectMapper(): ObjectMapper = {
    val objectMapper = new ObjectMapper()
    objectMapper.registerModule(DefaultScalaModule)
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
  }

  def main(args: Array[String]): Unit = {

//    val jsonString: String = args(0)

      val jsonString = "{\"value\":{\"data\":{\"schema\":{\"fields\":[{\"name\":\"state\",\"type\":\"string\"},{\"name\":\"bathrooms\",\"type\":\"double\"},{\"name\":\"square_feet\",\"type\":\"double\"},{\"name\":\"bedrooms\",\"type\":\"double\"},{\"name\":\"security_deposit\",\"type\":\"double\"},{\"name\":\"cleaning_fee\",\"type\":\"double\"},{\"name\":\"extra_people\",\"type\":\"double\"},{\"name\":\"number_of_reviews\",\"type\":\"double\"},{\"name\":\"review_scores_rating\",\"type\":\"double\"},{\"name\":\"room_type\",\"type\":\"string\"},{\"name\":\"host_is_superhost\",\"type\":\"string\"},{\"name\":\"cancellation_policy\",\"type\":\"string\"},{\"name\":\"instant_bookable\",\"type\":\"string\"}]},\"rows\":[[\"NY\",2.0,1250.0,3.0,50.0,30.0,2.0,56.0,90.0,\"Entire home/apt\",\"1.0\",\"strict\",\"1.0\"]]}}}"

      case class MyDate(map: Map[String, Map[String, String]])

      val d= getObjectMapper().readValue(jsonString, classOf[Map[String, String]])





//    val inputMap:Map[String , String] = jsonString.substring(1, jsonString.length - 1).split(",").map(_.split(":")).map { case Array(k, v) => (k.trim.replaceAll("\"",""),v.trim.replaceAll("\"",""))}.toMap

//    val command: String = inputMap.get("data").get



//    print(ExecutePipeline.predictModel(command))
  }
}


