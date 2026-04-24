package repositories

// -----------------------------
// DEPENDENCY INJECTION (Guice)
// -----------------------------
import javax.inject._

// -----------------------------
// SLICK + PLAY INTEGRATION
// -----------------------------
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

// -----------------------------
// DOMAIN MODEL
// -----------------------------
import models.User

// -----------------------------
// ASYNC SUPPORT
// -----------------------------
import scala.concurrent.{ExecutionContext, Future}


/**
 * Repository Layer:
 * -----------------
 * Acts as a bridge between:
 *   - Application logic
 *   - Database (via Slick)
 *
 * This class is responsible for:
 *   - Defining table schema
 *   - Building queries
 *   - Executing queries
 *
 * Lifecycle:
 *   - Managed by Guice
 *   - Singleton → only one instance exists
 */
@Singleton
class UserRepository @Inject() (

    /**
     * DatabaseConfigProvider:
     * -----------------------
     * Injected by Play/Guice.
     *
     * Responsibility:
     *   - Reads DB config from application.conf
     *   - Provides DatabaseConfig (db + profile)
     *
     * NOTE:
     *   This is ONLY a provider, not the DB itself.
     */
    protected val dbConfigProvider: DatabaseConfigProvider

)(
    /**
     * ExecutionContext:
     * ------------------
     * Thread pool used for async operations (Futures).
     */
    implicit ec: ExecutionContext
)

/**
 * HasDatabaseConfigProvider:
 * ---------------------------
 * This trait internally does:
 *
 *   lazy val dbConfig = dbConfigProvider.get[JdbcProfile]
 *   val db = dbConfig.db
 *   val profile = dbConfig.profile
 *
 * So after extending, we get:
 *   - db        → to execute queries
 *   - profile   → to build queries
 */
extends HasDatabaseConfigProvider[JdbcProfile] {

  /**
   * profile.api:
   * -------------
   * Imports Slick DSL (query language).
   *
   * Provides:
   *   - Table, column
   *   - Query operators (filter, map)
   *   - SQL operators (===)
   *
   * IMPORTANT:
   *   This is DB-specific (Postgres/MySQL/etc)
   */
  import profile.api._

  // ============================================================
  // TABLE DEFINITION (SCHEMA MAPPING)
  // ============================================================

  /**
   * UsersTable:
   * ------------
   * Maps DB table → Scala case class (User)
   *
   * tag:
   *   - Internal Slick identifier
   *
   * "users":
   *   - Actual table name in DB
   */
  class UsersTable(tag: Tag) extends Table[User](tag, "users") {

    /**
     * id column:
     * -----------
     * - Type: Long
     * - Primary Key
     * - Auto Increment
     */
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

    /**
     * name column
     */
    def name = column[String]("name")

    /**
     * age column
     */
    def age = column[Int]("age")

    /**
     * address column
     */
    def address = column[String]("address")

    /**
     * Default Projection (*):
     * -----------------------
     * Defines mapping:
     *
     *   DB Row  <->  Scala Object
     *
     * Left side:
     *   (id.?, name, age, address)
     *
     * Right side:
     *   User.apply / User.unapply
     *
     * id.?:
     *   Converts Long → Option[Long]
     */
    def * =
      (id.?, name, age, address) <> (
        (User.apply _).tupled,   // tuple → User
        User.unapply             // User → tuple
      )
  }

  // ============================================================
  // TABLE QUERY (ENTRY POINT)
  // ============================================================

  /**
   * TableQuery:
   * ------------
   * Represents the entire table.
   *
   * IMPORTANT:
   *   This does NOT fetch data.
   *   It only builds query structure.
   */
  private val users = TableQuery[UsersTable]


  // ============================================================
  // CREATE (INSERT)
  // ============================================================

  /**
   * Insert a new user.
   *
   * Flow:
   *   1. Build insert query
   *   2. Ask DB to return generated ID
   *   3. Combine ID with User object
   *   4. Execute using db.run
   */
  def create(user: User): Future[User] = {

    val insertQuery =
      (users
        returning users.map(_.id) // return generated ID
        into ((user, id) => user.copy(id = Some(id)))
      )

    /**
     * insertQuery += user:
     *   Builds DBIO action (NOT executed yet)
     *
     * db.run(...):
     *   - Executes query
     *   - Returns Future[User]
     */
    db.run(insertQuery += user)
  }


  // ============================================================
  // READ ALL
  // ============================================================

  /**
   * Fetch all users.
   *
   * users.result:
   *   Builds SELECT query
   *
   * SQL:
   *   SELECT * FROM users;
   */
  def findAll(): Future[Seq[User]] =
    db.run(users.result)


  // ============================================================
  // READ BY ID
  // ============================================================

  /**
   * Fetch user by ID.
   *
   * filter:
   *   Adds WHERE clause
   *
   * ===:
   *   Slick equality operator (builds SQL expression)
   *
   * headOption:
   *   - Returns first result safely
   *   - None if not found
   *
   * SQL:
   *   SELECT * FROM users WHERE id = ?
   */
  def findById(id: Long): Future[Option[User]] =
    db.run(
      users
        .filter(_.id === id)
        .result
        .headOption
    )


  // ============================================================
  // DELETE
  // ============================================================

  /**
   * Delete user by ID.
   *
   * delete:
   *   Converts query into DELETE operation
   *
   * SQL:
   *   DELETE FROM users WHERE id = ?
   *
   * Returns:
   *   Number of rows affected
   */
  def delete(id: Long): Future[Int] =
    db.run(
      users
        .filter(_.id === id)
        .delete
    )
}