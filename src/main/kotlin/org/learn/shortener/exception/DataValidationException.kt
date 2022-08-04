package org.learn.shortener.exception

import java.lang.RuntimeException

class DataValidationException (var errorMessage : String) : RuntimeException() {
    override val message: String?
        get() = super.message
}