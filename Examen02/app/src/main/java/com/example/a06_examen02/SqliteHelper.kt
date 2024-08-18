package com.example.a06_examen02

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SqliteHelper(
    context: Context? /* this */
) : SQLiteOpenHelper(context, "AndroidApp", null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        val createComputadorTable = """
            CREATE TABLE COMPUTADOR(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name VARCHAR(100),
                semester INTEGER,
                group_class VARCHAR(50),
                latitude VARCHAR(50), 
                longitude VARCHAR(50) 
            );
        """.trimIndent()

        val createComponenteTable = """
            CREATE TABLE COMPONENTE(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nameComponente VARCHAR(100),
                description VARCHAR(200),
                computador_id INTEGER,
                FOREIGN KEY (computador_id) REFERENCES COMPUTADOR(id) ON DELETE CASCADE
            );
        """.trimIndent()

        db?.execSQL(createComputadorTable)
        db?.execSQL(createComponenteTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (oldVersion < 2) {
            db?.execSQL("ALTER TABLE COMPUTADOR ADD COLUMN latitude VARCHAR(50)")
            db?.execSQL("ALTER TABLE COMPUTADOR ADD COLUMN longitude VARCHAR(50)")
        }
    }

    fun getAllComputadores(): ArrayList<ComputadorEntity> {
        val lectureDB = readableDatabase
        val queryScript = """
            SELECT * FROM COMPUTADOR
        """.trimIndent()
        val queryResult = lectureDB.rawQuery(queryScript, emptyArray())
        val response = arrayListOf<ComputadorEntity>()

        if (queryResult.moveToFirst()) {
            do {
                response.add(
                    ComputadorEntity(
                        queryResult.getInt(0),
                        queryResult.getString(1),
                        queryResult.getInt(2),
                        queryResult.getString(3),
                        queryResult.getString(4),
                        queryResult.getString(5)
                    )
                )
            } while (queryResult.moveToNext())
        }
        queryResult.close()
        lectureDB.close()

        return response
    }

    fun getComponentesByComputador(computador_id: Int): ArrayList<ComponenteEntity> {
        val lectureDB = readableDatabase
        val queryScript = """
            SELECT * FROM COMPONENTE WHERE computador_id=?
        """.trimIndent()
        val queryResult = lectureDB.rawQuery(queryScript, arrayOf(computador_id.toString()))
        val response = arrayListOf<ComponenteEntity>()

        if (queryResult.moveToFirst()) {
            do {
                response.add(
                    ComponenteEntity(
                        queryResult.getInt(0),
                        queryResult.getString(1),
                        queryResult.getString(2),
                        queryResult.getInt(3)
                    )
                )
            } while (queryResult.moveToNext())
        }
        queryResult.close()
        lectureDB.close()

        return response
    }

    fun createComputador(
        name: String,
        semester: Int,
        group_class: String,
        latitude: String,
        longitude: String
    ): Boolean {
        val writeDB = writableDatabase
        val valuesToStore = ContentValues().apply {
            put("name", name)
            put("semester", semester)
            put("group_class", group_class)
            put("latitude", latitude)
            put("longitude", longitude)
        }

        val storeResult = writeDB.insert(
            "COMPUTADOR", // Table name
            null,
            valuesToStore // Values to insert
        )
        writeDB.close()

        return storeResult.toInt() != -1
    }

    fun createComponente(
        nameComponente: String,
        description: String,
        computador_id: Int
    ): Boolean {
        val writeDB = writableDatabase
        val valuesToStore = ContentValues().apply {
            put("nameComponente", nameComponente)
            put("description", description)
            put("computador_id", computador_id)
        }

        val storeResult = writeDB.insert(
            "COMPONENTE", // Table name
            null,
            valuesToStore // Values to insert
        )
        writeDB.close()

        return storeResult.toInt() != -1
    }

    fun updateComputador(
        id: Int,
        name: String,
        semester: Int,
        group_class: String,
        latitude: String,
        longitude: String
    ): Boolean {
        val writeDB = writableDatabase
        val valuesToUpdate = ContentValues().apply {
            put("name", name)
            put("semester", semester)
            put("group_class", group_class)
            put("latitude", latitude)
            put("longitude", longitude)
        }

        val parametersUpdateQuery = arrayOf(id.toString())
        val updateResult = writeDB.update(
            "COMPUTADOR", // Table name
            valuesToUpdate,
            "id=?",
            parametersUpdateQuery
        )
        writeDB.close()

        return updateResult != -1
    }

    fun updateComponente(
        id: Int,
        nameComponente: String,
        description: String,
        computador_id: Int
    ): Boolean {
        val writeDB = writableDatabase
        val valuesToUpdate = ContentValues().apply {
            put("nameComponente", nameComponente)
            put("description", description)
            put("computador_id", computador_id)
        }

        val parametersUpdateQuery = arrayOf(id.toString())
        val updateResult = writeDB.update(
            "COMPONENTE", // Table name
            valuesToUpdate,
            "id=?",
            parametersUpdateQuery
        )
        writeDB.close()

        return updateResult != -1
    }

    fun deleteComputador(id: Int): Boolean {
        val writeDB = writableDatabase
        val parametersDeleteQuery = arrayOf(id.toString())
        val deleteResult = writeDB.delete(
            "COMPUTADOR",
            "id=?",
            parametersDeleteQuery
        )
        writeDB.close()

        return deleteResult != -1
    }

    fun deleteComponente(id: Int): Boolean {
        val writeDB = writableDatabase
        val parametersDeleteQuery = arrayOf(id.toString())
        val deleteResult = writeDB.delete(
            "COMPONENTE",
            "id=?",
            parametersDeleteQuery
        )
        writeDB.close()

        return deleteResult != -1
    }
}
