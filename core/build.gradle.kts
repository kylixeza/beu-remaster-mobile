plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.kylix.core"
    compileSdk = App.compileSdk

    defaultConfig {
        minSdk = App.minSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        debug {
            buildConfigField("String", "BASE_URL", "\"https://beu-api.up.railway.app\"")
        }
        release {
            buildConfigField("String", "BASE_URL", "\"https://beu-api.up.railway.app\"")
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {

    implementation(Libraries.Retrofit.retrofit)
    implementation(Libraries.Retrofit.retrofitGson)
    implementation(Libraries.Retrofit.okhttp)
    implementation(Libraries.Retrofit.okhttpLogging)
    implementation(Libraries.Retrofit.networkResponseAdapter)

    implementation(Libraries.Room.roomRuntime)
    implementation(Libraries.Room.roomKtx)
    ksp(Libraries.Room.roomCompilerKsp)
    implementation(Libraries.Room.sqlite)
    implementation(Libraries.Room.sqlCipher)

    implementation(Libraries.Datastore.datastore)
    implementation(Libraries.Datastore.datastorePreferences)

    implementation(Libraries.Koin.koinCore)
    implementation(Libraries.Koin.koinAndroid)

    implementation(platform(Libraries.Arrow.arrowBOM))
    implementation(Libraries.Arrow.arrowCore)

    api(project(Modules.common))
}