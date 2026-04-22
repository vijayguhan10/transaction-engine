package controllers

import javax.inject._
import play.api.mvc._
import play.api.libs.json._
import services.UserService
import models.User

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class UserController @Inject()(
  cc: ControllerComponents,
  userService: UserService
)(implicit ec: ExecutionContext)
  extends AbstractController(cc) {

  def createUser: Action[JsValue] = Action.async(parse.json) { request =>
    request.body.validate[User].fold(
      errors => Future.successful(BadRequest(JsError.toJson(errors))),
      user => {
        userService.createUser(user).map { created =>
          Created(Json.toJson(created))
        }
      }
    )
  }

  def getAllUsers: Action[AnyContent] = Action.async {
    userService.getAllUsers().map(users => Ok(Json.toJson(users)))
  }

  def getUser(id: Long): Action[AnyContent] = Action.async {
    userService.getUser(id).map {
      case Some(user) => Ok(Json.toJson(user))
      case None       => NotFound("User not found")
    }
  }

  def deleteUser(id: Long): Action[AnyContent] = Action.async {
    userService.deleteUser(id).map {
      case 0 => NotFound("User not found")
      case _ => NoContent
    }
  }
}