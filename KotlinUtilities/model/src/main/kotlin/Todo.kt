data class Todo(val id: Int, val description: String) {
    override fun toString(): String {
        return "Todo(id=$id, description='$description')"
    }
}