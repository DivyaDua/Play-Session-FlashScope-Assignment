package controllers

import javax.inject._
import play.api._
import play.api.mvc._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController extends Controller {

  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index = Action { implicit request: Request[AnyContent] =>
    Ok("Hello World").withSession("Yes" -> "Session started for user")
  }

  def action3(id: Int) = Action { implicit request: Request[AnyContent] =>
    Redirect(routes.HomeController.action2(s"with id: $id"))/*.withSession("Yes" -> s"User with id: $id")*/
  }

  def action1() = Action{
    request =>
      request.session.get("Yes").map { user =>
       Ok(request.flash.get("success").getOrElse("No user found "))
      }.getOrElse {
        Unauthorized("Oops : No user")
      }
  }

  def action2(user: String) = Action{
    Redirect(routes.HomeController.action1()).flashing(
      "success" -> s"We have got a user $user")
  }
}
