package com.example.movieappcompose.data.datastore

import androidx.datastore.CorruptionException
import androidx.datastore.Serializer
import com.example.movieappcompose.LastUpdated
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream

object SettingsSerializer : Serializer<LastUpdated> {
    override fun readFrom(input: InputStream): LastUpdated {
        try {
            return LastUpdated.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override fun writeTo(t: LastUpdated, output: OutputStream) = t.writeTo(output)

}