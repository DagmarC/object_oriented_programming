// =====================================================================
// CLEAN CODE – Symmetry with Success: same structure, same rationale.
//   When two things are parallel in meaning, make them parallel in form.
//   This is also DRY at the design level: one pattern for both outcomes.
//
// SOLID – SRP: Failure only represents "an operation that failed" and
//   wraps the exception. It does not print, log, or retry.
// =====================================================================
public record Failure<V, E extends Exception>(E err) implements Result<V, E> {
    // Intentionally empty — KISS.
}
