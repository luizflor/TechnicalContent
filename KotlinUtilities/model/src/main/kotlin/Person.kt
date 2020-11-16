import java.time.Instant
import java.time.LocalDate

data class Person(val id: Int, val firstName: String, val lastName: String){
    override fun toString(): String {
        return "Person(id=$id, firstName='$firstName', lastName='$lastName')"
    }
}

