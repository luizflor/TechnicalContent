import java.time.Instant
import java.time.LocalDate

data class Person2(val id: Int, val firstName: String, val lastName: String, val birthDate: LocalDate, val isModified: Boolean, val timestamp: Instant, val salary: Double){
    override fun toString(): String {
        return "Person2(id=$id, firstName='$firstName', lastName='$lastName', birthDate=$birthDate, isModified=$isModified, timestamp=$timestamp, salary=$salary)"
    }
}