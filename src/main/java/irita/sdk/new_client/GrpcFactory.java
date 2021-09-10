package irita.sdk.new_client;

import io.grpc.*;
import irita.sdk.client.GrpcClientInterceptor;
import irita.sdk.config.ClientConfig;
import irita.sdk.config.OpbConfig;
import irita.sdk.exception.IritaSDKException;

public class GrpcFactory {
    private static final String HTTP_PREFIX = "http://";
    private static final String HTTPS_PREFIX = "https://";

    public static ManagedChannel createGrpcClient(ClientConfig cliConfig, OpbConfig opbConfig) {
        return createGrpcClient(cliConfig.getGrpcAddr(), opbConfig);
    }

    public static ManagedChannel createGrpcClient(String grpcAddr, OpbConfig opbConfig) {
        grpcAddr = removePrefix(grpcAddr);

        String[] split = grpcAddr.split(":");
        if (split.length != 2) {
            throw new IritaSDKException("grpcAddr:\t" + grpcAddr + "is not correct");
        }
        return createGrpcClient(split[0], Integer.parseInt(split[1]), opbConfig);
    }

    public static ManagedChannel createGrpcClient(String name, int port, OpbConfig opbConfig) {
        name = removePrefix(name);
        ManagedChannel channel = ManagedChannelBuilder.forAddress(name, port).usePlaintext().build();

        if (opbConfig != null) {
            ClientInterceptors.intercept(channel, new GrpcClientInterceptor(opbConfig.getProjectKey()));
        }
        return channel;
    }

    private static String removePrefix(String grpcAddr) {
        if (grpcAddr.startsWith(HTTP_PREFIX)) {
            grpcAddr = grpcAddr.substring(HTTP_PREFIX.length());
        }
        if (grpcAddr.startsWith(HTTPS_PREFIX)) {
            grpcAddr = grpcAddr.substring(HTTPS_PREFIX.length());
        }
        return grpcAddr;
    }
}
