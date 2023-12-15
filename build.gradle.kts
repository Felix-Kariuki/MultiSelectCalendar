// Top-level build file where you can add configuration options common to all sub-projects/modules.
@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.kotlinAndroid) apply false
    alias(libs.plugins.com.android.library) apply false
    alias(libs.plugins.dokkaPlugin)
    alias(libs.plugins.ktlintPlugin)
}
true // Needed to make the Suppress annotation work for the plugins block

subprojects {
    repositories {
        mavenCentral()
    }

    apply(plugin = "org.jetbrains.dokka")

    apply(plugin = "org.jlleitschuh.gradle.ktlint")
//    ktlint {
//        android.set(true)
//        verbose.set(true)
//        debug.set(true)
//        outputToConsole.set(true)
//        outputColorName.set("RED")
//        ignoreFailures.set(false)
//        enableExperimentalRules.set(true)
//        disabledRules.set(
//            setOf(
//                "no-wildcard-imports",
//                "filename",
//                "package-name",
//                "experimental:package-name",
//                "experimental:comment-wrapping",
//            ),
//        )
//        reporters {
//            reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.PLAIN)
//            reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.CHECKSTYLE)
//            reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.SARIF)
//        }
//        kotlinScriptAdditionalPaths {
//            include(fileTree("scripts/"))
//        }
//        filter {
//            exclude { element -> element.file.path.contains("generated/") }
//        }
//    }
}
