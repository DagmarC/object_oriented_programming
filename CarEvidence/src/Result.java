// =====================================================================
// SOLID – Open/Closed Principle (OCP)
//   sealed interface + permits means the set of subtypes is closed
//   (no unknown implementations), so pattern-matching switch is exhaustive
//   without a default arm. Adding a new subtype is explicit and controlled.
//
// SOLID – Interface Segregation Principle (ISP)
//   Result only exposes helpers that every caller actually needs.
//   Domain-specific logic stays in Success/Failure, not here.
//
// CLEAN CODE – Generic type names: V = Value, E = Error.
//   Short but conventional; longer names (TValue, TError) add noise.
// =====================================================================
public sealed interface Result<V, E extends Exception> permits Success, Failure {

    // CLEAN CODE – Predicate methods named isXxx() follow Java convention.
    default boolean isSuccess() { return this instanceof Success<V, E>; }
    default boolean isFailure() { return this instanceof Failure<V, E>; }

    // CLEAN CODE – getOrElse is a well-known functional pattern: return the
    // wrapped value on success, or a safe default on failure. The name
    // communicates intent without requiring a comment.
    default V getOrElse(V defaultValue) {
        return switch (this) {
            case Success<V, E> s -> s.value();
            case Failure<V, E> f -> defaultValue;
        };
    }
}
