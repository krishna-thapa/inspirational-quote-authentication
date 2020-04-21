package utils

import com.mohiva.play.silhouette.api.Env
import com.mohiva.play.silhouette.impl.authenticators.CookieAuthenticator

import models.User

/**
  * Defines key components used for user authentication, which authenticator to
  * use(JWT, Cookie, Bearer token etc.). Environment consists of two types,
  * Identity(represents a User) and Authenticator(user validator).
  */
trait DefaultEnv extends Env {
  type I = User
  type A = CookieAuthenticator
}
