package com.tuan.flex;

import org.apache.commons.io.FilenameUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.*;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        startWatching();
    }

    private static void startWatching() {
        try {WatchService watchService = FileSystems.getDefault().newWatchService();
            final String ENTRY_CREATE = "ENTRY_CREATE";
            final String ENTRY_DELETE = "ENTRY_DELETE";

            Path path = Paths.get("C:\\Users\\Public\\Videos\\Sample Videos");
            path.register(
                    watchService,
                    StandardWatchEventKinds.ENTRY_CREATE,
//                    StandardWatchEventKinds.ENTRY_MODIFY,
                    StandardWatchEventKinds.ENTRY_DELETE);

            WatchKey key;
            while ((key = watchService.take()) != null) {
                for (WatchEvent<?> event : key.pollEvents()) {
                    System.out.println("Event kind:" + event.kind() + ". File affected: " + event.context() + ".");
                    System.out.println(System.currentTimeMillis());
                    String movieTitle = FilenameUtils.removeExtension(event.context().toString());

                    switch (event.kind().name()) {
                        case ENTRY_CREATE:
                            HttpClient client = HttpClientBuilder.create().build();
                            // https://api.themoviedb.org/3/search/movie?api_key=357a6111d3d9be8bf68bcc8b35e64e96&query=blade%20runner%202049
                            HttpResponse response = client.execute(new HttpGet("http://localhost:3000/movie?api_key=357a6111d3d9be8bf68bcc8b35e64e96&query=blade%20runner%202049"));
                            // Get the response
                            BufferedReader rd = new BufferedReader
                                    (new InputStreamReader(
                                            response.getEntity().getContent()));

                            String line = "";
                            int no = 0;
                            while ((line = rd.readLine()) != null) {
                                System.out.println(no+=1);
                                System.out.println(line);
                            }
                    }
                }
                key.reset();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
