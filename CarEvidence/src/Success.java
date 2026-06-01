// =====================================================================
// CLEAN CODE – record is the right tool here:
//   • Immutable by default (no setters possible)
//   • Auto-generates equals, hashCode, toString, and the accessor value()
//   • Zero boilerplate — KISS at the language level
//
// SOLID – SRP: Success only represents "an operation that worked" and
//   holds its result value. Nothing else.
// =====================================================================
public record Success<V, E extends Exception>(V value) implements Result<V, E> {
    // Intentionally empty — the record gives us everything we need.
    // KISS: resist the urge to add helper methods that belong elsewhere.
}
