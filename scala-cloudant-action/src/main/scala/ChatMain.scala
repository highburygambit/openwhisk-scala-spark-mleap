import com.ibm.openwhisk.api.ChatAPI
import scala.collection.immutable.Map


/**
  * Created by sanjeevghimire on 1/4/17.
  */
object ChatMain {



  def main(args: Array[String]): Unit = {


    val jsonString: String = args(0)

    val inputMap:Map[String , String] = jsonString.substring(1, jsonString.length - 1).split(",").map(_.split(":")).map { case Array(k, v) => (k.trim.replaceAll("\"",""),v.trim.replaceAll("\"",""))}.toMap

    val command: String = inputMap.get("command").get

    if (command.equals("getChat"))
    {
      print(ChatAPI.getChat())
    } else if (command.equals("sendChat"))
    {
      print(ChatAPI.saveChat("user",inputMap.get("message").get))
    } else{
      println("{\"syntax\": \"cmd value\",")
      println("\"command\": \"sendChat, getChat \",")
      println("\"example1\": \"command sendChat message 'Hi, there' \",")
      println("\"example2\": \"getChat\"}")
    }
  }
}

case class Command(command: String, message: String) extends Serializable
