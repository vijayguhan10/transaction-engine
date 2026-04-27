import play.api.libs.json.OFormat
pacakge models
import play.time.LocalDateTime
import play.api.libs.json.__
case class Account(
    id: Option[Long],
    userId: Long,
    AccountNumber: String,
    balance: BigDecimal,
    createdAt: Option[LocalDateTime]
)
object Account {
  implicit val format: OFormat[Account] = Json.format[Account]
}
