package com.example.enfantapp.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.example.enfantapp.R
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * Utilitaire pour gérer les opérations réseau et les erreurs associées
 */
object NetworkUtils {
    
    /**
     * Vérifie si l'appareil est connecté à Internet
     */
    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
            
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
        } else {
            @Suppress("DEPRECATION")
            val networkInfo = connectivityManager.activeNetworkInfo
            networkInfo?.isConnected == true
        }
    }
    
    /**
     * Gère les erreurs réseau communes
     */
    fun handleNetworkError(context: Context, throwable: Throwable) {
        when (throwable) {
            is SocketTimeoutException -> {
                ErrorHandler.showError(
                    context,
                    context.getString(R.string.error_timeout),
                    throwable
                )
            }
            is ConnectException, is UnknownHostException -> {
                ErrorHandler.showError(
                    context,
                    context.getString(R.string.error_network),
                    throwable
                )
            }
            else -> {
                ErrorHandler.showError(
                    context,
                    context.getString(R.string.error_unknown),
                    throwable
                )
            }
        }
    }
    
    /**
     * Exécute une opération réseau avec gestion des erreurs
     */
    suspend fun <T> withNetworkCheck(
        context: Context,
        operation: suspend () -> Result<T>
    ): Result<T> {
        return if (isNetworkAvailable(context)) {
            try {
                operation()
            } catch (e: Exception) {
                handleNetworkError(context, e)
                Result.failure(e)
            }
        } else {
            ErrorHandler.showError(
                context,
                context.getString(R.string.error_network)
            )
            Result.failure(ConnectException("No network available"))
        }
    }
    
    /**
     * Vérifie la force du signal réseau
     * @return Int de 0 (aucun signal) à 4 (signal excellent)
     */
    fun getNetworkSignalStrength(context: Context): Int {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return 0
            val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return 0
            
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> 4 // WiFi est généralement fiable
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                    // Pour les réseaux cellulaires, on pourrait utiliser SignalStrength si disponible
                    3 // Valeur par défaut pour les données mobiles
                }
                else -> 2 // Autres types de connexion
            }
        } else {
            3 // Valeur par défaut pour les versions plus anciennes
        }
    }
}
