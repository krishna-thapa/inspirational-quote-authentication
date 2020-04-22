# Silhouette, in a nutshell:

The principal characteristic of the Silhouette framework is flexibility. Silhouette implements a set of stand-alone authentication components, and it's up to the developer to configure and combine them to build authentication logic.

The main components are:

- **Identity Service**: Silhouette relies on an implementation of an IdentityService trait to handle all the operations related to retrieving users. In this way, user management is completely decoupled from the framework. The UserService class, described in the "User service" section, implements an identity service backed by MongoDB.
- **AuthInfoRepository**: Silhouette needs to know how to persist user credentials. The framework delegates this job to an implementation of the AuthInfoRepository trait. The application uses a composite repository that combines the PasswordInfoDao and OAuth1InfoDao classes described in the "Model persistence" section.
- **Authenticator**: Authenticators track a user after a successful authentication. They are tokens storing data such as its validity status and the login information for a user. Silhouette has implementations based on cookies, the Play stateless session, HTTP headers, and JSON Web Tokens (JWT).
- **Authenticator Service**: Every authenticator has an associated authenticator service responsible for an authenticator's lifecycle: creation, initialization, updates, renewal, and expiration.
- **Environment**: The environment defines the key components needed by a Silhouette application. It's type-parameterized by the user and authenticator types (in the application,the User class defined in Listing 4 and a CookieAuthenticator). The environment is built by passing the identity service implementation (UserService) and the authenticator service implementation. I'm using the CookieAuthenticatorService class, required by the CookieAuthenticator type.
- **Provider**: A provider is a service that handles the authentication of a user. The application uses Silhouette's CredentialsProvider for local authentication and the OAuth1 TwitterProvider.
- **SocialProviderRegistry**: This is a placeholder for all the social providers supported by the application. In this case, it contains the TwitterProvider instance.

## Classes/ traits used from Silhouette

1. LoginInfo: The login info contains the data about the provider that authenticated that identity.

   - @param providerID The ID of the provider.
   - @param providerKey A unique key which identifies a user on this provider (userID, email, ...)

2. PasswordInfo: The password details.

   - @param hasher The ID of the hasher used to hash this password.
   - @param password The hashed password.
   - @param salt The optional salt used when hashing.

3. PasswordHasher: A trait that defines the password hasher interface.

4. DelegableAuthInfoDAO: An implementation of the auth info DAO. This abstract implementation of the [[com.mohiva.play.silhouette.persistence.daos.AuthInfoDAO]] trait allows us to get the class tag of the auth info it is responsible for.

   - @tparam T The type of the auth info to store.

5. IdentityService: A trait that provides the means to retrieve identities for the Silhouette module.

   - @param loginInfo The login info to retrieve an identity.
   - @return The retrieved identity or None if no identity could be retrieved for the given login info.

6. AuthInfoRepository: A trait that provides the means to persist authentication information for the Silhouette module.

7. CredentialsProvider: A provider for authenticating with credentials.

8. CookieAuthenticator: An authenticator that uses a stateful as well as stateless, cookie based approach. It works either by storing an ID in a cookie to track the authenticated user and a server side backing store that maps the ID to an authenticator instance or by a stateless approach that stores the authenticator in a serialized form directly into the cookie. The stateless approach could also be named “server side session”.

9. Env: The environment type. Defines the [[Identity]] and [ [Authenticator]] types for an environment. It is possible to implement as many types as needed. This has the advantage that an application isn't bound only to a single `Identity` -> `Authenticator` combination.

10. Identity: This trait represents an authenticated user.
