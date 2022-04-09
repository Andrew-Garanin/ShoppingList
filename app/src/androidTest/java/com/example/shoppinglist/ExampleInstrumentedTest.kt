package com.example.shoppinglist

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.runner.RunWith
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.example.shoppinglist.database.ShoppingListDatabaseDao
import com.example.shoppinglist.database.ShoppingListDatabase
import com.example.shoppinglist.database.Purchase
import com.example.shoppinglist.database.ShoppingList
import org.junit.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class TruthOrDareGameDatabaseTest {

    private lateinit var truthOrDareGameDao: ShoppingListDatabaseDao
    private lateinit var db: ShoppingListDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.databaseBuilder(context, ShoppingListDatabase::class.java, "shopping_list")
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries().createFromAsset("shopping_list.db")
            .build()
        truthOrDareGameDao = db.getShoppingListDatabaseDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

//    @Test
//    @Throws(Exception::class)
//    fun insertAndGetNight() {
//        val question = Question(1, "Jopa", 1)
//        truthOrDareGameDao.insertQuestion(question)
//        val tonight = truthOrDareGameDao.getLastQuestion()
//        assertEquals(tonight?.questionString, "Jopa")
//    }

    @Test
    @Throws(Exception::class)
    fun getQuestion() {
        val question = truthOrDareGameDao.getShoppingListById(1)

        val a = question.get(0)?.purchases?.get(0)?.amount


        assertEquals(question, "12")
    }

}