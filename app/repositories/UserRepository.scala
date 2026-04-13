package repositories

import models.UserInfo

import scala.concurrent.{ExecutionContext, Future}

trait UserRepository {
  def create(user: UserInfo)(implicit ec: ExecutionContext): Future[Unit]

  /** Updates email and/or password for the given user id.
    * Returns true if a row was updated, false if the user does not exist.
    */
  def update(usrid: String, emailid: Option[String], password: Option[String])(
      implicit ec: ExecutionContext
  ): Future[Boolean]
}
