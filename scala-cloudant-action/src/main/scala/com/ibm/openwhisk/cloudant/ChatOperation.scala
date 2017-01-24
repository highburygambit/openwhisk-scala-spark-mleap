package com.ibm.openwhisk.cloudant

import java.util.List

import com.cloudant.client.api.{CloudantClient, Database}
import com.ibm.openwhisk.domain.{Chat, ChatTranscript}

import scala.util.Random

object ChatOperation {
  implicit val client: CloudantClient = IBMCloudantConnector.getCloudantClient()

  val dbName = "chat_transcripts"
  val to = "watson"

  /*
	*	save chat to database
	*/
  def save(from: String,text: String): Chat = {
    val db: Database = client.database(dbName, false)
    val chat = Chat(from, to, text, new java.sql.Timestamp(System.currentTimeMillis()))
    db.save(chat)
    return chat
  }

  /*
	*	get chat transcript
	*/
  def getTranscript(): ChatTranscript = {
    val db: Database = client.database(dbName, false)
    val chats:List[Chat] = db.getAllDocsRequestBuilder().includeDocs(true).build().getResponse().getDocsAs(classOf[Chat])
    ChatTranscript(Random.alphanumeric.take(16).mkString,chats,new java.sql.Timestamp(System.currentTimeMillis()))
  }
}