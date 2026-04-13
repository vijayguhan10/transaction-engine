package services

import models.UserInfo
import repositories.UserRepository

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
final class UserService @Inject()(repo: UserRepository) {

  def create(user: UserInfo)(implicit ec: ExecutionContext): Future[Unit] = {
    validateCreate(user)
    repo.create(user)
  }

  def update(usrid: String, emailid: Option[String], password: Option[String])(
      implicit ec: ExecutionContext
  ): Future[Boolean] = {
    val cleanUsrid = usrid.trim
    require(cleanUsrid.nonEmpty, "usrid must be non-empty")

    val cleanEmail = emailid.map(_.trim).filter(_.nonEmpty)
    val cleanPassword = password.map(_.trim).filter(_.nonEmpty)

    require(cleanEmail.nonEmpty || cleanPassword.nonEmpty, "emailid or password must be provided")

    repo.update(cleanUsrid, cleanEmail, cleanPassword)
  }

  private def validateCreate(user: UserInfo): Unit = {
    require(user.usrid.trim.nonEmpty, "usrid must be non-empty")
    require(user.emailid.trim.nonEmpty, "emailid must be non-empty")
    require(user.password.trim.nonEmpty, "password must be non-empty")
  }
}
