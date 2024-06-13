import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PeriodicWorker(context: Context, workerParams: WorkerParameters) : CoroutineWorker(context, workerParams) {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        // Retrieve the persisted counter, increment it and stored it again
        val persistedCounter = sharedPreferences.getInt(KEY_SAVED_VARIABLE, 0)
        val counterIncrement = persistedCounter + 1
        sharedPreferences.edit().putInt(KEY_SAVED_VARIABLE, counterIncrement).apply()
        Log.d(TAG, "PeriodicWorker executed. Count=$counterIncrement")

        Result.success()
    }

    companion object {
        const val UNIQUE_WORK_NAME = "PERIODIC_WORK"

        private val TAG = PeriodicWorker::class.java.simpleName

        private const val PREFS_NAME = "PeriodicWorkerPrefs"
        private const val KEY_SAVED_VARIABLE = "savedVariable"
    }
}
