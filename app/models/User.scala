package models
import play.api.libs.json._

final case class User(
    id: Option[Long],
    name: String,
    age: Int,
    address: String
)

object User {
    implicit val format: OFormat[User] = Json.format[User]
}
