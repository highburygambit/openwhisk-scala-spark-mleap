
package com.ibm.openwhisk.domain

import java.sql.Timestamp

case class Chat(
  val from: String,
  val to: String,
  val text: String,
  val chatTS: Timestamp) extends Serializable