// https://docs.scala-lang.org/tour/package-objects.html
package object models {

  import slick.lifted.ProvenShape

  import slick.jdbc.MySQLProfile.api._
  import slick.lifted.Tag
  import com.mohiva.play.silhouette.api.Identity

  case class User(
      id: Option[Int],
      firstName: Option[String],
      lastName: Option[String],
      email: Option[String],
      providerId: String,
      providerKey: String
  ) extends Identity

  class Users(tag: Tag) extends Table[User](tag, "userinfo") {
    def id: Rep[Option[Int]]           = column[Option[Int]]("user_id", O.PrimaryKey, O.AutoInc)
    def firstName: Rep[Option[String]] = column[Option[String]]("first_name")
    def lastName: Rep[Option[String]]  = column[Option[String]]("last_name")
    def email: Rep[Option[String]]     = column[Option[String]]("email")
    def providerId: Rep[String]        = column[String]("provider_id")
    def providerKey: Rep[String]       = column[String]("provider_key")

    def * : ProvenShape[User] =
      (id, firstName, lastName, email, providerId, providerKey) <> (User.tupled, User.unapply)
  }

  case class Password(key: String, hasher: String, hash: String, salt: Option[String])

  class Passwords(tag: Tag) extends Table[Password](tag, "password") {
    def key: Rep[String]          = column[String]("provider_key", O.PrimaryKey)
    def hasher: Rep[String]       = column[String]("hasher")
    def hash: Rep[String]         = column[String]("hash")
    def salt: Rep[Option[String]] = column[Option[String]]("salt")

    def * : ProvenShape[Password] = (key, hasher, hash, salt) <> (Password.tupled, Password.unapply)
  }

  val userTable     = TableQuery[Users]
  val passwordTable = TableQuery[Passwords]
}
