import com.jowanza.Workable
import scala.collection.immutable.Map


object ChatMain {

  def main(args: Array[String]): Unit = {

    val jsonString: String = args(0)

    val inputMap:Map[String , String] = jsonString.substring(1, jsonString.length - 1).split(",").map(_.split(":")).map { case Array(k, v) => (k.trim.replaceAll("\"",""),v.trim.replaceAll("\"",""))}.toMap

    val command: String = inputMap.get("command").get

    println(Workable.getData(command))
  }
}

