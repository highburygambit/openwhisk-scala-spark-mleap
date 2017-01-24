package com.ibm.openwhisk.api

import com.fasterxml.jackson.databind.{DeserializationFeature, ObjectMapper}
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.ibm.openwhisk.cloudant.ChatOperation
import com.ibm.openwhisk.domain.{Chat, ChatTranscript}

object ChatAPI {

  implicit def getObjectMapper(): ObjectMapper = {
    val objectMapper = new ObjectMapper()
    objectMapper.registerModule(DefaultScalaModule)
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
  }

  val OPENWHISK = "openwhisk"

  def saveChat(from: String, text: String): String = {
    val chat: Chat = ChatOperation.save(from, text)

    if (text.contains("hi")) {
      return getObjectMapper().writeValueAsString(Chat(OPENWHISK, chat.from, "Hi, this is "+OPENWHISK, new java.sql.Timestamp(System.currentTimeMillis())))
    } else if (text.equals("how are you?")) {
      return getObjectMapper().writeValueAsString(Chat(OPENWHISK, chat.from, "I am good!", new java.sql.Timestamp(System.currentTimeMillis())))
    }

    return getObjectMapper().writeValueAsString(Chat(OPENWHISK, chat.from, "Message Received:"+chat.text, new java.sql.Timestamp(System.currentTimeMillis())))

  }

  def getChat(): String = {
    val chats: ChatTranscript = ChatOperation.getTranscript();
    getObjectMapper().writeValueAsString(chats)
  }

}