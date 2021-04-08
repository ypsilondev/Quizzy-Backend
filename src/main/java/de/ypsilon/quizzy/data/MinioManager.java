package de.ypsilon.quizzy.data;

import de.ypsilon.quizzy.util.EnvironmentVariableWrapper;
import io.minio.MinioClient;

/**
 * Class to handle all transactions to the minio database.
 *
 * @version 1.0
 */
public class MinioManager {

    private final MinioClient client;

    /**
     * Build the minio manager and initialize the connection to the minio database.
     */
    public MinioManager() {
        EnvironmentVariableWrapper evw = EnvironmentVariableWrapper.getInstance();
        String minioSecretKey = evw.getenv("minio.key.secret");
        String minioAccessKey = evw.getenv("minio.key.access");
        String minioEndpoint = evw.getenv("minio.endpoint");

        this.client = MinioClient.builder()
                        .endpoint(minioEndpoint)
                        .credentials(minioAccessKey, minioSecretKey)
                        .build();
    }

    /**
     * Get the minio client.
     *
     * @return the registered minio client.
     */
    public MinioClient getClient() {
        return client;
    }
}
