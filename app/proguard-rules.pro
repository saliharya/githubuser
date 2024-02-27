-keep class com.arya.githubuser.core.data.model.**{*;}

### Gson
-keep class com.google.gson.** { *; }

### Retrofit
-keep class retrofit.** { *; }
-keepclassmembernames interface * {
    @retrofit.http.* <methods>;
}

### Custom Exception
-keep class * extends java.lang.Exception

### Kotlin
-keep class kotlin.Metadata { *; }
-keepclassmembernames class kotlinx.** {
    volatile <fields>;
}