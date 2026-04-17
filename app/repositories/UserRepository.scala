package repositories
import javax.inject._
import scala.concurrent.{Future, ExecutionContext}
import slick.jdbc.PostgresProfile.api._
import models.User
@singleton
class UserRepository @Inject() (db: Database)(implicit ec: ExecutionContext) {
  class UserTable(tag: Tag) extends Table[User](tag, "users") {
    def id = column[Long]("id", o.PrimaryKey, o.AutoInc)

  }
}