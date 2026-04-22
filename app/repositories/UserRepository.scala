package repositories

import javax.inject._
import slick.jdbc.PostgresProfile.api._
import models.User

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class UserRepository @Inject()(db: Database)(implicit ec: ExecutionContext) {

  class UsersTable(tag: Tag) extends Table[User](tag, "users") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def name = column[String]("name")
    def age = column[Int]("age")
    def address = column[String]("address")

    def * = (id.?, name, age, address) <> (User.tupled, User.unapply)
  }

  private val users = TableQuery[UsersTable]

  def create(user: User): Future[User] = {
    val insertQuery =
      (users returning users.map(_.id)
        into ((user, id) => user.copy(id = Some(id))))

    db.run(insertQuery += user)
  }

  def findAll(): Future[Seq[User]] =
    db.run(users.result)

  def findById(id: Long): Future[Option[User]] =
    db.run(users.filter(_.id === id).result.headOption)

  def delete(id: Long): Future[Int] =
    db.run(users.filter(_.id === id).delete)
}