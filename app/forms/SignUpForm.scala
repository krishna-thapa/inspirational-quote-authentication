package forms

import play.api.data.{ Form, Forms }
import play.api.data.Forms._

/**
  * Helper to manage HTML form description, submission and validation.
  * https://www.playframework.com/documentation/2.8.x/ScalaForms
  */
object SignUpForm {

  val form = Form(
    mapping(
      "firstName" -> nonEmptyText,
      "lastName"  -> nonEmptyText,
      "email"     -> email,
      "password"  -> nonEmptyText
    )(Data.apply)(Data.unapply)
  )

  case class Data(
      firstName: String,
      lastName: String,
      email: String,
      password: String
  )
}
