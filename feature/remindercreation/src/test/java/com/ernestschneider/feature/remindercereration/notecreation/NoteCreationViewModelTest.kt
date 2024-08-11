package com.ernestschneider.feature.remindercereration.notecreation

import com.ernestschcneider.feature.remindercreation.notecreation.NoteCreationViewModel
import com.ernestschcneider.remindersapp.core.dispatchers.CoroutineTestExtension
import com.ernestschneider.testutils.InMemoryLocalRepo
import kotlinx.coroutines.Dispatchers
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(CoroutineTestExtension::class)
class NoteCreationViewModelTest {
    private val localRepo = InMemoryLocalRepo()
    private val backgroundDispatcher = Dispatchers.Unconfined
    private val viewModel = NoteCreationViewModel(
        localRepo = localRepo,
        backgroundDispatcher= backgroundDispatcher
    )

    @Test
    fun onNoteContentUpdate() {
        val reminderContent = "reminderContent"

        viewModel.onNoteContentUpdate(reminderContent)

        assertEquals(reminderContent, viewModel.screenState.value.noteContent)
    }
}