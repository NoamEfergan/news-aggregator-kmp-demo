# Keep Koin definitions
-keep class org.koin.** { *; }

# Preserve kotlinx.serialization-generated serializers
-keepclassmembers class **$$serializer { *; }
-keepclasseswithmembers class * {
    @kotlinx.serialization.Serializable *;
}
-dontwarn kotlinx.serialization.**
