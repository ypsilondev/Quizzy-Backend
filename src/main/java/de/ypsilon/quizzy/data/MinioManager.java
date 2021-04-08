package de.ypsilon.quizzy.data;

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
        String minioSecretKey = System.getenv("minio.key.secret");
        String minioAccessKey = System.getenv("minio.key.access");
        String minioEndpoint = System.getenv("minio.endpoint");

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
