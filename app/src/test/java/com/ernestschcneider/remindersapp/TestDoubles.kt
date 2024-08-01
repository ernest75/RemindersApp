package com.ernestschcneider.remindersapp

import com.google.common.truth.Truth
import org.junit.jupiter.api.Test

class TestDoubles {
    @Test
    fun tryout() {
        val result = "test"
        Truth.assertThat(result).isEqualTo("test")
    }
}
