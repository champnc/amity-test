package com.champnc.amity.di

import android.content.Context
import androidx.room.Room
import com.champnc.amity.BuildConfig
import com.champnc.amity.data.TodoRepository
import com.champnc.amity.data.TodoRepositoryImpl
import com.champnc.amity.data.api.TodoService
import com.champnc.amity.data.db.TodoDao
import com.champnc.amity.data.db.TodoDatabase
import com.champnc.amity.domain.LoadTodoUseCase
import com.champnc.amity.domain.LoadTodoUseCaseImpl
import com.champnc.amity.domain.SaveTodoUseCase
import com.champnc.amity.domain.SaveTodoUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {
        val bodyLogging = HttpLoggingInterceptor()
        bodyLogging.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            client.addNetworkInterceptor(bodyLogging)
        }
        return client.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(client)
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideService(retrofit: Retrofit): TodoService {
        return retrofit.create(TodoService::class.java)
    }

    @Provides
    @Singleton
    fun provideTodoRepository(api: TodoService, db: TodoDao): TodoRepository {
        return TodoRepositoryImpl(api, db)
    }

    @Provides
    @Singleton
    fun provideTodoDatabase(@ApplicationContext context: Context): TodoDatabase {
        return Room.databaseBuilder(context, TodoDatabase::class.java, "todos.db").build()
    }

    @Provides
    @Singleton
    fun provideCarDao(todoDatabase: TodoDatabase): TodoDao {
        return todoDatabase.todoDao()
    }

    @Provides
    @Singleton
    fun provideLoadTodoUseCase(repository: TodoRepository): LoadTodoUseCase {
        return LoadTodoUseCaseImpl(repository = repository)
    }

    @Provides
    @Singleton
    fun provideSaveTodoUseCase(repository: TodoRepository): SaveTodoUseCase {
        return SaveTodoUseCaseImpl(repository = repository)
    }
}