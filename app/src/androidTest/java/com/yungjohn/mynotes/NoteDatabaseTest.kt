package com.yungjohn.mynotes

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.yungjohn.mynotes.database.Note
import com.yungjohn.mynotes.database.NoteDatabase
import com.yungjohn.mynotes.database.NoteDatabaseDao
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import java.io.IOException

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class NoteDatabaseTest {
    private lateinit var noteDao: NoteDatabaseDao
    private lateinit var db: NoteDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, NoteDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        noteDao = db.noteDatabaseDao
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetNote() {
        val note = Note(1L, "Test_Title", "Test_Text")
        noteDao.insert(note)
        val noteWithId = noteDao.getNote(1L)
        assertEquals(noteWithId?.noteTitle, "Test_Title")
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }
}