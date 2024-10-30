package org.example.sodam.global.s3

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.CannedAccessControlList
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.ByteArrayInputStream
import java.io.InputStream
import java.util.Date
import java.util.Locale
import java.util.UUID

@Service
class FileUtil(
    private val amazonS3: AmazonS3
) {
    @Value("\${cloud.aws.s3.bucket}")
    lateinit var bucketName: String

    companion object {
        const val EXP_TIME = 1000 * 60 * 60 * 1
    }

    fun upload(file: MultipartFile): String {
        val ext = verificationFile(file)

        val randomName = UUID.randomUUID().toString()
        val filename = "$randomName.$ext"
        val inputStream: InputStream = ByteArrayInputStream(file.bytes)

        val metadata = ObjectMetadata().apply {
            contentType = MediaType.IMAGE_PNG_VALUE
            contentLength = file.size
            contentDisposition = "inline"
        }

        inputStream.use {
            amazonS3.putObject(
                PutObjectRequest(bucketName, "$filename", it, metadata)
                    .withCannedAcl(CannedAccessControlList.AuthenticatedRead)
            )
        }

        return filename
    }

    fun delete(objectName: String) {
        amazonS3.deleteObject(bucketName, objectName)
    }

    fun generateObjectUrl(fileName: String): String {
        val expiration = Date().apply {
            time += EXP_TIME.toLong()
        }

        return amazonS3.generatePresignedUrl(
            GeneratePresignedUrlRequest(
                bucketName,
                "$fileName"
            ).withMethod(com.amazonaws.HttpMethod.GET).withExpiration(expiration)
        ).toString()
    }

    private fun verificationFile(file: MultipartFile): String {
        if (file.isEmpty || file.originalFilename == null) throw NullPointerException()
        val originalFilename = file.originalFilename!!
        val ext = originalFilename.substring(originalFilename.lastIndexOf(".") + 1).lowercase(Locale.getDefault())

        if (!(ext == "jpg" || ext == "jpeg" || ext == "png")) {
            throw Exception()
        }

        return ext
    }
}
