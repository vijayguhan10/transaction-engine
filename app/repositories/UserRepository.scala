package repositories
import javax.inject._
import slick.jdbc.PostgresProfile.api._
import models.User
import scala.concurrent.{ExecutionContext, Future}
@Singleton
class UserRepository @Inject() (db: Database)(implicit ec: ExecutionContext) {
  class UserTable(tag: Tag) extends Table[User](tag, "users") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def name = column[String]("name")
    def age = column[Int]("age")
    def address = column[String]("address")
    def*=(id.?,name , age,address)<>(User.tupled,User,unapply)
  }
  private val users=TableQuery[UserTable]
  def create (user:User):Future[User]={
    val insertQuery=
  }
}
