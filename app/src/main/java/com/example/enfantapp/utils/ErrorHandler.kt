package com.example.enfantapp.utils

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.enfantapp.R

/**
 * Gestionnaire centralisé des erreurs de l'application
 */
object ErrorHandler {
    private const val TAG = "ErrorHandler"

    /**
     * Affiche un message d'erreur à l'utilisateur
     * @param context Contexte de l'application
     * @param errorMessageId ID de la ressource de chaîne du message d'erreur
     * @param throwable Exception associée (optionnelle)
     */
    fun showError(context: Context, errorMessageId: Int, throwable: Throwable? = null) {
        val errorMessage = context.getString(errorMessageId)
        showError(context, errorMessage, throwable)
    }

    /**
     * Affiche un message d'erreur à l'utilisateur
     * @param context Contexte de l'application
     * @param errorMessage Message d'erreur à afficher
     * @param throwable Exception associée (optionnelle)
     */
    fun showError(context: Context, errorMessage: String, throwable: Throwable? = null) {
        // Enregistrement de l'erreur dans les logs
        logError(errorMessage, throwable)
        
        // Affichage d'un toast à l'utilisateur
        Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
        
        // Ici, vous pourriez ajouter un envoi d'erreur à un service de suivi comme Firebase Crashlytics
        // FirebaseCrashlytics.getInstance().recordException(throwable ?: Exception(errorMessage))
    }

    /**
     * Enregistre une erreur dans les logs
     * @param message Message d'erreur
     * @param throwable Exception associée (optionnelle)
     */
    fun logError(message: String, throwable: Throwable? = null) {
        if (throwable != null) {
            Log.e(TAG, message, throwable)
        } else {
            Log.e(TAG, message)
        }
    }

    /**
     * Gère une erreur réseau
     */
    fun handleNetworkError(context: Context, throwable: Throwable) {
        showError(
            context,
            context.getString(R.string.error_network),
            throwable
        )
    }

    /**
     * Gère une erreur de chargement des données
     */
    fun handleDataLoadError(context: Context, dataType: String, throwable: Throwable) {
        showError(
            context,
            context.getString(R.string.error_loading_data, dataType),
            throwable
        )
    }

    /**
     * Gère une erreur de lecture/écriture de fichier
     */
    fun handleFileError(context: Context, fileName: String, throwable: Throwable) {
        showError(
            context,
            context.getString(R.string.error_file_operation, fileName),
            throwable
        )
    }

    /**
     * Gère une erreur de permission
     */
    fun handlePermissionError(context: Context, permission: String) {
        showError(
            context,
            context.getString(R.string.error_permission_denied, permission)
        )
    }
}
