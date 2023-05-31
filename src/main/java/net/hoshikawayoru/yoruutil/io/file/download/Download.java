package net.hoshikawayoru.yoruutil.io.file.download;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class Download {
    public static void download(String fileUrl, File saveDir) throws IOException {
        if (fileUrl == null || saveDir == null || saveDir.isFile()){
            return;
        }

        if (saveDir.exists()){
            saveDir.mkdirs();
        }

        File saveFile = new File(saveDir + "/" + new File(fileUrl).getName());

        if (!saveFile.exists()){

            saveFile.createNewFile();
        }

        URL url = new URL(fileUrl);
        InputStream inputStream = url.openStream();
        FileOutputStream fileOutputStream = new FileOutputStream(saveFile);
        ReadableByteChannel readableByteChannel = Channels.newChannel(inputStream);
        ByteBuffer buffer = ByteBuffer.allocate(1024 * 1024);

        while (readableByteChannel.read(buffer) != -1) {
            buffer.flip();
            fileOutputStream.getChannel().write(buffer);
            buffer.compact();
        }

        buffer.flip();

        while (buffer.hasRemaining()) {
            fileOutputStream.getChannel().write(buffer);
        }

        fileOutputStream.close();
        inputStream.close();
    }
}
