package repositories

import models.UserInfo

import javax.inject.{Inject, Singleton}
import play.api.db.Database

import scala.concurrent.{ExecutionContext, Future}

@Singleton
final class JdbcUserRepository @Inject()(db: Database) extends UserRepository {

  override def create(user: UserInfo)(implicit ec: ExecutionContext): Future[Unit] = Future {
    db.withConnection { conn =>
      val sql = "INSERT INTO userinfo (usrid, emailid, password) VALUES (?, ?, ?)"
      val ps = conn.prepareStatement(sql)
      try {
        ps.setString(1, user.usrid)
        ps.setString(2, user.emailid)
        ps.setString(3, user.password)
        ps.executeUpdate()
        ()
      } finally {
        ps.close()
      }
    }
  }

  override def update(usrid: String, emailid: Option[String], password: Option[String])(
      implicit ec: ExecutionContext
  ): Future[Boolean] = Future {
    val emailToSet = emailid.orNull
    val passwordToSet = password.orNull

    db.withConnection { conn =>
      val sql =
        "UPDATE userinfo SET " +
          "emailid = COALESCE(?, emailid), " +
          "password = COALESCE(?, password) " +
          "WHERE usrid = ?"

      val ps = conn.prepareStatement(sql)
      try {
        ps.setString(1, emailToSet)
        ps.setString(2, passwordToSet)
        ps.setString(3, usrid)
        ps.executeUpdate() > 0
      } finally {
        ps.close()
      }
    }
  }
}
