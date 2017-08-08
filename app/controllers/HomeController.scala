package controllers

import javax.inject._
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
    Ok(views.html.index())
  }

  def startSession(name: String) = Action{
    implicit request =>
      Redirect(routes.HomeController.getSession).withSession("user" -> name)
  }

  def flashAction = Action{
    implicit request => Ok(views.html.home())
  }

  def getSession = Action{
    request =>
      val userName = request.session.get("user")

      val (key, value) = userName match {
        case Some(user) => if (user.equalsIgnoreCase("divya")) {
          ("Success", s"Hello $user, Welcome to play session!!")
        }
          else
          {
            ("Failure", "You are not authorised to access this page")
          }
        case None => ("Failure", "No User Found")
      }

      Redirect(routes.HomeController.flashAction()).flashing(key -> value)

  }


}
