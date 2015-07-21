package com.tenPines.builder;

import com.tenPines.application.SecretPalSystem;

public class Seed {
    public static void seed(SecretPalSystem secretPalSystem) throws Exception {

        for (int i = 0; i < 5; i++) {
            secretPalSystem.saveWorker(new WorkerBuilder().build());
        }

    }
}
