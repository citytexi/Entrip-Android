package ajou.paran.entrip.repository.room

import ajou.paran.entrip.model.PlanEntity
import ajou.paran.entrip.model.PlannerEntity
import ajou.paran.entrip.repository.room.plan.dao.PlanDao
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [PlanEntity::class, PlannerEntity::class],
    version = 1
)

abstract class AppDatabase : RoomDatabase() {

    companion object {
        const val DB_NAME = "AppDatabase.db"
    }

    abstract fun planDao() : PlanDao
}