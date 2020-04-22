package models

import javax.inject.Inject

import play.api.db.slick.HasDatabaseConfig
import play.api.db.slick.HasDatabaseConfigProvider
import play.api.db.slick.DatabaseConfigProvider
import slick.driver.JdbcProfile

import com.mohiva.play.silhouette.api.util.PasswordHasher
import com.mohiva.play.silhouette.api.repositories.AuthInfoRepository
import com.mohiva.play.silhouette.api.services.IdentityService
import com.mohiva.play.silhouette.api.LoginInfo
import com.mohiva.play.silhouette.api.util.PasswordInfo
import com.mohiva.play.silhouette.impl.providers.CredentialsProvider

import scala.concurrent.ExecutionContext
import scala.concurrent.Future

import forms.SignUpForm
import scala.util.Failure
import scala.util.Success

/**
  * Silhouette needs an IdentityService to handle all the operations related to retrieving identities(users)
  *
  * @param dbConfigProvider
  * @param passwordHasher
  * @param authInfoRepository
  * @param ec
  */
class UserService @Inject() (
    protected val dbConfigProvider: DatabaseConfigProvider,
    passwordHasher: PasswordHasher,
    authInfoRepository: AuthInfoRepository
)(implicit ec: ExecutionContext)
    extends IdentityService[User]
    with HasDatabaseConfigProvider[JdbcProfile] {

  import profile.api._

  /**
    * Retrieves an identity that matches the specified login info.
    */
  override def retrieve(loginInfo: LoginInfo): Future[Option[User]] = db.run(
    // == calls for equals,
    // === is a custom defined method in slick which is used for column comparison
    userTable
      .filter(user =>
        user.providerKey === loginInfo.providerKey && user.providerId === loginInfo.providerID
      )
      .result
      .headOption
  )

  /**
    * Create a new user login info
    * @return login info of the user
    */
  def create(data: SignUpForm.Data): Future[LoginInfo] = {

    val user = User(
      id = None,
      firstName = Some(data.firstName),
      lastName = Some(data.lastName),
      email = Some(data.email),
      providerId = CredentialsProvider.ID,
      providerKey = data.email
    )

    // use andThen to combine two actions and keep the result of the second one
    // https://docs.scala-lang.org/overviews/scala-book/functional-error-handling.html
    val insertQuery = userTable returning userTable.map(_.id)
    db.run {
      insertQuery += user
    } andThen {
      case Failure(_: Throwable) => None
      case Success(id: Option[Int]) => {
        val loginInfo: LoginInfo   = LoginInfo(CredentialsProvider.ID, data.email)
        val authInfo: PasswordInfo = passwordHasher.hash(data.password)
        authInfoRepository.add(loginInfo, authInfo)
      }
    } map { _id => LoginInfo(CredentialsProvider.ID, user.email.get) }
    // Creates a new future by applying a function to the successful result of
    //  this future.
  }
}
