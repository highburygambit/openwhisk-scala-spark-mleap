import com.jowanza.ExecutePipeline

object ChatMain {


  def main(args: Array[String]): Unit = {

    val jsonString: String = args(0).toString

    val json: String = ExecutePipeline.convertJSONString(jsonString)

    print(ExecutePipeline.predictModel(json.toString))
  }
}


