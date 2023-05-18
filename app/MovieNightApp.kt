import android.app.Application
import android.content.Context

@HiltAndroidApp
class MovieNightApp : Application() {

    private lateinit var appContext: Context
    override fun onCreate() {
        super.onCreate()

    }
}