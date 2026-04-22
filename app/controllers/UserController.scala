package controllers

import javax.inject._
import play.api.mvc._
import play.api.libs.json.__
import services.UserService
import models.User
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class UserController @Inject() (
    cc: ControllerComponents,
    user_services: UserService
)(implicit ec: ExecutionContext)
    extends AbstractController(cc) {}
