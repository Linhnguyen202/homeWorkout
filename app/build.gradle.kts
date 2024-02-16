plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.devtools.ksp")
    id("androidx.navigation.safeargs.kotlin")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.homeworkout"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.homeworkout"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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

    dataBinding {
        enable = true
    }


}

dependencies {
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.6")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.6")
    implementation(files("libs/ruler-picker-1.1.aar"))
    implementation("com.google.firebase:firebase-database:20.3.0")
    implementation("com.google.firebase:firebase-auth:22.3.1")
    implementation(files("libs/rcalenderlib_v2.6.0.aar"))
    implementation("com.google.firebase:firebase-messaging:23.4.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")


    // Room components
    implementation ("androidx.room:room-ktx:2.6.1")
    //noinspection KaptUsageInsteadOfKsp
    ksp ("androidx.room:room-compiler:2.6.1")
    implementation ("androidx.room:room-runtime:2.6.1")

    // coroutines
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")

    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.2")


    // glide
    implementation ("com.github.bumptech.glide:glide:4.14.2")

    // circleImageView
    implementation ("de.hdodenhof:circleimageview:3.1.0")

    // dagger 2
    implementation ("com.google.dagger:dagger:2.48")
    kapt  ("com.google.dagger:dagger-compiler:2.48")
    implementation ("com.google.dagger:dagger-android:2.48")
    implementation ("com.google.dagger:dagger-android-support:2.48")
    kapt ("com.google.dagger:dagger-android-processor:2.48")




    implementation ("me.relex:circleindicator:2.1.6")


    implementation ("io.github.ShawnLin013:number-picker:2.4.13")


    implementation ("com.google.android.flexbox:flexbox:3.0.0")



    implementation ("com.google.android.material:material:1.1.0")
    implementation ("joda-time:joda-time:2.10.1")

}






