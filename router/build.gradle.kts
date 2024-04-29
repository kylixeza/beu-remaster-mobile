plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.kylix.router"
    compileSdk = App.compileSdk

    defaultConfig {
        minSdk = App.minSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
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
}

dependencies {

    implementation(Libraries.AndroidX.coreKtx)
    implementation(Libraries.AndroidX.appCompat)
    implementation(Libraries.AndroidX.material)

    implementation(Libraries.Koin.koinCore)
    implementation(Libraries.Koin.koinAndroid)

    api(project(Modules.splash))
    api(project(Modules.onboard))
    api(project(Modules.auth))
    api(project(Modules.main))
    api(project(Modules.home))
    api(project(Modules.category))
    api(project(Modules.detail))
    api(project(Modules.review))
    api(project(Modules.search))
    api(project(Modules.camera))
    api(project(Modules.profile))
    api(project(Modules.updateProfile))
    api(project(Modules.reset_password))
    api(project(Modules.favorite))
    api(project(Modules.history))
    api(project(Modules.helpCenter))
}