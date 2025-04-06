import android.app.Application
import com.google.android.filament.Filament

class Medical3DViewerApp : Application() {
    override fun onCreate() {
        super.onCreate()
        // Initialize any app-wide dependencies here
        Filament.init()
    }
}