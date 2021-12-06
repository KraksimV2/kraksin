package pl.edu.agh.cs.kraksim.common.exception

import org.springframework.stereotype.Service
import org.springframework.web.context.annotation.RequestScope

@RequestScope
@Service
class ConfigurationErrorService {

    val errors = ArrayList<String>()

    fun add(message: String) {
        errors.add(message)
    }

    fun addAll(messages: List<String>) {
        errors.addAll(messages)
    }

    fun validate() {
        if (errors.isNotEmpty()) {
            throw InvalidConfigurationException(errors)
        }
    }

    fun <T> wrap(result: T): ErrorWrapper<T> {
        return ErrorWrapper(result, errors.joinToString(separator = ", \n"))
    }
}

class ErrorWrapper<T>(
    val result: T,
    val errorMessage: String
)
