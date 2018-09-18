package com.jowanza
import com.fasterxml.jackson.databind.{DeserializationFeature, ObjectMapper}
import com.fasterxml.jackson.module.scala.DefaultScalaModule

object Workable {

  case class Jowanza(name: String)

    implicit def getObjectMapper(): ObjectMapper = {
      val objectMapper = new ObjectMapper()
      objectMapper.registerModule(DefaultScalaModule)
      objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    }


  def getData(): String = {
    getObjectMapper().writeValueAsString(Jowanza("jowanza"))
  }

}
