package controllers // Organizes this class under "controllers" package

// Dependency Injection annotations
import javax.inject._

// Play MVC core components (Action, Controller, Result helpers like Ok)
import play.api.mvc._

// Play JSON library (Json, JsValue, validation)
import play.api.libs.json._

// Your business logic layer
import services.UserService

// Your data model
import models.User

// Scala concurrency tools
import scala.concurrent.{ExecutionContext, Future}

// Ensures only one instance of this controller is created
@Singleton
class UserController @Inject() ( // Constructor injection (Play injects dependencies)
    cc: ControllerComponents, // Required by Play to build controller
    userService: UserService // Service layer dependency
)(implicit ec: ExecutionContext) // Thread pool for async operations
    extends AbstractController(cc) { // Gives helper methods like Ok, Action, etc.

  // ---------------- CREATE USER ----------------
  def createUser: Action[JsValue] = Action.async(parse.json) { request =>
    // request.body is JSON, validate it into User case class
    request.body
      .validate[User]
      .fold(
        // ❌ Case 1: Validation failed
        errors =>
          Future.successful( // Wrap Result into Future
            BadRequest( // HTTP 400 response
              JsError.toJson(errors) // Convert validation errors → JSON
            )
          ),

        // ✅ Case 2: Validation success
        user => {
          userService.createUser(user).map { created => // Async DB call
            Created(Json.toJson(created)) // HTTP 201 + convert object → JSON
          }
        }
      )
  }

  // ---------------- GET ALL USERS ----------------
  def getAllUsers: Action[AnyContent] = Action.async {

    // Call service → returns Future[List[User]]
    userService.getAllUsers().map { users =>
      Ok(Json.toJson(users)) // Convert list → JSON, return 200
    }
  }

  // ---------------- GET USER BY ID ----------------
  def getUser(id: Long): Action[AnyContent] = Action.async {

    userService.getUser(id).map {

      case Some(user) =>
        Ok(Json.toJson(user)) // Found → return JSON

      case None =>
        NotFound("User not found") // Not found → 404
    }
  }

  // ---------------- DELETE USER ----------------
  def deleteUser(id: Long): Action[AnyContent] = Action.async {

    userService.deleteUser(id).map {

      case 0 =>
        NotFound("User not found") // Nothing deleted

      case _ =>
        NoContent // 204 success, no body
    }
  }
}
