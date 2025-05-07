package controllers

import play.api.mvc._
import play.api.libs.json._

import javax.inject._

@Singleton
class AuthController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  private val validUser = Map("email" -> "user@example.com", "password" -> "secret123")

  def login: Action[JsValue] = Action(parse.json) { request =>
    val email = (request.body \ "email").asOpt[String]
    val password = (request.body \ "password").asOpt[String]

    (email, password) match {
      case (Some(e), Some(p)) if e == validUser("email") && p == validUser("password") =>
        Ok(Json.obj("status" -> "success", "message" -> "Logged in"))
          .withSession("user" -> e)

      case _ =>
        Unauthorized(Json.obj("status" -> "error", "message" -> "Invalid credentials"))
    }
  }

  def me: Action[AnyContent] = Action { request =>
    request.session.get("user") match {
      case Some(email) => Ok(Json.obj("status" -> "success", "user" -> email))
      case None => Unauthorized(Json.obj("status" -> "error", "message" -> "Not fwfw  in"))
    }
  }

  def logout: Action[AnyContent] = Action {
    Ok(Json.obj("status" -> "success", "message" -> "Logged out")).withNewSession
  }
}
