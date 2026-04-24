package repositories // package grouping (logical folder)

import javax.inject._ // brings @Inject, @Singleton (dependency injection)
import slick.jdbc.PostgresProfile.api._ 
// imports ALL Slick DSL tools: Table, column, Database, Query, operators (===, +=, etc.)

import models.User // your case class (domain model)

import scala.concurrent.{ExecutionContext, Future} 
// Future → async result
// ExecutionContext → thread pool used to run Futures


@Singleton // only ONE instance of this repository will exist (managed by Play/Guice)
class UserRepository @Inject()(db: Database)(implicit ec: ExecutionContext) {
  // @Inject() → Play will automatically provide dependencies
  // db: Database → connection handler (uses connection pool internally)
  // implicit ec → thread pool for async execution (Future)

  //Purpose of the profile api import is 
  // -----------------------------
  // TABLE DEFINITION (schema mapping)
  // -----------------------------
  class UsersTable(tag: Tag) extends Table[User](tag, "users") {
    // tag → internal Slick identifier for table instance
    // Table[User] → each row will be mapped to User
    // "users" → actual DB table name

    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    // column[T] → defines a column returning type T
    // "id" → DB column name
    // O.PrimaryKey → marks as primary key
    // O.AutoInc → DB auto-generates this value

    def name = column[String]("name")
    // Rep[String] → represents SQL column "name"

    def age = column[Int]("age")
    // Rep[Int] → represents SQL column "age"

    def address = column[String]("address")
    // Rep[String] → represents SQL column "address"

    def * = (id.?, name, age, address) <> (User.tupled, User.unapply)
    // * → default projection (how a row maps to User)

    // id.? → converts Rep[Long] → Rep[Option[Long]]
    // because case class uses Option[Long]

    // (id.?, name, age, address) → tuple representing one DB row

    // <> → mapping operator (bridge between DB tuple and Scala object)

    // User.tupled → (tuple → User)
    // User.unapply → (User → tuple)

    // So:
    // DB row → tuple → User
    // User → tuple → DB row
  }

  // -----------------------------
  // TABLE QUERY (entry point to query)
  // -----------------------------
  private val users = TableQuery[UsersTable]
  // TableQuery → represents the entire table
  // used to build queries (NOT actual data)

  // -----------------------------
  // CREATE (INSERT)
  // -----------------------------
  def create(user: User): Future[User] = {
    val insertQuery =
      (users returning users.map(_.id)
        // users → table
        // returning → ask DB to return generated value
        // users.map(_.id) → select id column to return

        into ((user, id) => user.copy(id = Some(id))))
        // into → combine (input user + returned id)
        // user.copy(...) → create new User with generated id

    db.run(insertQuery += user)
    // += → insert operation
    // insertQuery += user → DBIO action (not executed yet)
    // db.run(...) → executes query
    // returns Future[User]
  }

  // -----------------------------
  // READ ALL
  // -----------------------------
  def findAll(): Future[Seq[User]] =
    db.run(users.result)
    // users → table
    // result → converts query into SELECT
    // SQL: SELECT * FROM users
    // returns Seq[User]
    // db.run → executes asynchronously

  // -----------------------------
  // READ BY ID
  // -----------------------------
  def findById(id: Long): Future[Option[User]] =
    db.run(users.filter(_.id === id).result.headOption)

    // filter → adds WHERE clause
    // _.id === id → SQL condition (id = ?)
    // === → Slick equality operator (builds SQL expression)

    // result → SELECT query
    // SQL: SELECT * FROM users WHERE id = ?

    // headOption → take first row safely
    // if found → Some(User)
    // if not → None

  // -----------------------------
  // DELETE
  // -----------------------------
  def delete(id: Long): Future[Int] =
    db.run(users.filter(_.id === id).delete)

    // filter → WHERE id = ?
    // delete → DELETE query
    // SQL: DELETE FROM users WHERE id = ?

    // returns Int → number of rows deleted
}