plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.kylix.onboard"
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

    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(Libraries.AndroidX.coreKtx)
    implementation(Libraries.AndroidX.appCompat)
    implementation(Libraries.AndroidX.material)
    implementation(Libraries.AndroidX.viewPager2)
    implementation(Libraries.AndroidX.lifecycleViewModelKtx)

    implementation(Libraries.Coroutine.coroutinesCore)
    implementation(Libraries.FlowBinding.flowBindingViewPager)

    implementation(Libraries.Koin.koinCore)
    implementation(Libraries.Koin.koinAndroid)

    implementation(Libraries.CustomUI.dotsIndicator)

    implementation(Libraries.Lottie.lottie)
    implementation(Libraries.Lottie.dotLottie)

    api(project(Modules.core))
}