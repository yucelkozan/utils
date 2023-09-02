package com.kozan.utils

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import java.io.*

class FileHelper(private val context: Context) {

    @Throws(FileNotFoundException::class)
    fun saveMediaToStorage(bitmap: Bitmap, path: String): Uri? {
        var uri: Uri? = null
        val filename = "$path.jpg"
        val fos: OutputStream? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val resolver = context.contentResolver
            val contentValues = ContentValues()
            contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
            contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
            contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
            uri =
                resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
            resolver.openOutputStream(uri!!)
        } else {
            val imagesDir =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val image = File(imagesDir, filename)
            uri = image.path.toUri()
            FileOutputStream(image)
        }
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
        Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show()
        return uri;
    }

    fun shareBitmap(bitmap: Bitmap) {
        val cachePath = File(context.externalCacheDir, "my_images/")
        cachePath.mkdirs()
        val file = File(cachePath, "Image_123.png")
        val fileOutputStream: FileOutputStream
        try {
            fileOutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
            fileOutputStream.flush()
            fileOutputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        val myImageFileUri = FileProvider.getUriForFile(
            context,
            context.applicationContext.packageName + ".provider",
            file
        )
        val intent = Intent(Intent.ACTION_SEND)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intent.putExtra(Intent.EXTRA_STREAM, myImageFileUri)
        intent.type = "image/png"
        context.startActivity(Intent.createChooser(intent, "Share with"))
    }

    companion object {
        @JvmStatic
        fun getBitmapFromUri(context: Context, imageUri: Uri?): Bitmap? {
            var bitmap: Bitmap? = null
            val contentResolver = context.contentResolver
            try {
                bitmap = if (Build.VERSION.SDK_INT < 28) {
                    MediaStore.Images.Media.getBitmap(contentResolver, imageUri)
                } else {
                    val source = ImageDecoder.createSource(
                        contentResolver,
                        imageUri!!
                    )
                    ImageDecoder.decodeBitmap(source)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return bitmap
        }

        /**
         * reduces the size of the image
         * @param image
         * @param maxSize
         * @return
         */
        fun getResizedBitmap(image: Bitmap, maxSize: Int): Bitmap? {
            var width = image.width
            var height = image.height
            val bitmapRatio = width.toFloat() / height.toFloat()
            if (bitmapRatio > 1) {
                width = maxSize
                height = (width / bitmapRatio).toInt()
            } else {
                height = maxSize
                width = (height * bitmapRatio).toInt()
            }
            return Bitmap.createScaledBitmap(image, width, height, true)
        }

        fun getSquareBitmap(bitmap: Bitmap): Bitmap? {
            val output: Bitmap
            output = if (bitmap.width >= bitmap.height) Bitmap.createBitmap(
                bitmap,
                bitmap.width / 2 - bitmap.height / 2,
                0,
                bitmap.height,
                bitmap.height
            ) else Bitmap.createBitmap(
                bitmap,
                0,
                bitmap.height / 2 - bitmap.width / 2,
                bitmap.width,
                bitmap.width
            )
            return output
        }
    }

    fun getBitmapFromView(view: View): Bitmap? {
        //Get the dimensions of the view so we can re-layout the view at its current size
        //and create a bitmap of the same size
        //Get the dimensions of the view so we can re-layout the view at its current size
        //and create a bitmap of the same size
        val width: Int = view.getWidth()
        val height: Int = view.getHeight()

        val measuredWidth = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY)
        val measuredHeight = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY)

        //Cause the view to re-layout

        //Cause the view to re-layout
        view.measure(measuredWidth, measuredHeight)
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight())

        //Create a bitmap backed Canvas to draw the view into

        //Create a bitmap backed Canvas to draw the view into
        val b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val c = Canvas(b)

        //Now that the view is laid out and we have a canvas, ask the view to draw itself into the canvas

        //Now that the view is laid out and we have a canvas, ask the view to draw itself into the canvas
        view.draw(c)

        return b
    }


}