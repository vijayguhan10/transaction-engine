package controllers

import models.UserInfo
import play.api.libs.json.{JsError, JsObject, JsSuccess, Json}
import play.api.mvc.{BaseController, ControllerComponents}
import services.UserService

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
final class UserController @Inject()(
    val controllerComponents: ControllerComponents,
    userService: UserService
)(implicit ec: ExecutionContext)
    extends BaseController {

  /** Create a user.
    * POST /users
    * Body: {"emailid":"a@b.com","usrid":"u1","password":"p"}
    */
  def create(): play.api.mvc.Action[play.api.libs.json.JsValue] = Action(parse.json).async { implicit request =>
    request.body.validate[UserInfo] match {
      case JsSuccess(user, _) =>
        userService
          .create(user)
          .map(_ => Created(Json.obj("status" -> "created", "usrid" -> user.usrid)))
          .recover { case e: IllegalArgumentException =>
            BadRequest(Json.obj("error" -> e.getMessage))
          }

      case JsError(errors) =>
        Future.successful(BadRequest(Json.obj("error" -> "invalid_json", "details" -> JsError.toJson(errors))))
    }
  }

  /** Update a user.
    * PUT /users/:usrid
    * Body: {"emailid":"new@b.com"} and/or {"password":"new"}
    */
  def update(usrid: String): play.api.mvc.Action[play.api.libs.json.JsValue] = Action(parse.json).async { implicit request =>
    val obj: JsObject = request.body.asOpt[JsObject].getOrElse(Json.obj())
    val emailid = (obj \ "emailid").headOption.flatMap(_.asOpt[String])
    val password = (obj \ "password").headOption.flatMap(_.asOpt[String])

    userService
      .update(usrid, emailid, password)
      .map {
        case true  => Ok(Json.obj("status" -> "updated", "usrid" -> usrid))
        case false => NotFound(Json.obj("error" -> "not_found", "usrid" -> usrid))
      }
      .recover { case e: IllegalArgumentException =>
        BadRequest(Json.obj("error" -> e.getMessage))
      }
  }
}
