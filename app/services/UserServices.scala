package services

import javax.inject._
import repositories.UserRepository
import models.User

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class UserService @Inject()(repo: UserRepository)(implicit ec: ExecutionContext) {

  def createUser(user: User): Future[User] =
    repo.create(user)

  def getAllUsers(): Future[Seq[User]] =
    repo.findAll()

  def getUser(id: Long): Future[Option[User]] =
    repo.findById(id)

  def deleteUser(id: Long): Future[Int] =
    repo.delete(id)
}