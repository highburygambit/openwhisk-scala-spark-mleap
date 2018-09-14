package com.ibm.openwhisk.domain

import java.sql.Timestamp
import java.util.List

/**
 * Created by sanjeevghimire on 11/1/16.
 */
@SerialVersionUID(100L)
case class ChatTranscript(
  val chatSessionId: String,
  val text: List[Chat],
  val sessionStart: Timestamp) extends Serializable