# 🎬 Movies Challenge

This is an Android project that displays a movie catalog and detailed information using the [TMDB API](https://developer.themoviedb.org/docs/getting-started).  
The app includes the following features:

- Fetching and displaying the most popular movies.
- Searching movies by title.
- Viewing detailed movie information.
- Retry mechanism for failed requests.

---

## 📌 Technologies & Concepts Used

### 🛠 Technologies
- **Kotlin**
- **Coroutines** (including Flow)
- **Hilt** for dependency injection
- **Jetpack Compose** for UI
- **Retrofit** for networking

### 🧪 Testing
- **JUnit**
- **Mockito**
- **Hamcrest**
- **Coroutines Test**

### 💡 Architecture & Patterns
- **Clean Architecture**
- **Clean Code**
- **SOLID principles**
- **MVVM**
- **Repository pattern**

---

## 🧪 UI Tests

### ▶️ Popular Movies

| Success | Error and Retry |
|--------|-----------------|
| <video src="https://github.com/user-attachments/assets/da50ccf2-218d-4dd1-9923-6d367c50e9a1" width="360" controls></video> | <video src="https://github.com/user-attachments/assets/490ecd9a-1f34-4a5c-994d-840cbbe92f18" width="360" controls></video> |

### 🔄 Load More Movies

<video src="https://github.com/user-attachments/assets/ca9a81e6-276f-4807-b40f-7fa7e5299d6d" width="360" controls></video> 

### 🔍 Search Movies

<video src="https://github.com/user-attachments/assets/6f87a037-39a4-418c-8487-2c44901c40f8" width="360" controls></video>

### 🎞 Movie Detail

| Success | Error and Retry |
|--------|-----------------|
| <video src="https://github.com/user-attachments/assets/00d7f2b3-8a4c-485a-9cc4-52a6d82f6725" width="360" controls></video> | <video src="https://github.com/user-attachments/assets/56541e2d-cb85-4f17-aa42-551301d44dc2" width="360" controls></video

## 🧪 How to Run & Test the Project

To run the project, add the following values to your `local.properties` file:

```properties
BASE_URL=https://api.themoviedb.org/
API_TOKEN=YOUR_API_TOKEN

```

You can generate your TMDB API_TOKEN by visiting your [TMDB API settings page](https://www.themoviedb.org/settings/api).


#### Also You can run the Unit Tests with

```
/gradlew test
```


