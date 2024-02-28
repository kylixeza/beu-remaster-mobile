object App {
    const val compileSdk = 34
    const val minSdk = 24
    const val targetSdk = 30
    const val versionCode = 1
    const val versionName = "1.0"
    const val applicationId = "com.kylix.beuskripsi"
}

object Modules {
    const val app = ":app"
    const val common = ":common"
    const val core = ":core"
    const val splash = ":splash"
    const val onboard = ":onboard"
    const val auth = ":auth"
    const val router = ":router"
    const val main = ":main"
    const val home = ":home"
    const val camera = ":camera"
    const val profile = ":profile"
}

object Libraries {

    object AndroidX {
        const val coreKtx = "androidx.core:core-ktx:1.12.0"
        const val appCompat = "androidx.appcompat:appcompat:1.6.1"
        const val material = "com.google.android.material:material:1.11.0"

        const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.1.4"
        const val viewPager2 = "androidx.viewpager2:viewpager2:1.0.0"
        const val recyclerView = "androidx.recyclerview:recyclerview:1.3.2"

        const val fragmentKtx = "androidx.fragment:fragment-ktx:1.6.2"
        const val lifecycleViewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0"

        const val navigationFragment = "androidx.navigation:navigation-fragment-ktx:2.7.7"
        const val navigationUI = "androidx.navigation:navigation-ui-ktx:2.7.7"
    }

    object Datastore {
        const val datastorePreferences = "androidx.datastore:datastore-preferences:1.0.0"
        const val datastore = "androidx.datastore:datastore:1.0.0"
    }

    object Room {
        const val roomRuntime = "androidx.room:room-runtime:2.6.1"
        const val roomKtx = "androidx.room:room-ktx:2.6.1"
        const val roomCompilerKsp = "androidx.room:room-compiler:2.6.1"
        const val sqlCipher = "net.zetetic:android-database-sqlcipher:4.5.0"
        const val sqlite = "androidx.sqlite:sqlite-ktx:2.4.0"
    }

    object Coroutine {
        const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.2"
        const val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3"
    }

    object FlowBinding {
        const val flowBindingAndroid = "io.github.reactivecircus.flowbinding:flowbinding-android:1.2.0"
        const val flowBindingMaterial = "io.github.reactivecircus.flowbinding:flowbinding-material:1.2.0"
        const val flowBindingViewPager = "io.github.reactivecircus.flowbinding:flowbinding-viewpager2:1.2.0"
    }

    object Koin {
        const val koinCore = "io.insert-koin:koin-core:3.5.3"
        const val koinAndroid = "io.insert-koin:koin-android:3.5.3"
    }

    object Retrofit {
        const val retrofit = "com.squareup.retrofit2:retrofit:2.9.0"
        const val retrofitGson = "com.squareup.retrofit2:converter-gson:2.9.0"
        const val okhttp = "com.squareup.okhttp3:okhttp:5.0.0-alpha.3"
        const val okhttpLogging = "com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.6"
        const val networkResponseAdapter = "com.github.haroldadmin:NetworkResponseAdapter:5.0.0"
    }

    object CameraX {
        const val cameraCore = "androidx.camera:camera-core:1.3.1"
        const val camera2 = "androidx.camera:camera-camera2:1.3.1"
        const val cameraLifecycle = "androidx.camera:camera-lifecycle:1.3.1"
        const val cameraView = "androidx.camera:camera-view:1.3.1"
    }

    object TFLite {
        const val tfLiteSupport = "org.tensorflow:tensorflow-lite-support:0.4.4"
        const val tfLiteMetadata = "org.tensorflow:tensorflow-lite-metadata:0.1.0"
        const val tfLite = "org.tensorflow:tensorflow-lite:2.14.0"
        const val tfLiteGPU = "org.tensorflow:tensorflow-lite-gpu:2.14.0"
    }

    object Navigation {
        const val navigationFragment = "androidx.navigation:navigation-fragment-ktx:2.7.6"
        const val navigationUI = "androidx.navigation:navigation-ui-ktx:2.7.6"
    }

    object Lottie {
        const val lottie = "com.airbnb.android:lottie:6.1.0"
        const val dotLottie = "com.github.dotlottie:dotlottieloader-android:1.1"
    }

    object CustomUI {
        const val dotsIndicator = "com.tbuonomo:dotsindicator:5.0"
        const val shimmer = "com.facebook.shimmer:shimmer:0.5.0"
        const val snackify = "com.github.Musfick:Snackify:0.1.2"
    }

    object Glide {
        const val glide = "com.github.bumptech.glide:glide:4.15.1"
        const val compiler = "com.github.bumptech.glide:compiler:4.15.1"
    }

    object Test {
        const val junit = "junit:junit:4.13.2"
        const val junitExt = "androidx.test.ext:junit:1.1.5"
        const val espressoCore = "androidx.test.espresso:espresso-core:3.5.1"
    }

    object Arrow {
        const val arrowBOM = "io.arrow-kt:arrow-stack:1.2.0"
        const val arrowCore = "io.arrow-kt:arrow-core"
    }

}