package com.myapps.allsocialaccess.room

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.RoomDatabase
import com.myapps.allsocialaccess.models.SocialMediaApp



@Dao
interface SelectedAppDao {
    @Insert
    suspend fun insertSelectedApp(selectedApp: SocialMediaApp)

    @Delete
    suspend fun deleteSelectedApp(selectedApp: SocialMediaApp)

    @Query("SELECT * FROM selected_apps")
    suspend fun getAllSelectedApps(): List<SocialMediaApp>
}

@Database(entities = [SocialMediaApp::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun selectedAppDao(): SelectedAppDao
}
