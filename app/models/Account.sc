// Import for JSON format type (provides both Reads and Writes)
import play.api.libs.json.OFormat

// Define package name (NOTE: typo fixed from 'pacakge' → 'package')
package models

// Import for date-time type
import java.time.LocalDateTime

// Import Play JSON helpers
import play.api.libs.json.Json

/**
 * Case class representing an Account entity
 *
 * @param id            Optional database ID (None before persistence)
 * @param userId        ID of the user who owns this account
 * @param AccountNumber Unique account number (string format)
 * @param balance       Current account balance (BigDecimal for precision)
 * @param createdAt     Timestamp when account was created (optional)
 */
case class Account(
  id: Option[Long],
  userId: Long,
  AccountNumber: String,
  balance: BigDecimal,
  createdAt: Option[LocalDateTime]
)

/**
 * Companion object for Account
 *
 * Purpose:
 * - Holds implicit JSON formatter
 * - Enables automatic serialization and deserialization
 *
 * Why here?
 * - Scala compiler automatically looks in companion object
 *   during implicit resolution
 */
object Account {

  /**
   * Implicit JSON formatter for Account
   *
   * Type: OFormat[Account]
   * - Combines Reads + Writes
   *
   * What it does:
   * - Converts Account → JSON (serialization)
   * - Converts JSON → Account (deserialization)
   *
   * How it works:
   * - Json.format[Account] is a macro
   * - At compile time, it generates code to:
   *     - Read fields from JSON
   *     - Write fields to JSON
   *
   * Why implicit?
   * - So Play JSON APIs (like Json.toJson / json.as)
   *   can automatically use this without manual passing
   */
  implicit val format: OFormat[Account] = Json.format[Account]
}