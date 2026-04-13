package models

import play.api.libs.json.{Format, Json}

final case class UserInfo(emailid: String, usrid: String, password: String)

object UserInfo {
  implicit val format: Format[UserInfo] = Json.format[UserInfo]
}
