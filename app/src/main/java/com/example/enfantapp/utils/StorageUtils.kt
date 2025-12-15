package com.example.enfantapp.utils

import android.content.Context
import android.os.Environment
import android.os.StatFs
import com.example.enfantapp.R
import java.io.File
import java.io.IOException

/**
 * Utilitaire pour gérer les opérations de stockage et les erreurs associées
 */
object StorageUtils {
    
    /**
     * Vérifie si le stockage externe est disponible en lecture et écriture
     */
    fun isExternalStorageWritable(): Boolean {
        return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
    }
    
    /**
     * Vérifie si le stockage externe est disponible au moins en lecture
     */
    fun isExternalStorageReadable(): Boolean {
        return Environment.getExternalStorageState() in 
                setOf(Environment.MEDIA_MOUNTED, Environment.MEDIA_MOUNTED_READ_ONLY)
    }
    
    /**
     * Vérifie s'il y a suffisamment d'espace disponible sur le stockage interne
     * @param requiredSpace Espace requis en octets
     */
    fun hasEnoughInternalStorage(requiredSpace: Long): Boolean {
        val stat = StatFs(Environment.getDataDirectory().path)
        val availableBlocks = stat.availableBlocksLong
        val blockSize = stat.blockSizeLong
        val availableSpace = availableBlocks * blockSize
        
        return availableSpace >= requiredSpace
    }
    
    /**
     * Gère les erreurs de stockage
     */
    fun handleStorageError(context: Context, throwable: Throwable, fileName: String? = null) {
        when (throwable) {
            is IOException -> {
                if (throwable.message?.contains("ENOSPC") == true || 
                    throwable.message?.contains("No space", ignoreCase = true) == true) {
                    // Stockage plein
                    ErrorHandler.showError(
                        context,
                        context.getString(R.string.error_storage_full)
                    )
                } else if (fileName != null) {
                    // Autre erreur de fichier
                    ErrorHandler.showError(
                        context,
                        context.getString(R.string.error_file_operation, fileName),
                        throwable
                    )
                } else {
                    // Erreur d'E/S générique
                    ErrorHandler.showError(
                        context,
                        context.getString(R.string.error_unknown),
                        throwable
                    )
                }
            }
            is SecurityException -> {
                // Erreur de permission
                ErrorHandler.handlePermissionError(context, "STORAGE")
            }
            else -> {
                // Autre type d'erreur
                ErrorHandler.showError(
                    context,
                    context.getString(R.string.error_unknown),
                    throwable
                )
            }
        }
    }
    
    /**
     * Crée un fichier dans le stockage interne de l'application
     */
    fun createInternalFile(context: Context, fileName: String): File? {
        return try {
            File(context.filesDir, fileName).apply {
                if (!exists()) {
                    parentFile?.mkdirs()
                    createNewFile()
                }
            }
        } catch (e: Exception) {
            handleStorageError(context, e, fileName)
            null
        }
    }
    
    /**
     * Lit le contenu d'un fichier du stockage interne
     */
    fun readInternalFile(context: Context, fileName: String): String? {
        return try {
            File(context.filesDir, fileName).takeIf { it.exists() }?.readText()
        } catch (e: Exception) {
            handleStorageError(context, e, fileName)
            null
        }
    }
    
    /**
     * Écrit du contenu dans un fichier du stockage interne
     */
    fun writeInternalFile(context: Context, fileName: String, content: String): Boolean {
        return try {
            val file = File(context.filesDir, fileName)
            file.parentFile?.mkdirs()
            file.writeText(content)
            true
        } catch (e: Exception) {
            handleStorageError(context, e, fileName)
            false
        }
    }
    
    /**
     * Supprime un fichier du stockage interne
     */
    fun deleteInternalFile(context: Context, fileName: String): Boolean {
        return try {
            val file = File(context.filesDir, fileName)
            if (file.exists()) {
                file.delete()
            } else {
                true // Le fichier n'existe pas, donc on considère que la suppression est réussie
            }
        } catch (e: Exception) {
            handleStorageError(context, e, fileName)
            false
        }
    }
}
