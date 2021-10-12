package pl.edu.agh.cs.kraksim.api.exception

class ObjectNotFoundException(message: String? = null, cause: Throwable? = null) : Exception(message, cause) {
    constructor(cause: Throwable) : this(null, cause)
}
