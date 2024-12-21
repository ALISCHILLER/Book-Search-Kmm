// Import کردن کتابخانه‌ها
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

// پلاگین‌ها برای پروژه
plugins {
    // پلاگین Kotlin Multiplatform برای پشتیبانی از چند پلتفرم
    alias(libs.plugins.kotlinMultiplatform)

    // پلاگین Android Application برای ساخت اپلیکیشن اندروید
    alias(libs.plugins.androidApplication)

    // پلاگین Compose Multiplatform برای پشتیبانی از Compose در چند پلتفرم
    alias(libs.plugins.composeMultiplatform)

    // پلاگین Compose Compiler برای پشتیبانی از کامپایلر Compose
    alias(libs.plugins.composeCompiler)

    // پلاگین KSP برای پردازش کدهای سورس و تولید کد
    alias(dependency.plugins.ksp)

    // پلاگین Room برای استفاده از دیتابیس Room در اندروید
    alias(dependency.plugins.room)
}

// پیکربندی پروژه Kotlin Multiplatform
kotlin {
    // تنظیمات مربوط به اندروید
    androidTarget {
        // استفاده از ویژگی‌های جدید Kotlin برای گریدل
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            // تنظیم نسخه هدف JVM برای استفاده از Java 11
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    // پیکربندی برای پلتفرم‌های iOS
    listOf(
        iosX64(), // پلتفرم x64 برای iOS
        iosArm64(), // پلتفرم ARM64 برای iOS
        iosSimulatorArm64() // شبیه‌ساز ARM64 برای iOS
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp" // نام فریمورک iOS
            isStatic = true // تولید فریمورک استاتیک
        }
    }

    // تنظیمات برای پلتفرم دسکتاپ
    jvm("desktop")

    // پیکربندی Room برای استفاده از اسکیماها در دایرکتوری مشخص
    room {
        schemaDirectory("$projectDir/schemas")
    }

    // پیکربندی برای بخش‌های مختلف سورس
    sourceSets {
        val desktopMain by getting

        // وابستگی‌ها برای اندروید
        androidMain.dependencies {
            implementation(compose.preview) // پشتیبانی از Compose Preview در اندروید
            implementation(libs.androidx.activity.compose) // وابستگی برای فعالیت‌های Compose در اندروید

            // وابستگی‌های شبکه
            implementation(dependency.ktor.client.okhttp) // استفاده از Ktor برای درخواست‌های HTTP

            // وابستگی‌های تزریق وابستگی (DI)
            implementation(dependency.koin.android)
            implementation(dependency.koin.androidx.compose)
        }

        // وابستگی‌های مشترک
        commonMain.dependencies {
            implementation(compose.runtime) // استفاده از کتابخانه Compose Runtime
            implementation(compose.foundation) // استفاده از بنیاد Compose
            implementation(compose.material3) // استفاده از Material3 در Compose
            implementation(compose.ui) // پشتیبانی از UI در Compose
            implementation(compose.components.resources) // منابع کامپوننت‌های Compose
            implementation(compose.components.uiToolingPreview) // پیش‌نمایش ابزارهای UI

            // وابستگی‌های زندگی‌نامه و ViewModel
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)

            // وابستگی‌های ناوبری
            implementation(dependency.jetbrains.compose.navigation)

            // وابستگی‌های شبکه
            implementation(dependency.bundles.ktor)

            // پشتیبانی از تصاویر غیرهمزمان
            implementation(dependency.bundles.coil)

            // وابستگی‌های پایگاه داده (DB)
            implementation(dependency.androidx.room.runtime)
            implementation(dependency.sqlite.bundled)

            // تزریق وابستگی (DI) برای Compose
            implementation(dependency.koin.compose)
            implementation(dependency.koin.compose.viewmodel)
            api(dependency.koin.core)

            // وابستگی‌های لاگینگ
            implementation(dependency.logging.kotlin)
        }

        // وابستگی‌های دسکتاپ
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs) // استفاده از Compose برای دسکتاپ
            implementation(libs.kotlinx.coroutines.swing) // پشتیبانی از کوروتین‌ها در دسکتاپ

            // وابستگی‌های شبکه
            implementation(dependency.ktor.client.okhttp)
        }

        // وابستگی‌های پلتفرم‌های Native
        nativeMain.dependencies {
            implementation(dependency.ktor.client.darwin) // استفاده از Ktor برای پلتفرم‌های داروین (iOS)
        }

        // وابستگی‌های KSP (Kotlin Symbol Processing)
        dependencies {
            ksp(dependency.androidx.room.compiler) // پشتیبانی از Room Compiler
        }
    }
}

// پیکربندی برای پروژه اندروید
android {
    namespace = "org.msa.booksearch" // نام فضای پروژه
    compileSdk = libs.versions.android.compileSdk.get().toInt() // نسخه SDK برای کامپایل

    // پیکربندی تنظیمات پیش‌فرض
    defaultConfig {
        applicationId = "org.msa.booksearch" // شناسه اپلیکیشن
        minSdk = libs.versions.android.minSdk.get().toInt() // حداقل نسخه SDK
        targetSdk = libs.versions.android.targetSdk.get().toInt() // هدف نسخه SDK
        versionCode = 1 // نسخه کد
        versionName = "1.0" // نسخه اپلیکیشن
    }

    // بسته‌بندی منابع
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}" // حذف برخی از فایل‌ها از بسته‌بندی
        }
    }

    // پیکربندی انواع بیلد
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false // غیرفعال کردن فشرده‌سازی کد در نسخه ریلز
        }
    }

    // پیکربندی سازگاری با نسخه‌های جاوا
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

// وابستگی‌های حالت دیباگ
dependencies {
    debugImplementation(compose.uiTooling) // ابزارهای UI Compose برای حالت دیباگ
}

// پیکربندی برای اپلیکیشن دسکتاپ Compose
compose.desktop {
    application {
        mainClass = "org.msa.booksearch.MainKt" // کلاس اصلی اپلیکیشن دسکتاپ

        // پیکربندی برای توزیع‌های بومی
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb) // فرمت‌های هدف توزیع
            packageName = "org.msa.booksearch" // نام بسته
            packageVersion = "1.0.0" // نسخه بسته
        }
    }
}
