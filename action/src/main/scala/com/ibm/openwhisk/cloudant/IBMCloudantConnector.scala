package com.ibm.openwhisk.cloudant

import java.io.FileInputStream
import java.net.URL
import java.sql.Timestamp
import java.util
import java.util.Properties

import com.cloudant.client.api.{ ClientBuilder, CloudantClient }
import com.cloudant.client.api.model.Document
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.cloudant.client.api.Database
import java.io.InputStream
import com.fasterxml.jackson.databind.DeserializationFeature
import scala.collection.JavaConversions._
import com.ibm.openwhisk.domain.ChatTranscript

/**
 * Created by sanjeevghimire on 11/1/16.
 */
object IBMCloudantConnector {

  /**
   * Get cloudant connection using connection parameters from config.properties
   */
  def getCloudantClient(): CloudantClient = {

    val (host, port, url, username, password, dbname, key, passcode) =
      try {
        val prop = new Properties()
        // prop.load(new FileInputStream("src/main/resources/config.properties"))

        prop.load(getClass().getResourceAsStream("/config.properties"))
        (
          prop.getProperty("host"),
          new Integer(prop.getProperty("port")),
          prop.getProperty("url"),
          prop.getProperty("username"),
          prop.getProperty("password"),
          prop.getProperty("dbname"),
          prop.getProperty("key"),
          prop.getProperty("passcode"))
      } catch {
        case e: Exception =>
          e.printStackTrace()
          sys.exit(1)
      }

    ClientBuilder.account(host)
      .username(username)
      .password(password)
      .build()
  }
}
